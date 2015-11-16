package toystore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import toystore.domain.Invoice;
import toystore.domain.Orders;
import toystore.service.AddInvoiceService;
import toystore.service.AddOrderService;
import toystore.service.CheckoutOrderService;
import toystore.service.EmptyOrderService;
import toystore.service.GetInvoiceService;
import toystore.service.GetOrderDateService;
import toystore.service.GetOrderService;

@RestController
@RequestMapping("order/**")
public class OrderApiController {
    @Autowired
    AddOrderService addOrderService;
    @Autowired
    GetOrderService getOrderService;
    @Autowired
    GetOrderDateService getOrderDateService;
    @Autowired
    EmptyOrderService emptyOrderService;
    @Autowired
    AddInvoiceService addInvoiceService;
    @Autowired
    GetInvoiceService getInvoiceService;
    @Autowired
    CheckoutOrderService checkoutOrderService;

    //GET EXISTING OPEN ORDER ON CUSTOMER OR CREATE A NEW ONE IF ONE DOES NOT EXIST
    @RequestMapping(value = "get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> getOrder(@RequestParam Long customerID)
    {
        Orders order = getOrderService.getOrder(customerID);
        if(order==null)
        {
            boolean bool = addOrderService.addOrder(customerID);
            if(!bool)
                return new ResponseEntity<Orders>(HttpStatus.FORBIDDEN);//This should never ever happen but if it does then this is here
            order = getOrderService.getOrder(customerID);
            return new ResponseEntity<Orders>(order, HttpStatus.CREATED);//Creates a new order if one doesn't exist for this customer
        }
        return new ResponseEntity<Orders>(order, HttpStatus.FOUND);//returns uncheckedout order for this customer
    }

    //GET ORDER DATE AS STRING
    @RequestMapping(value = "getdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrderDate(@RequestParam Long orderID)
    {
        String date = getOrderDateService.getOrderDate(orderID);
        if(date==null)
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);//Something is wrong with getOrderDateService or the orderID doesn't exist
        return new ResponseEntity<String>(date, HttpStatus.FOUND);
    }

    //DELETE EXISTING ORDER
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ResponseEntity<Boolean> deleteOrder(@RequestParam Long orderID)
    {
        boolean bool = emptyOrderService.emptyOrder(orderID);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//order id not found - has already been deleted
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //CHECKOUT ORDER
    @RequestMapping(value = "checkout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> checkoutOrder(@RequestParam Long orderID,
                                                 @RequestParam Long customerID)
    {
        Long id = addInvoiceService.addInvoice(customerID, orderID);
        if(id==null)
            return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);//customer id or order id not found, does not exist in the database
        boolean bool = checkoutOrderService.checkoutOrder(orderID);
        if(!bool)
            return new ResponseEntity<Invoice>(HttpStatus.CONFLICT);//order has already been checked out; probably
        Invoice invoice = getInvoiceService.getInvoice(id);
        if(invoice==null)
            return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);//the addInvoiceService didn't add the invoice to the database for some reason
        return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
    }
}
