package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toystore.domain.Customer;
import toystore.domain.Orders;
import toystore.repository.CustomerRepository;
import toystore.repository.OrderRepository;
import toystore.service.details.GetOrderDetails;

@Service
public class GetOrderService implements GetOrderDetails {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;

    private Orders order;
    private Customer customer;
    @Override
    public Orders getOrder(Long customerID)
    {
        customer = customerRepository.findOne(customerID);
        if(customer==null)
            return null;
        Iterable<Orders> orders = customer.getOrders();
        for(Orders orderi: orders)
        {
            if(!orderi.getCheckout())
                return orderi;
        }
        return null;
    }
}
