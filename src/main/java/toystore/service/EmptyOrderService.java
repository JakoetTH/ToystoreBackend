package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.OrderRepository;
import toystore.repository.OrderlineRepository;
import toystore.service.details.EmptyOrderDetails;

@Service
public class EmptyOrderService implements EmptyOrderDetails {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderlineRepository orderlineRepository;

    private Orders orders;
    private List<Orderline> orderlines;
    @Override
    public boolean emptyOrder(Long id)
    {
        orderlines = new ArrayList<Orderline>();
        orders = orderRepository.findOne(id);
        if(orders.getCheckout())
            return false;
        for(Orderline orderline: orders.getOrderlines())
            orderlineRepository.delete(orderline);
        orders = new Orders
                    .Builder(orders.getDateModified())
                    .copy(orders)
                    .dateModified(new Date())
                    .totalPrice(0)
                    .orderlines(orderlines)
                    .build();
        orderRepository.save(orders);
        return true;
    }
}
