package toystore.domain;


import javax.persistence.*;
import java.io.Serializable;

import toystore.domain.details.OrderlineDetails;

@Entity
public class Orderline implements OrderlineDetails, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private int quantity;



    private Orderline()
    {

    }

    public Orderline(Builder builder)
    {
        this.ID=builder.ID;
        this.quantity=builder.quantity;

    }
    @Override
    public Long getID()
    {
        return this.ID;
    }
    @Override
    public int getQuantity()
    {
        return this.quantity;
    }


    public static class Builder
    {
        private Long ID;
        private int quantity;

        public Builder(int quantity)
        {
            this.quantity=quantity;
        }

        public Builder quantity(int quantity)
        {
            this.quantity=quantity;
            return this;
        }

        public Builder ID(Long ID)
        {
            this.ID=ID;
            return this;
        }

        public Builder copy(Orderline orderline)
        {
            this.ID=orderline.getID();
            this.quantity=orderline.getQuantity();
            return this;
        }

        public Orderline build()
        {
            return new Orderline(this);
        }
    }
}

