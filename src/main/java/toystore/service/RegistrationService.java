package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import toystore.conf.CustomerFactory;
import toystore.domain.Customer;
import toystore.domain.Invoice;
import toystore.domain.Orders;
import toystore.repository.CustomerRepository;
import toystore.service.details.RegistrationDetails;

@Service
public class RegistrationService implements RegistrationDetails {
    @Autowired
    CustomerRepository customerRepository;

    private List<Orders> orders;
    private List<Invoice> invoices;

    @Override
    public boolean Register(String userName, String password, String firstName, String lastName, String idNumber, String contact)
    {
        Iterable<Customer> icustomers = customerRepository.findAll();
            for(Customer cust: icustomers)
            {
                if(cust.getUserName().equalsIgnoreCase(userName))
                    return false;
            }
        Customer newCustomer = CustomerFactory.createCustomer(userName, password, firstName, lastName, idNumber, contact, orders, invoices);
        customerRepository.save(newCustomer);
        return true;
    }

}
