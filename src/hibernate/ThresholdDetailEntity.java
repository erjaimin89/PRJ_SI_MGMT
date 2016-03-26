package hibernate;


public class ThresholdDetailEntity {

	private long thresholdId;
	private long partyId;
	private String partyName;
	private long itemId;
	private String itemName;
	private long itemDetailId;
	private String detailName;
	private Integer xsmallThreshold;
	private Integer smallThreshold;
	private Integer mediumThreshold;
	private Integer largeThreshold;
	private Integer xlargeThreshold;
	private Integer xxlargeThreshold;
	private Integer xxxlargeThreshold;
	private Integer mixThreshold;
	private Integer xsmallTarget;
	private Integer smallTarget;
	private Integer mediumTarget;
	private Integer largeTarget;
	private Integer xlargeTarget;
	private Integer xxlargeTarget;
	private Integer xxxlargeTarget;
	private Integer mixTarget;
	private long orderDate;
	private long updatedOn;
	
	public ThresholdDetailEntity() {
		super();
	}

	public ThresholdDetailEntity(long partyId, String partyName, long itemId, String itemName,
			long itemDetailId, String detailName, Integer xsmallThreshold,
			Integer smallThreshold, Integer mediumThreshold,
			Integer largeThreshold, Integer xlargeThreshold,
			Integer xxlargeThreshold, Integer xxxlargeThreshold,
			Integer mixThreshold, Integer xsmallTarget, Integer smallTarget,
			Integer mediumTarget, Integer largeTarget, Integer xlargeTarget,
			Integer xxlargeTarget, Integer xxxlargeTarget, Integer mixTarget,
			long orderDate, long updatedOn) {
		super();
		this.partyId = partyId;
		this.partyName = partyName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDetailId = itemDetailId;
		this.detailName = detailName;
		this.xsmallThreshold = xsmallThreshold;
		this.smallThreshold = smallThreshold;
		this.mediumThreshold = mediumThreshold;
		this.largeThreshold = largeThreshold;
		this.xlargeThreshold = xlargeThreshold;
		this.xxlargeThreshold = xxlargeThreshold;
		this.xxxlargeThreshold = xxxlargeThreshold;
		this.mixThreshold = mixThreshold;
		this.xsmallTarget = xsmallTarget;
		this.smallTarget = smallTarget;
		this.mediumTarget = mediumTarget;
		this.largeTarget = largeTarget;
		this.xlargeTarget = xlargeTarget;
		this.xxlargeTarget = xxlargeTarget;
		this.xxxlargeTarget = xxxlargeTarget;
		this.mixTarget = mixTarget;
		this.orderDate = orderDate;
		this.updatedOn = updatedOn;
	}

	public long getThresholdId() {
		return thresholdId;
	}

	public void setThresholdId(long thresholdId) {
		this.thresholdId = thresholdId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getItemDetailId() {
		return itemDetailId;
	}

	public void setItemDetailId(long itemDetailId) {
		this.itemDetailId = itemDetailId;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public Integer getXsmallThreshold() {
		return xsmallThreshold;
	}

	public void setXsmallThreshold(Integer xsmallThreshold) {
		this.xsmallThreshold = xsmallThreshold;
	}

	public Integer getSmallThreshold() {
		return smallThreshold;
	}

	public void setSmallThreshold(Integer smallThreshold) {
		this.smallThreshold = smallThreshold;
	}

	public Integer getMediumThreshold() {
		return mediumThreshold;
	}

	public void setMediumThreshold(Integer mediumThreshold) {
		this.mediumThreshold = mediumThreshold;
	}

	public Integer getLargeThreshold() {
		return largeThreshold;
	}

	public void setLargeThreshold(Integer largeThreshold) {
		this.largeThreshold = largeThreshold;
	}

	public Integer getXlargeThreshold() {
		return xlargeThreshold;
	}

	public void setXlargeThreshold(Integer xlargeThreshold) {
		this.xlargeThreshold = xlargeThreshold;
	}

	public Integer getXxlargeThreshold() {
		return xxlargeThreshold;
	}

	public void setXxlargeThreshold(Integer xxlargeThreshold) {
		this.xxlargeThreshold = xxlargeThreshold;
	}

	public Integer getXxxlargeThreshold() {
		return xxxlargeThreshold;
	}

	public void setXxxlargeThreshold(Integer xxxlargeThreshold) {
		this.xxxlargeThreshold = xxxlargeThreshold;
	}

	public Integer getMixThreshold() {
		return mixThreshold;
	}

	public void setMixThreshold(Integer mixThreshold) {
		this.mixThreshold = mixThreshold;
	}

	public Integer getXsmallTarget() {
		return xsmallTarget;
	}

	public void setXsmallTarget(Integer xsmallTarget) {
		this.xsmallTarget = xsmallTarget;
	}

	public Integer getSmallTarget() {
		return smallTarget;
	}

	public void setSmallTarget(Integer smallTarget) {
		this.smallTarget = smallTarget;
	}

	public Integer getMediumTarget() {
		return mediumTarget;
	}

	public void setMediumTarget(Integer mediumTarget) {
		this.mediumTarget = mediumTarget;
	}

	public Integer getLargeTarget() {
		return largeTarget;
	}

	public void setLargeTarget(Integer largeTarget) {
		this.largeTarget = largeTarget;
	}

	public Integer getXlargeTarget() {
		return xlargeTarget;
	}

	public void setXlargeTarget(Integer xlargeTarget) {
		this.xlargeTarget = xlargeTarget;
	}

	public Integer getXxlargeTarget() {
		return xxlargeTarget;
	}

	public void setXxlargeTarget(Integer xxlargeTarget) {
		this.xxlargeTarget = xxlargeTarget;
	}

	public Integer getXxxlargeTarget() {
		return xxxlargeTarget;
	}

	public void setXxxlargeTarget(Integer xxxlargeTarget) {
		this.xxxlargeTarget = xxxlargeTarget;
	}

	public Integer getMixTarget() {
		return mixTarget;
	}

	public void setMixTarget(Integer mixTarget) {
		this.mixTarget = mixTarget;
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
		return "ThresholdDetailEntity [thresholdId=" + thresholdId
				+ ", itemId=" + itemId + ", itemName=" + itemName
				+ ", itemDetailId=" + itemDetailId + ", detailName="
				+ detailName + ", xsmallThreshold=" + xsmallThreshold
				+ ", smallThreshold=" + smallThreshold + ", mediumThreshold="
				+ mediumThreshold + ", largeThreshold=" + largeThreshold
				+ ", xlargeThreshold=" + xlargeThreshold
				+ ", xxlargeThreshold=" + xxlargeThreshold
				+ ", xxxlargeThreshold=" + xxxlargeThreshold
				+ ", mixThreshold=" + mixThreshold + ", xsmallTarget="
				+ xsmallTarget + ", smallTarget=" + smallTarget
				+ ", mediumTarget=" + mediumTarget + ", largeTarget="
				+ largeTarget + ", xlargeTarget=" + xlargeTarget
				+ ", xxlargeTarget=" + xxlargeTarget + ", xxxlargeTarget="
				+ xxxlargeTarget + ", mixTarget=" + mixTarget + ", orderDate="
				+ orderDate + ", updatedOn=" + updatedOn + "]";
	}

}
