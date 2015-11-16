package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toystore.domain.Item;
import toystore.repository.ItemRepository;
import toystore.service.details.AddItemStockDetails;

@Service
public class AddItemStockService implements AddItemStockDetails {
    @Autowired
    ItemRepository itemRepository;
    private Item item;
    @Override
    public boolean addItemStock(Long id, int stock)
    {
        item = itemRepository.findOne(id);
        if(item==null)
            return false;
        item = new Item
                .Builder(item.getName())
                .copy(item)
                .quantity(item.getQuantity() + stock)
                .build();
        itemRepository.save(item);
        return true;
    }
}
