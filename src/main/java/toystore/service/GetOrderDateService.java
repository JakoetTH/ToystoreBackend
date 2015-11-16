package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toystore.domain.Orders;
import toystore.repository.OrderRepository;
import toystore.service.details.GetOrderDateDetails;

@Service
public class GetOrderDateService implements GetOrderDateDetails {
    @Autowired
    OrderRepository orderRepository;

    private Orders order;
    @Override
    public String getOrderDate(Long orderID)
    {
        order = orderRepository.findOne(orderID);
        if(order==null)
            return null;
        return order.getDateModified().toString();
    }

}
