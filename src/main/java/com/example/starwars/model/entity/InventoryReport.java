package com.example.starwars.model.entity;

import java.util.HashMap;
import java.util.Map;

public class InventoryReport {
	private Map<InventoryItem, Float> items;

	public InventoryReport() {
		this.items = new HashMap<>();
	}

	public InventoryReport(Map<InventoryItem,Float> items) {
		this.items = items;
	}

	public Map<InventoryItem,Float> getItems() {
		return this.items;
	}

	public void setItems(Map<InventoryItem,Float> items) {
		this.items = items;
	}

	public InventoryReport items(Map<InventoryItem,Float> items) {
		setItems(items);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" items='" + getItems() + "'" +
			"}";
	}

	
}
