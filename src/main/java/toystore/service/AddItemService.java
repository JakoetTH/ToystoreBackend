package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import toystore.conf.ItemFactory;
import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.repository.ItemRepository;
import toystore.service.details.AddItemDetails;

@Service
public class AddItemService implements AddItemDetails {
    @Autowired
    ItemRepository itemRepository;
    private Item item;
    private List<Orderline> orderlines;

    @Override
    public boolean addItem(String name, String category, int stock, float price)
    {
        Iterable<Item> iitems = itemRepository.findAll();
        for(Item it: iitems)
        {
            if(it.getName().equalsIgnoreCase(name))
                return false;
        }
        item = ItemFactory.createItem(name, category, stock, price, orderlines);
        itemRepository.save(item);
        return true;
    }


}
