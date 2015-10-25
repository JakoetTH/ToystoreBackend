package toystore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import toystore.domain.Customer;
import toystore.domain.Invoice;
import toystore.domain.Item;
import toystore.domain.Orders;
import toystore.service.AddInvoiceService;
import toystore.service.AddItemService;
import toystore.service.AddOrderService;
import toystore.service.AddOrderlineService;
import toystore.service.ChangePasswordService;
import toystore.service.CheckoutOrderService;
import toystore.service.DeleteCustomerService;
import toystore.service.DeleteOrderlineService;
import toystore.service.EmptyOrderService;
import toystore.service.GetInvoiceService;
import toystore.service.GetOrderDateService;
import toystore.service.GetOrderService;
import toystore.service.GetOrderlineService;
import toystore.service.LoginService;
import toystore.service.RegistrationService;
import toystore.service.UpdateItemService;
import toystore.service.UpdateOrderlineService;
import toystore.service.ViewItemsByCategoryService;
import toystore.service.ViewItemsService;

@RestController
@RequestMapping("/**")
public class AllApiController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    LoginService loginService;
    @Autowired
    AddOrderService addOrderService;
    @Autowired
    GetOrderService getOrderService;
    @Autowired
    GetOrderDateService getOrderDateService;
    @Autowired
    EmptyOrderService emptyOrderService;
    @Autowired
    AddItemService addItemService;
    @Autowired
    ViewItemsService viewItemsService;
    @Autowired
    ViewItemsByCategoryService viewItemsByCategoryService;
    @Autowired
    ChangePasswordService changePasswordService;
    @Autowired
    AddOrderlineService addOrderlineService;
    @Autowired
    GetOrderlineService getOrderlineService;
    @Autowired
    UpdateOrderlineService updateOrderlineService;
    @Autowired
    DeleteOrderlineService deleteOrderlineService;
    @Autowired
    AddInvoiceService addInvoiceService;
    @Autowired
    GetInvoiceService getInvoiceService;
    @Autowired
    CheckoutOrderService checkoutOrderService;
    @Autowired
    DeleteCustomerService deleteCustomerService;
    @Autowired
    UpdateItemService updateItemService;

    //USER REGISTRATION
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<Boolean> createCustomer(@RequestParam String username,
                                                  @RequestParam String password,
                                                  @RequestParam String firstname,
                                                  @RequestParam String lastname,
                                                  @RequestParam String idnumber,
                                                  @RequestParam String contact)
    {
        boolean bool = registrationService.Register(username, password, firstname, lastname, idnumber, contact);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //USER LOGIN
    @RequestMapping(value ="/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@RequestParam String username,
                                                @RequestParam String password)
    {
        Customer customer = loginService.Login(username, password);
        //the following code should be added into a service to check if customer exists
        if(customer==null)
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);//customer with that username and password doesn't exist
        return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
    }

    //DELETE USER
    @RequestMapping(value ="customer/delete", method = RequestMethod.GET)
    public ResponseEntity<Boolean> deleteCustomer(@RequestParam Long customerid)
    {
        boolean bool = deleteCustomerService.deleteCustomer(customerid);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);//customerid didn't correspond with any existing customers
        return new ResponseEntity<Boolean>(true, HttpStatus.FOUND);

    }

    //GET EXISTING OPEN ORDER ON CUSTOMER OR CREATE A NEW ONE IF ONE DOES NOT EXIST
    @RequestMapping(value = "order/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @RequestMapping(value = "order/getdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrderDate(@RequestParam Long orderID)
    {
        String date = getOrderDateService.getOrderDate(orderID);
        if(date==null)
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);//Something is wrong with getOrderDateService or the orderID doesn't exist
        return new ResponseEntity<String>(date, HttpStatus.FOUND);
    }

    //DELETE EXISTING ORDER
    @RequestMapping(value = "order/delete", method = RequestMethod.GET)
    public ResponseEntity<Boolean> deleteOrder(@RequestParam Long orderID)
    {
        boolean bool = emptyOrderService.emptyOrder(orderID);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//order id not found - has already been deleted
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //CHECKOUT ORDER
    @RequestMapping(value = "order/checkout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    //ADD NEW ITEM
    @RequestMapping(value = "item/add", method = RequestMethod.GET)
    public ResponseEntity<Boolean> addItem(@RequestParam String name,
                                           @RequestParam String category,
                                           @RequestParam int stock,
                                           @RequestParam float price)
    {
        boolean bool = addItemService.addItem(name, category, stock, price);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//Item with that name already exists
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //VIEW ALL ITEMS
    @RequestMapping(value = "item/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> viewAllItems()
    {
        List<Item> items = viewItemsService.viewAllItems();
        if(items.size()==0)
            return new ResponseEntity<List<Item>>(HttpStatus.NOT_FOUND);//No items exist in the database - or something else went wrong
        return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
    }

    //VIEW ITEMS BY CATEGORY
    @RequestMapping(value = "item/category", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> viewAllItemsByCategory(@RequestParam String category)
    {
        List<Item> items = viewItemsByCategoryService.viewItemsByCategory(category);
        if(items.size()==0)
            return new ResponseEntity<List<Item>>(HttpStatus.NOT_FOUND);//No items with that category exist
        return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
    }

    //UPDATE EXISTING ITEM PRICE
    @RequestMapping(value = "item/update/stock", method = RequestMethod.GET)
    public ResponseEntity<Boolean> updateItemStock(@RequestParam Long itemID,
                                                   @RequestParam int addQuantity)
    {
        boolean bool = updateItemService.updateItemStock(itemID, addQuantity);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);//item of itemid does not exist in the database
        return new ResponseEntity<Boolean>(true, HttpStatus.FOUND);
    }

    //UPDATE EXISTING ITEM STOCK
    @RequestMapping(value = "item/update/price", method = RequestMethod.GET)
    public ResponseEntity<Boolean> updateItemPrice(@RequestParam Long itemID,
                                                   @RequestParam float newPrice)
    {
        boolean bool = updateItemService.updateItemPrice(itemID, newPrice);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);//item of itemid does not exist in the database
        return new ResponseEntity<Boolean>(true, HttpStatus.FOUND);
    }

    //CHANGE PASSWORD
    @RequestMapping(value = "customer/changepassword", method = RequestMethod.GET)
    public ResponseEntity<Boolean> changePassword(@RequestParam Long customerID,
                                                  @RequestParam String newPassword)
    {
        boolean bool = changePasswordService.changePassword(customerID, newPassword);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//customer ID is invalid
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //ITS FINALLY HERE - ORDERLINE HANDLER API
    @RequestMapping(value= "orderline/handle", method = RequestMethod.GET)
    public ResponseEntity<Boolean> handleOrderline(@RequestParam Long orderID,
                                                   @RequestParam Long itemID,
                                                   @RequestParam int quantity)
    {
        boolean bool;
        Long id = getOrderlineService.getOrderlineID(orderID, itemID);
        if(id==null && quantity != 0) {
            bool = addOrderlineService.addOrderline(orderID, itemID, quantity);
            if(!bool)
                return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//Could not create an Orderline, probably because not enough quantity or the order is already checked out
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);//New Orderline created successfully
        }
        if(quantity==0)
        {
            bool = deleteOrderlineService.deleteOrderline(orderID, itemID, id);
            if(!bool)
                return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//something went wrong in the deleteOrderlineService
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);//Deleted Orderline successfully
        }
        bool = updateOrderlineService.updateOrderline(orderID, itemID, id, quantity);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//something went wrong in the updateOrderlineService, probably not enough stock of an item or order is already checked out
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);//Updated Orderline successfully
    }

}
