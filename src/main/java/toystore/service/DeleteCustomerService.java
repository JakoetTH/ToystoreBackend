package toystore.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toystore.domain.Customer;
import toystore.repository.CustomerRepository;

@Service
public class DeleteCustomerService implements DeleteCustomerDetails{
    @Autowired
    CustomerRepository customerRepository;

    private Customer customer;
    @Override
    public boolean deleteCustomer(Long customerID)
    {
        customer = customerRepository.findOne(customerID);
        if(customer==null)
            return false;
        customerRepository.delete(customer);
        return true;
    }
}
