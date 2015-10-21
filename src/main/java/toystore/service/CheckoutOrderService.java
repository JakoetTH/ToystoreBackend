package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.ItemRepository;
import toystore.repository.OrderRepository;

@Service
public class CheckoutOrderService implements CheckoutOrderDetails{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    private Orders order;
    private List<Orderline> orderlines;
    @Override
    public boolean checkoutOrder(Long orderID)
    {
        order = orderRepository.findOne(orderID);
        if(order==null)
            return false;//order id not found

        orderlines = order.getOrderlines();

        Iterable<Item> items = itemRepository.findAll();

        for(Item itemit: items)
        {
            for(Orderline orderlineit: orderlines) {
                if (itemit.getOrderlines().contains(orderlineit)) {
                    if(itemit.getQuantity() < orderlineit.getQuantity())
                        return false;//not enough items in stock anymore
                    itemit = new Item
                                .Builder(itemit.getName())
                                .copy(itemit)
                                .quantity(itemit.getQuantity()-orderlineit.getQuantity())
                                .build();
                    itemRepository.save(itemit);
                }
            }
        }

        Orders checkedoutOrder = new Orders
                                    .Builder(order.getDateModified())
                                    .copy(order)
                                    .dateModified(new Date())
                                    .checkout(true)
                                    .build();


        orderRepository.save(checkedoutOrder);
        return true;
    }
}
