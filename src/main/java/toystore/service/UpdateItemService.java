package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toystore.domain.Item;
import toystore.repository.ItemRepository;
import toystore.service.details.UpdateItemDetails;

@Service
public class UpdateItemService implements UpdateItemDetails {
    @Autowired
    ItemRepository itemRepository;

    private Item item;
    @Override
    public boolean updateItemPrice(Long itemid, float newPrice)
    {
        item = itemRepository.findOne(itemid);
        if(item==null)
            return false;

        item = new Item
                    .Builder(item.getName())
                    .copy(item)
                    .price(newPrice)
                    .build();

        itemRepository.save(item);
        return true;
    }

    @Override
    public boolean updateItemStock(Long itemid, int addQuantity)
    {
        item = itemRepository.findOne(itemid);
        if(item==null)
            return false;

        item = new Item
                .Builder(item.getName())
                .copy(item)
                .quantity(item.getQuantity() + addQuantity)
                .build();

        itemRepository.save(item);
        return true;
    }
}
