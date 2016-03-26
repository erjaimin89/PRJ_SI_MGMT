package hibernate;

import java.math.BigDecimal;

public class SalesOrderDetailEntity implements Comparable<SalesOrderDetailEntity>{

	private long orderDetailId;
	private long partyId;
	private String partyName;
	private long itemId;
	private String itemName;
	private long itemDetailId;
	private String detailName;
	private Integer xsmall;
	private Integer small;
	private Integer medium;
	private Integer large;
	private Integer xlarge;
	private Integer xxlarge;
	private Integer xxxlarge;
	private Integer mix;
	private Integer totalQuantity;
	private BigDecimal unitPrice;
	private BigDecimal subTotal;
	
	public SalesOrderDetailEntity() {
		super();
	}

	public SalesOrderDetailEntity(long partyId, String partyName, long itemId, String itemName, long itemDetailId, String detailName,
			Integer xsmall, Integer small, Integer medium, Integer large,
			Integer xlarge, Integer xxlarge, Integer xxxlarge, Integer mix,
			Integer totalQuantity, BigDecimal unitPrice, BigDecimal subTotal) {
		super();
		this.partyId = partyId;
		this.partyName = partyName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDetailId = itemDetailId;
		this.detailName = detailName;
		this.xsmall = xsmall;
		this.small = small;
		this.medium = medium;
		this.large = large;
		this.xlarge = xlarge;
		this.xxlarge = xxlarge;
		this.xxxlarge = xxxlarge;
		this.mix = mix;
		this.totalQuantity = totalQuantity;
		this.unitPrice = unitPrice;
		this.subTotal = subTotal;
	}

	public long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getItemDetailId() {
		return itemDetailId;
	}

	public void setItemDetailId(long itemDetailId) {
		this.itemDetailId = itemDetailId;
	}

	public Integer getXsmall() {
		return xsmall;
	}

	public void setXsmall(Integer xsmall) {
		this.xsmall = xsmall;
	}

	public Integer getSmall() {
		return small;
	}

	public void setSmall(Integer small) {
		this.small = small;
	}

	public Integer getMedium() {
		return medium;
	}

	public void setMedium(Integer medium) {
		this.medium = medium;
	}

	public Integer getLarge() {
		return large;
	}

	public void setLarge(Integer large) {
		this.large = large;
	}

	public Integer getXlarge() {
		return xlarge;
	}

	public void setXlarge(Integer xlarge) {
		this.xlarge = xlarge;
	}

	public Integer getXxlarge() {
		return xxlarge;
	}

	public void setXxlarge(Integer xxlarge) {
		this.xxlarge = xxlarge;
	}

	public Integer getXxxlarge() {
		return xxxlarge;
	}

	public void setXxxlarge(Integer xxxlarge) {
		this.xxxlarge = xxxlarge;
	}

	public Integer getMix() {
		return mix;
	}

	public void setMix(Integer mix) {
		this.mix = mix;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public long getPartyId() {
		return partyId;
	}

	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	@Override
	public String toString() {
		return "SalesOrderDetailEntity [orderDetailId=" + orderDetailId
				+ ", partyId=" + partyId + ", partyName=" + partyName
				+ ", itemId=" + itemId + ", itemName=" + itemName
				+ ", itemDetailId=" + itemDetailId + ", detailName="
				+ detailName + ", xsmall=" + xsmall + ", small=" + small
				+ ", medium=" + medium + ", large=" + large + ", xlarge="
				+ xlarge + ", xxlarge=" + xxlarge + ", xxxlarge=" + xxxlarge
				+ ", mix=" + mix + ", totalQuantity=" + totalQuantity
				+ ", unitPrice=" + unitPrice + ", subTotal=" + subTotal + "]";
	}

	@Override
	public int compareTo(SalesOrderDetailEntity o) {
		 int i = this.partyName.compareTo(o.partyName);
		 if(i != 0) return i;
		 
		 i = this.itemName.compareTo(o.itemName);
		 if(i != 0) return i;
		 
		 return this.detailName.compareTo(o.detailName);
	}

}
