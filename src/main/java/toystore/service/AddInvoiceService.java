package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import toystore.conf.InvoiceFactory;
import toystore.domain.Customer;
import toystore.domain.Invoice;
import toystore.domain.Orderline;
import toystore.domain.Orders;
import toystore.repository.CustomerRepository;
import toystore.repository.InvoiceRepository;
import toystore.repository.OrderRepository;
import toystore.service.details.AddInvoiceDetails;

@Service
public class AddInvoiceService implements AddInvoiceDetails {
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
    private List<Orderline> orderlinesblank;
    @Override
    public Long addInvoice(Long customerID, Long orderID)
    {
        customer = customerRepository.findOne(customerID);
        if(customer==null)
            return null;

        order = orderRepository.findOne(orderID);
        if(order==null || order.getCheckout())
            return null;
        orderlinesblank = new ArrayList<Orderline>();
        orderlines = order.getOrderlines();
        List<Orderline> orderlinesi = new ArrayList<Orderline>();
        for(Orderline orderlineit: orderlines)
            orderlinesi.add(orderlineit);

        invoice = InvoiceFactory.createInvoice(orderID, order.getTotalPrice(), orderlinesblank);
        invoiceRepository.save(invoice);

        id = invoice.getID();

        invoice = invoiceRepository.findOne(id);

        invoice = new Invoice
                        .Builder(invoice.getOrderID())
                        .copy(invoice)
                        .orderlines(orderlinesi)
                        .build();

        invoiceRepository.save(invoice);

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
