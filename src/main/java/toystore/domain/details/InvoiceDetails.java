package toystore.domain.details;

import java.util.List;

import toystore.domain.Orderline;

public interface InvoiceDetails {
    public Long getID();
    public Long getOrderID();
    public float getTotalPrice();
    public List<Orderline> getOrderlines();
}
