package com.example.starwars.model.entity.api;

import com.example.starwars.model.entity.Inventory;

public class ItemsTrade {
	private Inventory itemsOffer;
	private Inventory itemsReceive;


	public ItemsTrade() {
	}

	public ItemsTrade(Inventory itemsOffer, Inventory itemsReceive) {
		this.itemsOffer = itemsOffer;
		this.itemsReceive = itemsReceive;
	}

	public Inventory getItemsOffer() {
		return this.itemsOffer;
	}

	public void setItemsOffer(Inventory itemsOffer) {
		this.itemsOffer = itemsOffer;
	}

	public Inventory getItemsReceive() {
		return this.itemsReceive;
	}

	public void setItemsReceive(Inventory itemsReceive) {
		this.itemsReceive = itemsReceive;
	}

	public ItemsTrade itemsOffer(Inventory itemsOffer) {
		setItemsOffer(itemsOffer);
		return this;
	}

	public ItemsTrade itemsReceive(Inventory itemsReceive) {
		setItemsReceive(itemsReceive);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" itemsOffer='" + getItemsOffer() + "'" +
			", itemsReceive='" + getItemsReceive() + "'" +
			"}";
	}

}
