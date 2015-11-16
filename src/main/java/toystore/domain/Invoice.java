package toystore.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import toystore.domain.details.InvoiceDetails;

@Entity
public class Invoice implements InvoiceDetails, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private Long orderID;
    private float totalPrice;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="invoice_id")
    private List<Orderline> orderlines;


    private Invoice()
    {

    }

    public Invoice(Builder builder)
    {
        this.ID=builder.ID;
        this.orderID=builder.orderID;
        this.totalPrice=builder.totalPrice;
        this.orderlines=builder.orderlines;

    }
    @Override
    public Long getID()
    {
        return this.ID;
    }
    @Override
    public Long getOrderID()
    {
        return this.orderID;
    }
    @Override
    public float getTotalPrice()
    {
        return this.totalPrice;
    }
    @Override
    public List<Orderline> getOrderlines()
    {
        return this.orderlines;
    }


    public static class Builder
    {
        private Long ID;
        private Long orderID;
        private float totalPrice;
        private List<Orderline> orderlines;

        public Builder(Long orderID)
        {
            this.orderID=orderID;
        }

        public Builder orderID(Long orderID)
        {
            this.orderID=orderID;
            return this;
        }

        public Builder ID(Long ID)
        {
            this.ID=ID;
            return this;
        }

        public Builder totalPrice(float totalPrice)
        {
            this.totalPrice=totalPrice;
            return this;
        }

        public Builder orderlines(List<Orderline> orderlines)
        {
            this.orderlines=orderlines;
            return this;
        }

        public Builder copy(Invoice invoice)
        {
            this.ID=invoice.getID();
            this.orderID=invoice.getOrderID();
            this.totalPrice=invoice.getTotalPrice();
            this.orderlines=invoice.getOrderlines();
            return this;
        }

        public Invoice build()
        {
            return new Invoice(this);
        }
    }
}

