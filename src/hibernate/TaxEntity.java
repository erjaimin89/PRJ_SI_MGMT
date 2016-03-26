package hibernate;

public class TaxEntity {

	private long taxId;
	private String taxSchemeName;
	private String primaryTaxName;
	private double primaryTaxRate;
	private String secondaryTaxName;
	private double secondaryTaxRate;
	private boolean isSecondaryCompound;
	private long createdOn;
	private long updatedOn;

	public TaxEntity() {
		super();
	}

	public TaxEntity(String taxSchemeName, String primaryTaxName,
			double primaryTaxRate, String secondaryTaxName,
			double secondaryTaxRate, boolean isSecondaryCompound,
			long createdOn, long updatedOn) {
		super();
		this.taxSchemeName = taxSchemeName;
		this.primaryTaxName = primaryTaxName;
		this.primaryTaxRate = primaryTaxRate;
		this.secondaryTaxName = secondaryTaxName;
		this.secondaryTaxRate = secondaryTaxRate;
		this.isSecondaryCompound = isSecondaryCompound;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public long getTaxId() {
		return taxId;
	}

	public void setTaxId(long taxId) {
		this.taxId = taxId;
	}

	public String getTaxSchemeName() {
		return taxSchemeName;
	}

	public void setTaxSchemeName(String taxSchemeName) {
		this.taxSchemeName = taxSchemeName;
	}

	public String getPrimaryTaxName() {
		return primaryTaxName;
	}

	public void setPrimaryTaxName(String primaryTaxName) {
		this.primaryTaxName = primaryTaxName;
	}

	public double getPrimaryTaxRate() {
		return primaryTaxRate;
	}

	public void setPrimaryTaxRate(double primaryTaxRate) {
		this.primaryTaxRate = primaryTaxRate;
	}

	public String getSecondaryTaxName() {
		return secondaryTaxName;
	}

	public void setSecondaryTaxName(String secondaryTaxName) {
		this.secondaryTaxName = secondaryTaxName;
	}

	public double getSecondaryTaxRate() {
		return secondaryTaxRate;
	}

	public void setSecondaryTaxRate(double secondaryTaxRate) {
		this.secondaryTaxRate = secondaryTaxRate;
	}

	public boolean getIsSecondaryCompound() {
		return isSecondaryCompound;
	}

	public void setIsSecondaryCompound(boolean isSecondaryCompound) {
		this.isSecondaryCompound = isSecondaryCompound;
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

}
