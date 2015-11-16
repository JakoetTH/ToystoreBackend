package toystore.domain.details;

import java.util.Date;
import java.util.List;

import toystore.domain.Orderline;

public interface OrderDetails {
    public Long getID();
    public Date getDateModified();
    public float getTotalPrice();
    public Boolean getCheckout();
    public List<Orderline> getOrderlines();
}
