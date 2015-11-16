package toystore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import toystore.domain.Customer;
import toystore.service.LoginService;
import toystore.service.RegistrationService;

@RestController
@RequestMapping("/**")
public class GeneralApiController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    LoginService loginService;

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
        if(customer==null)
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);//customer with that username and password doesn't exist
        return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
    }



}
