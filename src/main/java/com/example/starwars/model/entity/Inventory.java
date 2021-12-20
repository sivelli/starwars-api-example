package com.example.starwars.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.data.util.Pair;

public class Inventory {
	private Map<InventoryItem, Long> items;

	public Inventory() {
		this.items = new HashMap<>();
	}

	public boolean addItemIfValid(InventoryItem item, long quantity) {
		if (quantity == 0) {
			return true;
		}
		Long currentQuantity = items.getOrDefault(item, 0L);
		if (currentQuantity + quantity < 0) {
			return false;
		}
		else {
			if (currentQuantity + quantity > 0) {
				items.put(item, currentQuantity + quantity);
			}
			else {
				items.remove(item);
			}
			return true;
		}
	}

	public Inventory item(InventoryItem item, long quantity) {
		addItemNoCheck(item, quantity);
		return this;
	}

	public void addItemNoCheck(InventoryItem item, long quantity) {
		items.compute(item, (i, q) -> {
			long newQuantity = Objects.requireNonNullElse(q, 0L) + quantity;
			return (newQuantity > 0 ? newQuantity : null);
		});
	}

	public boolean hasItem(InventoryItem item, long quantity) {
		if (quantity <= 0) {
			return true;
		}
		Long currentQuantity = items.get(item);
		return (currentQuantity != null && currentQuantity >= quantity);
	}

	public long items(InventoryItem item) {
		return items.getOrDefault(item, 0L);
	}

	public int size() {
		return items.size();
	}

	public Stream<Pair<InventoryItem, Long>> stream() {
		return items.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue()));
	}

	public long totalPoints() {
		return stream().mapToLong(itemQuantity -> itemQuantity.getFirst().getPoints() * itemQuantity.getSecond()).sum();
	}

	public static Inventory merge(Inventory inventory1, Inventory inventory2) {
		Inventory merged = new Inventory();
		Stream.concat(inventory1.stream(), inventory2.stream()).forEach(itemQuantity -> merged.addItemNoCheck(itemQuantity.getFirst(), itemQuantity.getSecond()));
		return merged;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Inventory)) {
			return false;
		}
		Inventory inventory = (Inventory) o;
		return Objects.equals(items, inventory.items);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(items);
	}

	public Map<InventoryItem,Long> getItems() {
		return this.items;
	}

	public void setItems(Map<InventoryItem,Long> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "{" +
			" items='" + items + "'" +
			"}";
	}

}
