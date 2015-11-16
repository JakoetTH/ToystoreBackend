package toystore.service.details;

public interface DeleteOrderlineDetails {
    public boolean deleteOrderline(Long orderID, Long itemID, Long orderlineID);
}
