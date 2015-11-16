package toystore.service.details;


public interface UpdateOrderlineDetails {
    public boolean updateOrderline(Long orderID, Long itemID, Long orderlineID, int quantity);
}
