package toystore.service.details;

import toystore.domain.Customer;

public interface LoginDetails {
    public Customer Login(String userName, String password);
}
