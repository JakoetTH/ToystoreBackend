package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.ItemRepository;
import toystore.repository.OrderRepository;
import toystore.repository.OrderlineRepository;
import toystore.service.details.DeleteOrderlineDetails;

@Service
public class DeleteOrderlineService implements DeleteOrderlineDetails {
    @Autowired
    OrderlineRepository orderlineRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    private Orders order;
    private Item item;
    private Orderline orderline;
    @Override
    public boolean deleteOrderline(Long orderID, Long itemID, Long orderlineID)
    {
        orderline = orderlineRepository.findOne(orderlineID);
        if(orderline==null)
            return false;

        order = orderRepository.findOne(orderID);
        if(order==null || order.getCheckout())
            return false;

        item = itemRepository.findOne(itemID);
        if(item==null)
            return false;

        //item = new Item
                //.Builder(item.getName())
                //.copy(item)
                //.quantity(item.getQuantity() + orderline.getQuantity())
                //.build();
        //itemRepository.save(item);

        order = new Orders
                    .Builder(new Date())
                    .copy(order)
                    .dateModified(new Date())
                    .build();
        orderRepository.save(order);

        orderlineRepository.delete(orderline);
        return true;
    }

}
