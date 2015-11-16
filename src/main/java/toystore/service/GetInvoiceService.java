package toystore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toystore.domain.Invoice;
import toystore.repository.InvoiceRepository;
import toystore.service.details.GetInvoiceDetails;

@Service
public class GetInvoiceService implements GetInvoiceDetails {
    @Autowired
    InvoiceRepository invoiceRepository;

    private Invoice invoice;
    @Override
    public Invoice getInvoice(Long invoiceID)
    {
        invoice = invoiceRepository.findOne(invoiceID);
        if(invoice==null)
            return null;
        return invoice;
    }
}
