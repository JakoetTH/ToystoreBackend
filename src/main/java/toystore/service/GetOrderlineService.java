package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import toystore.domain.Item;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.ItemRepository;
import toystore.repository.OrderRepository;
import toystore.service.details.GetOrderlineDetails;

@Service
public class GetOrderlineService implements GetOrderlineDetails {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    private Orders order;
    private Item item;
    private Orderline orderline;
    @Override
    public Long getOrderlineID(Long orderID, Long itemID)
    {
        order = orderRepository.findOne(orderID);
        if(order==null)
            return null;

        item = itemRepository.findOne(itemID);
        if(item==null)
            return null;

        Iterable<Orderline> orderlineso = order.getOrderlines();
        Iterable<Orderline> orderlinesi = item.getOrderlines();

        List<Long> orderlinesoid = new ArrayList<Long>();
        List<Long> orderlinesiid = new ArrayList<Long>();

        for(Orderline orderlineit: orderlineso)
            orderlinesoid.add(orderlineit.getID());
        for(Orderline orderlineit: orderlinesi)
            orderlinesiid.add(orderlineit.getID());

        for(Long orderlineid: orderlinesoid)
        {
            if(orderlinesiid.contains(orderlineid))
                return orderlineid;
        }

        return null;
    }
}
