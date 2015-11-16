package toystore.service.details;

import java.util.List;

import toystore.domain.Item;

public interface ViewItemsByCategoryDetails {
    public List<Item> viewItemsByCategory(String category);
}
