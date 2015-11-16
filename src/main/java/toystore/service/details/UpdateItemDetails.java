package toystore.service.details;

public interface UpdateItemDetails {
    public boolean updateItemPrice(Long itemid, float newPrice);
    public boolean updateItemStock(Long itemid, int addQuantity);
}
