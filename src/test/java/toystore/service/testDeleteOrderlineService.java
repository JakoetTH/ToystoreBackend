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
import toystore.conf.ItemFactory;
import toystore.conf.OrderFactory;
import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.ItemRepository;
import toystore.repository.OrderRepository;

@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class testDeleteOrderlineService extends AbstractTestNGSpringContextTests{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    AddOrderlineService addOrderlineService;
    @Autowired
    DeleteOrderlineService deleteOrderlineService;


    private Orders order;
    private Item item;
    private Orderline orderline;
    private List<Orderline> orderlines;
    private boolean bool;
    @Before
    public void setUp()
    {
        orderlines = new ArrayList<Orderline>();
        bool = false;
    }

    @Test
    public void testAddOrder()
    {
        order = OrderFactory.createOrder(new Date(), 200, false, orderlines);
        orderRepository.save(order);
    }

    @Test(dependsOnMethods = "testAddOrder")
    public void testAddItem()
    {
        item = ItemFactory.createItem("Teddybear","Kiddies",1,200,orderlines);
        itemRepository.save(item);
    }


    @Test(dependsOnMethods = "testAddItem")
    public void testAddOrderline()
    {
        bool = addOrderlineService.addOrderline(order.getID(), item.getID(), 1);
        Assert.assertTrue(bool);

        order = orderRepository.findOne(order.getID());
        List<Orderline> orderlineso = order.getOrderlines();
        Assert.assertEquals(orderlineso.size(),1);

        item = itemRepository.findOne(item.getID());
        List<Orderline> orderlinsi = item.getOrderlines();
        Assert.assertEquals(orderlinsi.size(),1);

        for(Orderline orderlineit: orderlinsi)
            orderline = orderlineit;
    }
    @Test(dependsOnMethods = "testAddOrderline")
    public void testDeleteOrderline()
    {
        bool = false;
        bool = deleteOrderlineService.deleteOrderline(order.getID(), item.getID(), orderline.getID());
        orderRepository.delete(order);
        itemRepository.delete(item);
        Assert.assertTrue(bool);
    }

    @After
    public void tearDown()
    {

    }

}
