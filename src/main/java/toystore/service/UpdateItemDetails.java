package toystore.service;

public interface UpdateItemDetails {
    public boolean updateItemPrice(Long itemid, float newPrice);
    public boolean updateItemStock(Long itemid, int addQuantity);
}
