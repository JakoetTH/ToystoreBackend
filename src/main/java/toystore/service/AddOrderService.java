package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import toystore.conf.OrderFactory;
import toystore.domain.Customer;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.CustomerRepository;
import toystore.repository.OrderRepository;
import toystore.service.details.AddOrderDetails;

@Service
public class AddOrderService implements AddOrderDetails {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;

    private Orders order;
    private Customer customer;
    private List<Orderline> orderlines;
    @Override
    public boolean addOrder(Long customerID)
    {
        customer = customerRepository.findOne(customerID);
        if(customer==null)
            return false;
        order = OrderFactory.createOrder(new Date(), 0, false, orderlines);
        List<Orders> orders = customer.getOrders();
        orders.add(order);
        customer = new Customer
                .Builder(customer.getUserName())
                .copy(customer)
                .orders(orders)
                .build();
        customerRepository.save(customer);
        return true;
    }

}
