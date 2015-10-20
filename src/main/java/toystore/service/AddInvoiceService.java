package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import toystore.conf.InvoiceFactory;
import toystore.domain.Customer;
import toystore.domain.Invoice;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.CustomerRepository;
import toystore.repository.InvoiceRepository;
import toystore.repository.OrderRepository;

@Service
public class AddInvoiceService implements AddInvoiceDetails{
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;


    private Long id;
    private Orders order;
    private Customer customer;
    private Invoice invoice;
    private List<Orderline> orderlines;
    @Override
    public Long addInvoice(Long customerID, Long orderID)
    {
        customer = customerRepository.findOne(customerID);
        if(customer==null)
            return null;

        order = orderRepository.findOne(orderID);
        if(order==null || order.getCheckout())
            return null;

        orderlines = order.getOrderlines();

        invoice = InvoiceFactory.createInvoice(orderID, order.getTotalPrice(), orderlines);
        invoiceRepository.save(invoice);
        id = invoice.getID();

        List<Invoice> invoices = customer.getInvoices();
        invoices.add(invoice);

        customer = new Customer
                        .Builder(customer.getUserName())
                        .copy(customer)
                        .invoices(invoices)
                        .build();

        customerRepository.save(customer);

        return id;
    }
}
