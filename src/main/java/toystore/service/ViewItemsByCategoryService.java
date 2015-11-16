package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import toystore.domain.Item;
import toystore.repository.ItemRepository;
import toystore.service.details.ViewItemsByCategoryDetails;

@Service
public class ViewItemsByCategoryService implements ViewItemsByCategoryDetails {
    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Item> viewItemsByCategory(String category)
    {
        List<Item> items = new ArrayList<Item>();
        Iterable<Item> iitems = itemRepository.findAll();
        for(Item item: iitems)
        {
            if(item.getCategory().equalsIgnoreCase(category))
                items.add(item);
        }
        return items;
    }
}
