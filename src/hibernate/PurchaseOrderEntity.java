package hibernate;

import java.util.SortedSet;

public class PurchaseOrderEntity {
	
	private long purchaseOrderId;
	private String lotNo;
	private Integer shippingStatus;
	private Integer totalItemQuantity;
	private long arrivalDate;
	private long orderDate;
	private long updatedOn;
	private SortedSet<PurchaseOrderDetailEntity> orderDetails;
	
	public PurchaseOrderEntity() {
		super();
	}

	public PurchaseOrderEntity(String lotNo, Integer shippingStatus, Integer totalItemQuantity, long arrivalDate, long orderDate,
			long updatedOn, SortedSet<PurchaseOrderDetailEntity> orderDetails) {
		super();
		this.setLotNo(lotNo);
		this.shippingStatus = shippingStatus;
		this.totalItemQuantity = totalItemQuantity;
		this.setArrivalDate(arrivalDate);
		this.orderDate = orderDate;
		this.updatedOn = updatedOn;
		this.orderDetails = orderDetails;
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

	public long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public Integer getTotalItemQuantity() {
		return totalItemQuantity;
	}

	public void setTotalItemQuantity(Integer totalItemQuentity) {
		this.totalItemQuantity = totalItemQuentity;
	}

	public SortedSet<PurchaseOrderDetailEntity> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(SortedSet<PurchaseOrderDetailEntity> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public long getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(long arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Override
	public String toString() {
		return "PurchaseOrderEntity [purchaseOrderId=" + purchaseOrderId
				+ ", lotNo=" + lotNo + ", shippingStatus=" + shippingStatus
				+ ", totalItemQuentity=" + totalItemQuantity + ", arrivalDate="
				+ arrivalDate + ", orderDate=" + orderDate + ", updatedOn="
				+ updatedOn + ", orderDetails=" + orderDetails + "]";
	}

	
}
