package toystore.repository;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import org.junit.Assert;

import java.util.List;

import toystore.App;
import toystore.conf.ItemFactory;
import toystore.domain.Item;
import toystore.domain.Orderline;

@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
public class testItemCrudRepository extends AbstractTestNGSpringContextTests{
    private Long id;
    private List<Orderline> orderlines;
    @Autowired
    ItemRepository repository;

    @Test
    public void testCreate()
    {
        Item item = ItemFactory.createItem("Teddybear","Kiddies",1,200,orderlines);
        repository.save(item);
        id = item.getID();
        Assert.assertNotNull(item);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRead()
    {
        Item item = repository.findOne(id);
        Assert.assertEquals(item.getName(), "Teddybear");
    }

    @Test(dependsOnMethods = "testRead")
    public void testUpdate()
    {
        Item item = repository.findOne(id);
        Item newItem = new Item
                .Builder(item.getName())
                .copy(item)
                .category("Teens")
                .build();
        repository.save(newItem);

        Item updatedItem = repository.findOne(id);
        Assert.assertNotEquals(item.getCategory(),updatedItem.getCategory());
    }

    @Test(dependsOnMethods = "testUpdate")
    public void testDelete()
    {
        Item item = repository.findOne(id);
        repository.delete(item);
        Item newItem = repository.findOne(id);
        Assert.assertNull(newItem);
    }

}
