package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toystore.domain.Customer;
import toystore.repository.CustomerRepository;
import toystore.service.details.ChangePasswordDetails;


@Service
public class ChangePasswordService implements ChangePasswordDetails {
    @Autowired
    CustomerRepository customerRepository;
    private Customer customer;
    private Customer newCustomer;
    @Override
    public boolean changePassword(Long customerid, String newPassword)
    {
        customer = customerRepository.findOne(customerid);
            if(customer==null)
                return false;
        newCustomer = new Customer
                            .Builder(customer.getUserName())
                            .copy(customer)
                            .password(newPassword)
                            .build();
        customerRepository.save(newCustomer);
        return true;
    }

}
