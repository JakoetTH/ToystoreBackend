package toystore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import toystore.service.AddOrderlineService;
import toystore.service.DeleteOrderlineService;
import toystore.service.GetOrderlineService;
import toystore.service.UpdateOrderlineService;

@RestController
@RequestMapping("orderline/**")
public class OrderlineApiController {
    @Autowired
    AddOrderlineService addOrderlineService;
    @Autowired
    GetOrderlineService getOrderlineService;
    @Autowired
    UpdateOrderlineService updateOrderlineService;
    @Autowired
    DeleteOrderlineService deleteOrderlineService;

    //ITS FINALLY HERE - ORDERLINE HANDLER API
    @RequestMapping(value= "handle", method = RequestMethod.GET)
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
