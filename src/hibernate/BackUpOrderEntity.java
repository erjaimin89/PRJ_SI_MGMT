package hibernate;

import java.util.Set;

public class BackUpOrderEntity {
	
	private long backUpOrderId;
	private Integer totalItemQuantity;
	private long arrivalDate;
	private Integer shippingStatus;
	private long orderDate;
	private long updatedOn;
	private String remarks;
	private Set<BackUpOrderDetailEntity> orderDetails;
	
	public BackUpOrderEntity() {
		super();
	}

	
	public BackUpOrderEntity(Integer totalItemQuantity, long arrivalDate, Integer shippingStatus,
			long orderDate, long updatedOn,
			Set<BackUpOrderDetailEntity> orderDetails, String remarks) {
		super();
		this.totalItemQuantity = totalItemQuantity;
		this.arrivalDate = arrivalDate;
		this.shippingStatus = shippingStatus;
		this.orderDate = orderDate;
		this.updatedOn = updatedOn;
		this.orderDetails = orderDetails;
		this.remarks = remarks;
	}


	public long getBackUpOrderId() {
		return backUpOrderId;
	}

	public void setBackUpOrderId(long backUpOrderId) {
		this.backUpOrderId = backUpOrderId;
	}

	public Integer getTotalItemQuantity() {
		return totalItemQuantity;
	}

	public void setTotalItemQuantity(Integer totalItemQuantity) {
		this.totalItemQuantity = totalItemQuantity;
	}

	public long getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(long arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Integer getShippingStatus() {
		return shippingStatus;
	}


	public void setShippingStatus(Integer shippingStatus) {
		this.shippingStatus = shippingStatus;
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

	public Set<BackUpOrderDetailEntity> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<BackUpOrderDetailEntity> orderDetails) {
		this.orderDetails = orderDetails;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	@Override
	public String toString() {
		return "BackUpOrderEntity [backUpOrderId=" + backUpOrderId
				+ ", totalItemQuantity=" + totalItemQuantity + ", arrivalDate="
				+ arrivalDate + ", shippingStatus=" + shippingStatus
				+ ", orderDate=" + orderDate + ", updatedOn=" + updatedOn
				+ ", remarks=" + remarks + ", orderDetails=" + orderDetails
				+ "]";
	}
		
}
