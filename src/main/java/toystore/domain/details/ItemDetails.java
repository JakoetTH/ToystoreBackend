package toystore.domain.details;

import java.util.List;

import toystore.domain.Orderline;

public interface ItemDetails {
    public Long getID();
    public String getName();
    public String getCategory();
    public int getQuantity();
    public float getPrice();
    public List<Orderline> getOrderlines();

}
