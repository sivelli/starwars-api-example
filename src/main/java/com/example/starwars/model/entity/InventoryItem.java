package com.example.starwars.model.entity;

import java.util.Optional;

public enum InventoryItem {
	WEAPON(4),
	AMMO(3),
	WATER(2),
	FOOD(1),
	;
	private int points;

	private InventoryItem(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public static Optional<InventoryItem> fromName(String name) {
		for (InventoryItem item: values()) {
			if (item.name().equalsIgnoreCase(name)) {
				return Optional.of(item);
			}
		}
		return Optional.empty();
	}

}
