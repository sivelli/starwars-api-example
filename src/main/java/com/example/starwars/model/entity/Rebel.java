package com.example.starwars.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.data.util.Pair;

public class Rebel {
	private Integer rebelId;

	private String name;

	private String gender;

	private LocalDate birth;

	private Location location;

	private LocalDateTime locationUpdatedAt;

	private Integer traitorReports;

	private Inventory inventory;

	private LocalDateTime createdAt;

	public Stream<Pair<InventoryItem, Long>> streamItems() {
		return inventory == null? Stream.empty() : inventory.stream();
	}

	public long totalItemPoints() {
		return inventory == null? 0 : inventory.totalPoints();
	}

	public void changeItemQuantity(InventoryItem item, long quantity) {
		if (inventory == null) {
			inventory = new Inventory();
		}
		inventory.addItemNoCheck(item, quantity);
	}

	public Integer getAge(LocalDate now) {
		if (getBirth() != null) {
			return (int)ChronoUnit.YEARS.between(getBirth(), now);
		}
		return null;
	}

	// Start of auto generated



	public Rebel() {
	}

	public Rebel(Integer rebelId, String name, String gender, LocalDate birth, Location location, LocalDateTime locationUpdatedAt, Integer traitorReports, Inventory inventory, LocalDateTime createdAt) {
		this.rebelId = rebelId;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.location = location;
		this.locationUpdatedAt = locationUpdatedAt;
		this.traitorReports = traitorReports;
		this.inventory = inventory;
		this.createdAt = createdAt;
	}

	public Integer getRebelId() {
		return this.rebelId;
	}

	public void setRebelId(Integer rebelId) {
		this.rebelId = rebelId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirth() {
		return this.birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public LocalDateTime getLocationUpdatedAt() {
		return this.locationUpdatedAt;
	}

	public void setLocationUpdatedAt(LocalDateTime locationUpdatedAt) {
		this.locationUpdatedAt = locationUpdatedAt;
	}

	public Integer getTraitorReports() {
		return this.traitorReports;
	}

	public void setTraitorReports(Integer traitorReports) {
		this.traitorReports = traitorReports;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Rebel rebelId(Integer rebelId) {
		setRebelId(rebelId);
		return this;
	}

	public Rebel name(String name) {
		setName(name);
		return this;
	}

	public Rebel gender(String gender) {
		setGender(gender);
		return this;
	}

	public Rebel birth(LocalDate birth) {
		setBirth(birth);
		return this;
	}

	public Rebel location(Location location) {
		setLocation(location);
		return this;
	}

	public Rebel locationUpdatedAt(LocalDateTime locationUpdatedAt) {
		setLocationUpdatedAt(locationUpdatedAt);
		return this;
	}

	public Rebel traitorReports(Integer traitorReports) {
		setTraitorReports(traitorReports);
		return this;
	}

	public Rebel inventory(Inventory inventory) {
		setInventory(inventory);
		return this;
	}

	public Rebel createdAt(LocalDateTime createdAt) {
		setCreatedAt(createdAt);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Rebel)) {
			return false;
		}
		Rebel rebel = (Rebel) o;
		return Objects.equals(rebelId, rebel.rebelId) && Objects.equals(name, rebel.name) && Objects.equals(gender, rebel.gender) && Objects.equals(birth, rebel.birth) && Objects.equals(location, rebel.location) && Objects.equals(locationUpdatedAt, rebel.locationUpdatedAt) && Objects.equals(traitorReports, rebel.traitorReports) && Objects.equals(inventory, rebel.inventory) && Objects.equals(createdAt, rebel.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rebelId, name, gender, birth, location, locationUpdatedAt, traitorReports, inventory, createdAt);
	}

	@Override
	public String toString() {
		return "{" +
			" rebelId='" + getRebelId() + "'" +
			", name='" + getName() + "'" +
			", gender='" + getGender() + "'" +
			", birth='" + getBirth() + "'" +
			", location='" + getLocation() + "'" +
			", locationUpdatedAt='" + getLocationUpdatedAt() + "'" +
			", traitorReports='" + getTraitorReports() + "'" +
			", inventory='" + getInventory() + "'" +
			", createdAt='" + getCreatedAt() + "'" +
			"}";
	}

}
