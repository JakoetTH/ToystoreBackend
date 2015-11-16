package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import toystore.domain.Item;
import toystore.repository.ItemRepository;
import toystore.service.details.ViewItemsDetails;

@Service
public class ViewItemsService implements ViewItemsDetails {
    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Item> viewAllItems()
    {
        List<Item> items = new ArrayList<Item>();
        Iterable<Item> iitems = itemRepository.findAll();
        for(Item item: iitems)
            items.add(item);
        return items;
    }

}
