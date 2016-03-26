package hibernate;

import java.util.SortedSet;


public class ProductEntity {
	
	private long itemId;
	private String itemName;
	private long partyId;
	private String partyName;
	private Integer quantity;
	private Integer transitQuantity;
	private Integer productionQuantity;
	private String itemLabel;
	private String itemStyle;
	private String itemContents;
	private String itemWeight;
	private String itemPolyBag;
	private String itemBox;
	private String itemColors;
	private long createdOn;
	private long updatedOn;
	private SortedSet<ProductDetailEntity> details;

	public ProductEntity() {
		super();
	}

	public ProductEntity(String itemName, long partyId, String partyName,
			Integer quantity, Integer transitQuantity,
			Integer productionQuantity, String itemLabel, String itemStyle,
			String itemContents, String itemWeight, String itemPolyBag,
			String itemBox, String itemColors, long createdOn, long updatedOn) {
		super();
		this.itemName = itemName;
		this.partyId = partyId;
		this.partyName = partyName;
		this.quantity = quantity;
		this.transitQuantity = transitQuantity;
		this.productionQuantity = productionQuantity;
		this.itemLabel = itemLabel;
		this.itemStyle = itemStyle;
		this.itemContents = itemContents;
		this.itemWeight = itemWeight;
		this.itemPolyBag = itemPolyBag;
		this.itemBox = itemBox;
		this.itemColors = itemColors;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getItemLabel() {
		return itemLabel;
	}



	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}



	public String getItemStyle() {
		return itemStyle;
	}



	public void setItemStyle(String itemStyle) {
		this.itemStyle = itemStyle;
	}



	public Integer getTransitQuantity() {
		return transitQuantity;
	}

	public void setTransitQuantity(Integer transitQuantity) {
		this.transitQuantity = transitQuantity;
	}

	public Integer getProductionQuantity() {
		return productionQuantity;
	}

	public void setProductionQuantity(Integer productionQuantity) {
		this.productionQuantity = productionQuantity;
	}

	public String getItemContents() {
		return itemContents;
	}



	public void setItemContents(String itemContents) {
		this.itemContents = itemContents;
	}



	public String getItemWeight() {
		return itemWeight;
	}



	public void setItemWeight(String itemWeight) {
		this.itemWeight = itemWeight;
	}



	public String getItemPolyBag() {
		return itemPolyBag;
	}



	public void setItemPolyBag(String itemPolyBag) {
		this.itemPolyBag = itemPolyBag;
	}



	public String getItemBox() {
		return itemBox;
	}



	public void setItemBox(String itemBox) {
		this.itemBox = itemBox;
	}



	public String getItemColors() {
		return itemColors;
	}



	public void setItemColors(String itemColors) {
		this.itemColors = itemColors;
	}



	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
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




	public SortedSet<ProductDetailEntity> getDetails() {
		return details;
	}

	public void setDetails(SortedSet<ProductDetailEntity> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ProductEntity [itemId=" + itemId + ", itemName=" + itemName
				+ ", partyId=" + partyId + ", partyName=" + partyName
				+ ", quantity=" + quantity + ", transitQuantity="
				+ transitQuantity + ", productionQuantity="
				+ productionQuantity + ", itemLabel=" + itemLabel
				+ ", itemStyle=" + itemStyle + ", itemContents=" + itemContents
				+ ", itemWeight=" + itemWeight + ", itemPolyBag=" + itemPolyBag
				+ ", itemBox=" + itemBox + ", itemColors=" + itemColors
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", details=" + details + "]";
	}
}
