package toystore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import toystore.domain.Item;
import toystore.service.AddItemService;
import toystore.service.UpdateItemService;
import toystore.service.ViewItemsByCategoryService;
import toystore.service.ViewItemsService;

@RestController
@RequestMapping("item/**")
public class ItemApiController {
    @Autowired
    AddItemService addItemService;
    @Autowired
    ViewItemsService viewItemsService;
    @Autowired
    ViewItemsByCategoryService viewItemsByCategoryService;
    @Autowired
    UpdateItemService updateItemService;
    //ADD NEW ITEM
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ResponseEntity<Boolean> addItem(@RequestParam String name,
                                           @RequestParam String category,
                                           @RequestParam int stock,
                                           @RequestParam float price)
    {
        boolean bool = addItemService.addItem(name, category, stock, price);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);//Item with that name already exists
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    //VIEW ALL ITEMS
    @RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> viewAllItems()
    {
        List<Item> items = viewItemsService.viewAllItems();
        if(items.size()==0)
            return new ResponseEntity<List<Item>>(HttpStatus.NOT_FOUND);//No items exist in the database - or something else went wrong
        return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
    }

    //VIEW ITEMS BY CATEGORY
    @RequestMapping(value = "category", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> viewAllItemsByCategory(@RequestParam String category)
    {
        List<Item> items = viewItemsByCategoryService.viewItemsByCategory(category);
        if(items.size()==0)
            return new ResponseEntity<List<Item>>(HttpStatus.NOT_FOUND);//No items with that category exist
        return new ResponseEntity<List<Item>>(items, HttpStatus.FOUND);
    }

    //UPDATE EXISTING ITEM PRICE
    @RequestMapping(value = "update/stock", method = RequestMethod.GET)
    public ResponseEntity<Boolean> updateItemStock(@RequestParam Long itemID,
                                                   @RequestParam int addQuantity)
    {
        boolean bool = updateItemService.updateItemStock(itemID, addQuantity);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);//item of itemid does not exist in the database
        return new ResponseEntity<Boolean>(true, HttpStatus.FOUND);
    }

    //UPDATE EXISTING ITEM STOCK
    @RequestMapping(value = "update/price", method = RequestMethod.GET)
    public ResponseEntity<Boolean> updateItemPrice(@RequestParam Long itemID,
                                                   @RequestParam float newPrice)
    {
        boolean bool = updateItemService.updateItemPrice(itemID, newPrice);
        if(!bool)
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);//item of itemid does not exist in the database
        return new ResponseEntity<Boolean>(true, HttpStatus.FOUND);
    }
}
