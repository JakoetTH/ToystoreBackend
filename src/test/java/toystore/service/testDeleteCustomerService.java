package toystore.service;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import toystore.App;
import toystore.conf.CustomerFactory;
import toystore.conf.OrderFactory;
import toystore.domain.Customer;
import toystore.domain.Invoice;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.CustomerRepository;
import toystore.repository.OrderRepository;

@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class testDeleteCustomerService extends AbstractTestNGSpringContextTests{
    @Autowired
    DeleteCustomerService deleteCustomerService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;

    private Orders order;
    private Customer customer;
    private List<Invoice> invoices;
    private List<Orders> orders;
    private List<Orderline> orderlines;
    private Long id;

    @BeforeMethod
    public void setUp()
    {
        order = OrderFactory.createOrder(new Date(), 200, false, orderlines);
        orderRepository.save(order);
        customer = CustomerFactory.createCustomer("Redc", "password", "Thawhir", "Jakoet", "12345", "12345", orders, invoices);
        customerRepository.save(customer);
        orders = new ArrayList<Orders>();
        orders.add(order);
        customer = new Customer
                        .Builder(customer.getUserName())
                        .copy(customer)
                        .orders(orders)
                        .build();
        customerRepository.save(customer);
        id = customer.getID();
    }

    @Test
    public void testDeleteCustomer()
    {
        boolean bool = false;
        bool = deleteCustomerService.deleteCustomer(id);
        Assert.assertTrue(bool);
        customer = customerRepository.findOne(id);
        Assert.assertNull(customer);
    }

    @AfterMethod
    public void tearDown()
    {

    }
}
