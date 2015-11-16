package toystore.service.details;


public interface AddOrderlineDetails {
    public boolean addOrderline(Long orderID, Long itemID, int quantity);
}
