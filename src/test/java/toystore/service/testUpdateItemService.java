package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import toystore.App;
import toystore.conf.ItemFactory;
import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.repository.ItemRepository;

@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class testUpdateItemService extends AbstractTestNGSpringContextTests{
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UpdateItemService updateItemService;

    private Item item;
    private List<Orderline> orderlines;
    private Long id;
    @BeforeMethod
    public void setUp()
    {
        item = ItemFactory.createItem("Teddybear", "kiddies", 6, 200, orderlines);
        itemRepository.save(item);
        id = item.getID();
    }

    @Test
    public void testUpdateItemPrice()
    {
        boolean bool = false;
        bool = updateItemService.updateItemPrice(id, 300);
        Assert.assertTrue(bool);
        item = itemRepository.findOne(id);
        Assert.assertEquals(item.getPrice(), 300, 0.001);
    }

    @Test(dependsOnMethods = "testUpdateItemPrice")
    public void testUpdateItemStock()
    {
        boolean bool = false;
        bool = updateItemService.updateItemStock(id, 2);
        Assert.assertTrue(bool);
        item = itemRepository.findOne(id);
        Assert.assertEquals(item.getQuantity(), 8);
    }

    @AfterMethod
    public void tearDown()
    {
        itemRepository.delete(item);
    }
}
