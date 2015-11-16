package toystore.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import toystore.domain.details.ItemDetails;

@Entity
public class Item implements ItemDetails, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String name;
    private String category;
    private int quantity;
    private float price;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="item_id")
    private List<Orderline> orderlines;

    private Item()
    {

    }

    public Item(Builder builder)
    {
        this.ID=builder.ID;
        this.name=builder.name;
        this.category=builder.category;
        this.quantity=builder.quantity;
        this.price=builder.price;
        this.orderlines=builder.orderlines;

    }
    @Override
    public Long getID()
    {
        return this.ID;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public String getCategory()
    {
        return this.category;
    }
    @Override
    public int getQuantity()
    {
        return this.quantity;
    }
    @Override
    public float getPrice()
    {
        return this.price;
    }
    @Override
    public List<Orderline> getOrderlines() {
        return this.orderlines;
    }


    public static class Builder
    {
        private Long ID;
        private String name;
        private String category;
        private int quantity;
        private float price;
        private List<Orderline> orderlines;

        public Builder(String name)
        {
            this.name=name;
        }

        public Builder ID(Long ID)
        {
            this.ID=ID;
            return this;
        }

        public Builder category(String category)
        {
            this.category=category;
            return this;
        }

        public Builder quantity(int quantity)
        {
            this.quantity=quantity;
            return this;
        }

        public Builder price(float price)
        {
            this.price=price;
            return this;
        }

        public Builder orderlines(List<Orderline> orderlines)
        {
            this.orderlines=orderlines;
            return this;
        }

        public Builder copy(Item item)
        {
            this.ID=item.getID();
            this.name=item.getName();
            this.category=item.getCategory();
            this.quantity=item.getQuantity();
            this.price=item.getPrice();
            this.orderlines=item.getOrderlines();
            return this;
        }

        public Item build()
        {
            return new Item(this);
        }
    }
}
