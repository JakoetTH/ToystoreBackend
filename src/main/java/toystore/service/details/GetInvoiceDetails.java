package toystore.service.details;
import toystore.domain.Invoice;

public interface GetInvoiceDetails {
    public Invoice getInvoice(Long invoiceID);
}
