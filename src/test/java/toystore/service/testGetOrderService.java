package toystore.service;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
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
public class testGetOrderService extends AbstractTestNGSpringContextTests {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    GetOrderService getOrderService;

    private Customer customer;
    private Long id;
    private List<Orders> orders;
    private Orders order;
    private List<Invoice> invoices;
    private List<Orderline> orderlines;
    @Before
    public void setUp()
    {
        orders = new ArrayList<Orders>();
        invoices = new ArrayList<Invoice>();
        orderlines = new ArrayList<Orderline>();
    }

    @Test
    public void testCreateCustomer()
    {
        customer = CustomerFactory.createCustomer("Redc", "password", "Thawhir", "Jakoet", "12345", "12345", orders, invoices);
        customerRepository.save(customer);
        id = customer.getID();
    }

    @Test(dependsOnMethods = "testCreateCustomer")
    public void testAddOrder()
    {
        customer = customerRepository.findOne(id);
        orders = customer.getOrders();
        order = OrderFactory.createOrder(new Date(),0,false, orderlines);
        Assert.assertNotNull(order);
        orders.add(order);
        customer = new Customer
                .Builder(customer.getUserName())
                .copy(customer)
                .orders(orders)
                .build();
        customerRepository.save(customer);
    }
    @Test(dependsOnMethods = "testAddOrder")
    public void testGetOrder()
    {
        order = getOrderService.getOrder(id);
        Assert.assertNotNull(order);
    }
    //turned this into a test so that I could use TestNG-sorting, this is actually the tearDown() method
    @Test(dependsOnMethods = "testGetOrder")
    public void deleteThem()
    {
        customer = customerRepository.findOne(customer.getID());
        List<Orders> ordersc = customer.getOrders();
        for(Orders orderi: ordersc)
            orderRepository.delete(orderi);
        customerRepository.delete(customer);
        Assert.assertTrue(true);
    }

    @After
    public void tearDown()
    {

    }

}
