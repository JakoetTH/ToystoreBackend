package toystore.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import toystore.conf.OrderlineFactory;
import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.ItemRepository;
import toystore.repository.OrderRepository;
import toystore.repository.OrderlineRepository;
import toystore.service.details.AddOrderlineDetails;

@Service
public class AddOrderlineService implements AddOrderlineDetails {
    @Autowired
    OrderlineRepository orderlineRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    private Orders order;
    private Item item;
    private Orderline orderline;
    private List<Orderline> orderlineso;
    private List<Orderline> orderlinesi;

    @Override
    public boolean addOrderline(Long orderID, Long itemID, int quantity)
    {
        order = orderRepository.findOne(orderID);
        if(order==null || order.getCheckout())
            return false;

        item = itemRepository.findOne(itemID);
        if(item==null || item.getQuantity() < quantity)
            return false;

        orderlineso = order.getOrderlines();
        orderlinesi = item.getOrderlines();

        orderline = OrderlineFactory.createOrderline(quantity);
        orderlineRepository.save(orderline);

        orderlineso.add(orderline);
        orderlinesi.add(orderline);

        order = new Orders
                    .Builder(order.getDateModified())
                    .copy(order)
                    .dateModified(new Date())
                    .orderlines(orderlineso)
                    .totalPrice(order.getTotalPrice() + (item.getPrice() * quantity))
                    .build();

        item = new Item
                    .Builder(item.getName())
                    .copy(item)
                    .orderlines(orderlinesi)
                    //.quantity(item.getQuantity()-quantity)//do this in checkout Order Service
                    .build();
        orderRepository.save(order);
        itemRepository.save(item);
        return true;
    }
}
