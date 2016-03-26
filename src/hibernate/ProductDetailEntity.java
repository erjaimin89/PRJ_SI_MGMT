package hibernate;

public class ProductDetailEntity implements Comparable<ProductDetailEntity>{
	
	private long itemDetailId;
	private String name;
	private Integer xsmall;
	private Integer small;
	private Integer medium;
	private Integer large;
	private Integer xlarge;
	private Integer xxlarge;
	private Integer xxxlarge;
	private Integer mix;
	private Integer subTotal;
	private Integer res_xsmall;
	private Integer res_small;
	private Integer res_medium;
	private Integer res_large;
	private Integer res_xlarge;
	private Integer res_xxlarge;
	private Integer res_xxxlarge;
	private Integer res_mix;
	private Integer trans_xsmall;
	private Integer trans_small;
	private Integer trans_medium;
	private Integer trans_large;
	private Integer trans_xlarge;
	private Integer trans_xxlarge;
	private Integer trans_xxxlarge;
	private Integer trans_mix;
	private Integer total_transit;
	private Integer prod_xsmall;
	private Integer prod_small;
	private Integer prod_medium;
	private Integer prod_large;
	private Integer prod_xlarge;
	private Integer prod_xxlarge;
	private Integer prod_xxxlarge;
	private Integer prod_mix;
	private Integer total_production;
	
	public ProductDetailEntity() {
		super();
	}

	public ProductDetailEntity(String name, Integer xsmall, Integer small,
			Integer medium, Integer large, Integer xlarge, Integer xxlarge,
			Integer xxxlarge, Integer mix, Integer subTotal,
			Integer res_xsmall, Integer res_small, Integer res_medium,
			Integer res_large, Integer res_xlarge, Integer res_xxlarge,
			Integer res_xxxlarge, Integer res_mix, Integer trans_xsmall,
			Integer trans_small, Integer trans_medium, Integer trans_large,
			Integer trans_xlarge, Integer trans_xxlarge,
			Integer trans_xxxlarge, Integer trans_mix, Integer total_transit,
			Integer prod_xsmall, Integer prod_small, Integer prod_medium,
			Integer prod_large, Integer prod_xlarge, Integer prod_xxlarge,
			Integer prod_xxxlarge, Integer prod_mix, Integer total_production) {
		super();
		this.name = name;
		this.xsmall = xsmall;
		this.small = small;
		this.medium = medium;
		this.large = large;
		this.xlarge = xlarge;
		this.xxlarge = xxlarge;
		this.xxxlarge = xxxlarge;
		this.mix = mix;
		this.subTotal = subTotal;
		this.res_xsmall = res_xsmall;
		this.res_small = res_small;
		this.res_medium = res_medium;
		this.res_large = res_large;
		this.res_xlarge = res_xlarge;
		this.res_xxlarge = res_xxlarge;
		this.res_xxxlarge = res_xxxlarge;
		this.res_mix = res_mix;
		this.trans_xsmall = trans_xsmall;
		this.trans_small = trans_small;
		this.trans_medium = trans_medium;
		this.trans_large = trans_large;
		this.trans_xlarge = trans_xlarge;
		this.trans_xxlarge = trans_xxlarge;
		this.trans_xxxlarge = trans_xxxlarge;
		this.trans_mix = trans_mix;
		this.total_transit = total_transit;
		this.prod_xsmall = prod_xsmall;
		this.prod_small = prod_small;
		this.prod_medium = prod_medium;
		this.prod_large = prod_large;
		this.prod_xlarge = prod_xlarge;
		this.prod_xxlarge = prod_xxlarge;
		this.prod_xxxlarge = prod_xxxlarge;
		this.prod_mix = prod_mix;
		this.total_production = total_production;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}

	public long getItemDetailId() {
		return itemDetailId;
	}

	public void setItemDetailId(long itemDetailId) {
		this.itemDetailId = itemDetailId;
	}
	
	 public Integer getRes_xsmall() {
		return res_xsmall;
	}

	public void setRes_xsmall(Integer res_xsmall) {
		this.res_xsmall = res_xsmall;
	}

	public Integer getRes_small() {
		return res_small;
	}

	public void setRes_small(Integer res_small) {
		this.res_small = res_small;
	}

	public Integer getRes_medium() {
		return res_medium;
	}

	public void setRes_medium(Integer res_medium) {
		this.res_medium = res_medium;
	}

	public Integer getRes_large() {
		return res_large;
	}

	public void setRes_large(Integer res_large) {
		this.res_large = res_large;
	}

	public Integer getRes_xlarge() {
		return res_xlarge;
	}

	public void setRes_xlarge(Integer res_xlarge) {
		this.res_xlarge = res_xlarge;
	}

	public Integer getRes_xxlarge() {
		return res_xxlarge;
	}

	public void setRes_xxlarge(Integer res_xxlarge) {
		this.res_xxlarge = res_xxlarge;
	}

	public Integer getRes_xxxlarge() {
		return res_xxxlarge;
	}

	public void setRes_xxxlarge(Integer res_xxxlarge) {
		this.res_xxxlarge = res_xxxlarge;
	}

	public Integer getRes_mix() {
		return res_mix;
	}

	public void setRes_mix(Integer res_mix) {
		this.res_mix = res_mix;
	}

	public Integer getTrans_xsmall() {
		return trans_xsmall;
	}

	public void setTrans_xsmall(Integer trans_xsmall) {
		this.trans_xsmall = trans_xsmall;
	}

	public Integer getTrans_small() {
		return trans_small;
	}

	public void setTrans_small(Integer trans_small) {
		this.trans_small = trans_small;
	}

	public Integer getTrans_medium() {
		return trans_medium;
	}

	public void setTrans_medium(Integer trans_medium) {
		this.trans_medium = trans_medium;
	}

	public Integer getTrans_large() {
		return trans_large;
	}

	public void setTrans_large(Integer trans_large) {
		this.trans_large = trans_large;
	}

	public Integer getTrans_xlarge() {
		return trans_xlarge;
	}

	public void setTrans_xlarge(Integer trans_xlarge) {
		this.trans_xlarge = trans_xlarge;
	}

	public Integer getTrans_xxlarge() {
		return trans_xxlarge;
	}

	public void setTrans_xxlarge(Integer trans_xxlarge) {
		this.trans_xxlarge = trans_xxlarge;
	}

	public Integer getTrans_xxxlarge() {
		return trans_xxxlarge;
	}

	public void setTrans_xxxlarge(Integer trans_xxxlarge) {
		this.trans_xxxlarge = trans_xxxlarge;
	}

	public Integer getTrans_mix() {
		return trans_mix;
	}

	public void setTrans_mix(Integer trans_mix) {
		this.trans_mix = trans_mix;
	}

	public Integer getTotal_transit() {
		return total_transit;
	}

	public void setTotal_transit(Integer total_transit) {
		this.total_transit = total_transit;
	}

	public Integer getProd_xsmall() {
		return prod_xsmall;
	}

	public void setProd_xsmall(Integer prod_xsmall) {
		this.prod_xsmall = prod_xsmall;
	}

	public Integer getProd_small() {
		return prod_small;
	}

	public void setProd_small(Integer prod_small) {
		this.prod_small = prod_small;
	}

	public Integer getProd_medium() {
		return prod_medium;
	}

	public void setProd_medium(Integer prod_medium) {
		this.prod_medium = prod_medium;
	}

	public Integer getProd_large() {
		return prod_large;
	}

	public void setProd_large(Integer prod_large) {
		this.prod_large = prod_large;
	}

	public Integer getProd_xlarge() {
		return prod_xlarge;
	}

	public void setProd_xlarge(Integer prod_xlarge) {
		this.prod_xlarge = prod_xlarge;
	}

	public Integer getProd_xxlarge() {
		return prod_xxlarge;
	}

	public void setProd_xxlarge(Integer prod_xxlarge) {
		this.prod_xxlarge = prod_xxlarge;
	}

	public Integer getProd_xxxlarge() {
		return prod_xxxlarge;
	}

	public void setProd_xxxlarge(Integer prod_xxxlarge) {
		this.prod_xxxlarge = prod_xxxlarge;
	}

	public Integer getProd_mix() {
		return prod_mix;
	}

	public void setProd_mix(Integer prod_mix) {
		this.prod_mix = prod_mix;
	}

	public Integer getTotal_production() {
		return total_production;
	}

	public void setTotal_production(Integer total_production) {
		this.total_production = total_production;
	}

	public boolean equals(Object obj) {
	      if (obj == null) return false;
	      if (!this.getClass().equals(obj.getClass())) return false;

	      ProductDetailEntity obj2 = (ProductDetailEntity)obj;
	      if((this.itemDetailId == obj2.getItemDetailId()) && (this.name.equals(obj2.getName()))) {
	         return true;
	      }
	      return false;
	   }
	   public int hashCode() {
	      int tmp = 0;
	      tmp = ( itemDetailId + name ).hashCode();
	      return tmp;
	   }

	@Override
	public String toString() {
		return "ProductDetailEntity [itemDetailId=" + itemDetailId + ", name="
				+ name + ", xsmall=" + xsmall + ", small=" + small
				+ ", medium=" + medium + ", large=" + large + ", xlarge="
				+ xlarge + ", xxlarge=" + xxlarge + ", xxxlarge=" + xxxlarge
				+ ", mix=" + mix + ", subTotal=" + subTotal + ", res_xsmall="
				+ res_xsmall + ", res_small=" + res_small + ", res_medium="
				+ res_medium + ", res_large=" + res_large + ", res_xlarge="
				+ res_xlarge + ", res_xxlarge=" + res_xxlarge
				+ ", res_xxxlarge=" + res_xxxlarge + ", res_mix=" + res_mix
				+ ", trans_xsmall=" + trans_xsmall + ", trans_small="
				+ trans_small + ", trans_medium=" + trans_medium
				+ ", trans_large=" + trans_large + ", trans_xlarge="
				+ trans_xlarge + ", trans_xxlarge=" + trans_xxlarge
				+ ", trans_xxxlarge=" + trans_xxxlarge + ", trans_mix="
				+ trans_mix + ", total_transit=" + total_transit
				+ ", prod_xsmall=" + prod_xsmall + ", prod_small=" + prod_small
				+ ", prod_medium=" + prod_medium + ", prod_large=" + prod_large
				+ ", prod_xlarge=" + prod_xlarge + ", prod_xxlarge="
				+ prod_xxlarge + ", prod_xxxlarge=" + prod_xxxlarge
				+ ", prod_mix=" + prod_mix + ", total_production="
				+ total_production + "]";
	}

	@Override
	public int compareTo(ProductDetailEntity o) {
		return this.name.compareTo(o.name);
	}



	

}
