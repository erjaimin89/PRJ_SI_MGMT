package hibernate;

import java.math.BigDecimal;
import java.util.Set;
import java.util.SortedSet;

public class SalesOrderEntity {
	
	private long salesOrderId;
	private Integer shippingStatus;
	private Integer invoiceStatus;
	private Integer paymentStatus;
	private BigDecimal orderTotal;
	private BigDecimal discount;
	private BigDecimal subTotal;
	private BigDecimal total;
	private BigDecimal taxAmount1;
	private BigDecimal taxAmount2;
	private BigDecimal paidAmount;
	private BigDecimal remainingBalance;
	private String remarks;
	private String via;
	private String refNo;
	private String conditions;
	private long orderDate;
	private long shippingDate;
	private long updatedOn;
	private UserEntity user;
	private TaxEntity taxingScheme;
	private CustomerEntity customer;
	private SortedSet<SalesOrderDetailEntity> orderDetails;
	private SortedSet<PaymentEntity> paymentDetails;
	private InvoiceEntity invoice;
	
	public SalesOrderEntity() {
		super();
	}

	public SalesOrderEntity(Integer shippingStatus,
			Integer invoiceStatus, Integer paymentStatus, BigDecimal orderTotal, 
			BigDecimal discount, BigDecimal subTotal, BigDecimal total, BigDecimal taxAmount1, BigDecimal taxAmount2,
			BigDecimal paidAmount, BigDecimal remainingBalance, String remarks,
			String via, String refNo, String conditions, long shippingDate,
			long orderDate, long updatedOn, UserEntity user,
			TaxEntity taxingScheme, CustomerEntity customer,
			SortedSet<SalesOrderDetailEntity> orderDetails,
			SortedSet<PaymentEntity> paymentDetails) {
		super();
		this.shippingStatus = shippingStatus;
		this.invoiceStatus = invoiceStatus;
		this.paymentStatus = paymentStatus;
		this.orderTotal = orderTotal;
		this.discount = discount;
		this.subTotal = subTotal;
		this.total = total;
		this.taxAmount1 = taxAmount1;
		this.taxAmount2 = taxAmount2;
		this.paidAmount = paidAmount;
		this.remainingBalance = remainingBalance;
		this.remarks = remarks;
		this.via = via;
		this.refNo = refNo;
		this.conditions = conditions;
		this.orderDate = orderDate;
		this.shippingDate = shippingDate;
		this.updatedOn = updatedOn;
		this.user = user;
		this.taxingScheme = taxingScheme;
		this.customer = customer;
		this.orderDetails = orderDetails;
		this.paymentDetails = paymentDetails;
	}



	public long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public Integer getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(Integer shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTaxAmount1() {
		return taxAmount1;
	}

	public void setTaxAmount1(BigDecimal taxAmount1) {
		this.taxAmount1 = taxAmount1;
	}

	public BigDecimal getTaxAmount2() {
		return taxAmount2;
	}

	public void setTaxAmount2(BigDecimal taxAmount2) {
		this.taxAmount2 = taxAmount2;
	}

	public long getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(long orderDate) {
		this.orderDate = orderDate;
	}

	public long getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(long updatedOn) {
		this.updatedOn = updatedOn;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public TaxEntity getTaxingScheme() {
		return taxingScheme;
	}

	public void setTaxingScheme(TaxEntity taxingScheme) {
		this.taxingScheme = taxingScheme;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public Set<SalesOrderDetailEntity> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(SortedSet<SalesOrderDetailEntity> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public BigDecimal getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(BigDecimal remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SortedSet<PaymentEntity> getPaymentDetails() {
		return paymentDetails;
	}

	public long getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(long shippingDate) {
		this.shippingDate = shippingDate;
	}

	public void setPaymentDetails(SortedSet<PaymentEntity> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String getVia() {
		return via;
	}



	public void setVia(String via) {
		this.via = via;
	}



	public String getRefNo() {
		return refNo;
	}



	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}



	public String getConditions() {
		return conditions;
	}



	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	public InvoiceEntity getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceEntity invoice) {
		this.invoice = invoice;
	}



	@Override
	public String toString() {
		return "SalesOrderEntity [salesOrderId=" + salesOrderId
				+ ", shippingStatus=" + shippingStatus + ", invoiceStatus="
				+ invoiceStatus + ", paymentStatus=" + paymentStatus
				+ ", subTotal=" + subTotal + ", total=" + total
				+ ", taxAmount1=" + taxAmount1 + ", taxAmount2=" + taxAmount2
				+ ", paidAmount=" + paidAmount + ", remainingBalance="
				+ remainingBalance + ", remarks=" + remarks + ", via=" + via
				+ ", refNo=" + refNo + ", conditions=" + conditions
				+ ", InvoiceEntity=" + invoice + ", orderDate=" + orderDate
				 + ", shippingDate=" + shippingDate + ", updatedOn=" + updatedOn
				+ ", user=" + user + ", taxingScheme=" + taxingScheme
				+ ", customer=" + customer + ", orderDetails=" + orderDetails
				+ ", paymentDetails=" + paymentDetails + "]";
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}



	
	
}
