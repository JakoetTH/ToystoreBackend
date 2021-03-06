package toystore.service;

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
import toystore.repository.OrderlineRepository;

@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class testUpdateOrderlineService extends AbstractTestNGSpringContextTests{
    @Autowired
    OrderlineRepository orderlineRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    AddOrderlineService addOrderlineService;
    @Autowired
    GetOrderlineService getOrderlineService;
    @Autowired
    UpdateOrderlineService updateOrderlineService;



    private Orders order;
    private Item item;
    private Orderline orderline;
    private Orderline searchedOrderline;
    private Orderline updatedOrderline;
    private List<Orderline> orderlines;
    private boolean bool;
    private Long id;
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
        item = ItemFactory.createItem("Teddybear", "Kiddies", 2, 200, orderlines);
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
        Assert.assertEquals(orderlinsi.size(), 1);

        for(Orderline orderlineit: orderlinsi)
            orderline = orderlineit;
    }

    @Test(dependsOnMethods = "testAddOrderline")
    public void testGetOrderline()
    {
        id = getOrderlineService.getOrderlineID(order.getID(), item.getID());
        searchedOrderline = orderlineRepository.findOne(id);
        Assert.assertNotNull(searchedOrderline);
        Assert.assertEquals(searchedOrderline.getQuantity(), 1);
    }

    @Test(dependsOnMethods = "testGetOrderline")
    public void testUpdateOrderline()
    {
        bool = false;
        bool = updateOrderlineService.updateOrderline(order.getID(), item.getID(), id, 2);
        Assert.assertTrue(bool);
        updatedOrderline = orderlineRepository.findOne(id);
        Assert.assertEquals(updatedOrderline.getQuantity(), 2);
    }

    @Test(dependsOnMethods = "testUpdateOrderline")
    public void DeleteThem()
    {
        orderlineRepository.delete(updatedOrderline);
        orderRepository.delete(order);
        itemRepository.delete(item);
        Assert.assertTrue(true);
    }
}
