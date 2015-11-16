package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

import toystore.App;
import toystore.conf.ItemFactory;
import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.repository.ItemRepository;

@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class testViewItemsService extends AbstractTestNGSpringContextTests{
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ViewItemsService viewItemsService;

    private Item item1;
    private Item item2;
    private Item item3;
    private List<Orderline> orderlines;
    private Iterable<Item> items;
    private List<Item> serviceItems;
    private int count = 0;

    @BeforeMethod
    public void setUp()
    {
        item1 = ItemFactory.createItem("Teddy Bear","4-7",2,200,orderlines);
        item2 = ItemFactory.createItem("Hoola Hoop","4-7",2,50,orderlines);
        item3 = ItemFactory.createItem("Playstation","10+",2,3000,orderlines);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

    }

    @Test
    public void testViewItems()
    {
        serviceItems = viewItemsService.viewAllItems();
        items = itemRepository.findAll();
        for(Item item: items)
            count++;
        Assert.assertEquals(serviceItems.size(),count);
    }

    @AfterMethod
    public void tearDown()
    {
        itemRepository.delete(item1);
        itemRepository.delete(item2);
        itemRepository.delete(item3);
    }
}
