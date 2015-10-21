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

@Service
public class UpdateOrderlineService implements UpdateOrderlineDetails{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderlineRepository orderlineRepository;

    private Orders order;
    private Item item;
    private Orderline orderline;
    private Orderline newOrderline;
    //private int oldQuantity;
    @Override
    public boolean updateOrderline(Long orderID, Long itemID, Long orderlineID, int quantity)
    {
        orderline = orderlineRepository.findOne(orderlineID);
        if(orderline==null)
            return false;
        //oldQuantity = orderline.getQuantity();

        order = orderRepository.findOne(orderID);
        if(order==null || order.getCheckout())
            return false;

        item = itemRepository.findOne(itemID);
        if(item==null || item.getQuantity() < quantity)
            return false;

        newOrderline = new Orderline
                .Builder(quantity)
                            .copy(orderline)
                            .quantity(quantity)
                            .build();

        orderlineRepository.save(newOrderline);

        //item = itemRepository.findOne(itemID);

        //item = new Item
                //.Builder(item.getName())
                //.copy(item)
                //.quantity(item.getQuantity() + oldQuantity - quantity)
                //.build();
        //itemRepository.save(item);
        order = orderRepository.findOne(orderID);

        order = new Orders
                .Builder(order.getDateModified())
                .copy(order)
                .dateModified(new Date())
                .totalPrice(item.getPrice() * quantity)
                .build();
        orderRepository.save(order);
        return true;
    }
}
