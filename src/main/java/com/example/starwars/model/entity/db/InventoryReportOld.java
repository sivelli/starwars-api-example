package com.example.starwars.model.entity.db;

import com.example.starwars.model.entity.InventoryItem;

public class InventoryReportOld {
	private InventoryItem item;
	private Long quantity;

	public InventoryReportOld() {
	}

	public InventoryReportOld(InventoryItem item, Long quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public InventoryItem getItem() {
		return this.item;
	}

	public void setItem(InventoryItem item) {
		this.item = item;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public InventoryReportOld item(InventoryItem item) {
		setItem(item);
		return this;
	}

	public InventoryReportOld quantity(Long quantity) {
		setQuantity(quantity);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" item='" + getItem() + "'" +
			", quantity='" + getQuantity() + "'" +
			"}";
	}

}
