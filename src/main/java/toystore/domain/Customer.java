package toystore.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class Customer implements CustomerDetails, Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String contact;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="customer_id")
    private List<Order> orders;

    private Customer()
    {

    }

    public Customer(Builder builder)
    {
        this.ID=builder.ID;
        this.userName=builder.userName;
        this.password=builder.password;
        this.firstName=builder.firstName;
        this.lastName=builder.lastName;
        this.idNumber=builder.idNumber;
        this.contact=builder.contact;
        this.orders=builder.orders;
    }
    @Override
    public Long getID()
    {
        return this.ID;
    }
    public String getUserName() {
        return this.userName;
    }
    public String getPassword()
    {
        return this.password;
    }
    @Override
    public String getFirstName()
    {
        return this.firstName;
    }
    @Override
    public String getLastName()
    {
        return this.lastName;
    }
    @Override
    public String getIdNumber()
    {
        return this.idNumber;
    }
    @Override
    public String getContact()
    {
        return this.contact;
    }
    @Override
    public List<Order> getOrders()
    {
        return this.orders;
    }

    public static class Builder
    {
        private Long ID;
        private String userName;
        private String password;
        private String firstName;
        private String lastName;
        private String idNumber;
        private String contact;
        private List<Order> orders;

        public Builder(String userName)
        {
            this.userName=userName;
        }

        public Builder ID(Long ID)
        {
            this.ID=ID;
            return this;
        }

        public Builder password(String password)
        {
            this.password=password;
            return this;
        }

        public Builder firstName(String firstName)
        {
            this.firstName=firstName;
            return this;
        }

        public Builder lastName(String lastName)
        {
            this.lastName=lastName;
            return this;
        }

        public Builder idNumber(String idNumber)
        {
            this.idNumber=idNumber;
            return this;
        }

        public Builder contact(String contact)
        {
            this.contact=contact;
            return this;
        }

        public Builder orders(List<Order> orders)
        {
            this.orders=orders;
            return this;
        }



        public Builder copy(Customer Customer)
        {
            this.ID=Customer.getID();
            this.userName=Customer.getUserName();
            this.password=Customer.getPassword();
            this.firstName=Customer.getFirstName();
            this.lastName=Customer.getLastName();
            this.idNumber=Customer.getIdNumber();
            this.contact=Customer.getContact();
            this.orders=Customer.getOrders();
            return this;
        }

        public Customer build()
        {
            return new Customer(this);
        }
    }

}

