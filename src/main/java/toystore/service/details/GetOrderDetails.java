package toystore.service.details;

import toystore.domain.Orders;

public interface GetOrderDetails {
    public Orders getOrder(Long customerID);
}
