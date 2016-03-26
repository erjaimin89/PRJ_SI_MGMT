package gui;

import hibernate.ProductDetailEntity;

import java.util.Map;

import main.ComboItem;

public class ComboItems {
	
	private ComboItem party;
	private ComboItem item;
	private Map<ComboItem, ProductDetailEntity> detailMap;
	private boolean selectAll;
	
	public ComboItem getItem() {
		return item;
	}
	public void setItem(ComboItem item) {
		this.item = item;
	}
	public Map<ComboItem, ProductDetailEntity> getDetailMap() {
		return detailMap;
	}
	public void setDetailMap(Map<ComboItem, ProductDetailEntity> detail) {
		this.detailMap = detail;
	}
	public ComboItem getParty() {
		return party;
	}
	public void setParty(ComboItem party) {
		this.party = party;
	}
	public boolean isSelectAll() {
		return selectAll;
	}
	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

}
