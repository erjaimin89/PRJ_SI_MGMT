package hibernate;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentEntity implements Serializable, Comparable<PaymentEntity>{

	private static final long serialVersionUID = 3854508345409919006L;
	private long paymentId;
	private BigDecimal amount;
	private Integer type;
	private long paymentDate;
	
	public PaymentEntity() {
	}

	public PaymentEntity( BigDecimal amount, Integer type,
			long paymentDate) {
		super();
		this.amount = amount;
		this.type = type;
		this.paymentDate = paymentDate;
	}

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public long getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(long paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return "PaymentEntity [paymentId=" + paymentId + ", amount=" + amount
				+ ", type=" + type + ", paymentDate=" + paymentDate + "]";
	}

	@Override
	public int compareTo(PaymentEntity o) {
		if(this.paymentDate < o.paymentDate)
			return -1;
		if(this.paymentDate == o.paymentDate)
			return 0;
		return 1;
	}
	
	
	
}
