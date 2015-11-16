package toystore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import toystore.service.ChangePasswordService;
import toystore.service.DeleteCustomerService;

@RestController
@RequestMapping("customer/**")
public class CustomerApiController {
    @Autowired
    ChangePasswordService changePasswordService;
    @Autowired
    DeleteCustomerService deleteCustomerService;

    //CHANGE PASSWORD
    @RequestMapping(value = "changepassword", method = RequestMethod.GET)
    public ResponseEntity<Boolean> changePassword(@RequestParam Long customerID,
                                                  @RequestParam String newPassword)
    {
        boolean bool = changePasswordService.changePassword(customerID, newPassword);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//customer ID is invalid
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //DELETE USER
    @RequestMapping(value ="delete", method = RequestMethod.GET)
    public ResponseEntity<Boolean> deleteCustomer(@RequestParam Long customerid)
    {
        boolean bool = deleteCustomerService.deleteCustomer(customerid);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);//customerid didn't correspond with any existing customers
        return new ResponseEntity<Boolean>(true, HttpStatus.FOUND);
    }
}
