package toystore.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import toystore.domain.Customer;
import toystore.domain.Invoice;
import toystore.domain.Orders;
import toystore.repository.CustomerRepository;
import toystore.service.details.DeleteCustomerDetails;

@Service
public class DeleteCustomerService implements DeleteCustomerDetails {
    @Autowired
    CustomerRepository customerRepository;


    private Customer customer;
    private List<Orders> orders;
    private List<Invoice> invoices;
    @Override
    public boolean deleteCustomer(Long customerID)
    {
        customer = customerRepository.findOne(customerID);
        if(customer==null)
            return false;

        orders = new ArrayList<Orders>();
        invoices = new ArrayList<Invoice>();

        customer = new Customer
                        .Builder(customer.getUserName())
                        .copy(customer)
                        .orders(orders)
                        .invoices(invoices)
                        .build();

        customerRepository.save(customer);
        customerRepository.delete(customer);

        return true;
    }
}
