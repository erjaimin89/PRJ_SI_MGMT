package hibernate;

import java.io.Serializable;
import java.sql.Blob;

public class InvoiceEntity implements Serializable{

	private static final long serialVersionUID = 4435052305884797232L;
	private long invoiceId;
	private Blob invoice;
	private long invoiceDate;
	
	public InvoiceEntity() {
	}

	public InvoiceEntity(Blob invoice, long invoiceDate) {
		super();
		this.invoice = invoice;
		this.invoiceDate = invoiceDate;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Blob getInvoice() {
		return invoice;
	}

	public void setInvoice(Blob invoice) {
		this.invoice = invoice;
	}

	public long getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(long invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Override
	public String toString() {
		return "InvoiceEntity [invoiceId=" + invoiceId + ", invoice=" + invoice
				+ ", invoiceDate=" + invoiceDate + "]";
	}

	
	
}
