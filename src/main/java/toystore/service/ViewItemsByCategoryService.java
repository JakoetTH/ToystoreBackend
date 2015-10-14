package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import toystore.domain.Item;
import toystore.repository.ItemRepository;

@Service
public class ViewItemsByCategoryService implements ViewItemsByCategoryDetails {
    @Autowired
    ItemRepository itemRepository;

    private List<Item> items = new ArrayList<Item>();
    private Iterable<Item> iitems;

    @Override
    public List<Item> viewItemsByCategory(String category)
    {
        iitems = itemRepository.findAll();
        for(Item item: iitems)
        {
            if(item.getCategory().equalsIgnoreCase(category))
                items.add(item);
        }
        return items;
    }
}
