package com.example.starwars.model.entity.api;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.starwars.config.AppConfig;
import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.Location;
import com.fasterxml.jackson.annotation.JsonFormat;

public class RebelApi {
	private Integer rebelId;

	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConfig.API_DATE_FORMAT)
	private LocalDate birth;

	private Integer age;

	private Location location;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConfig.API_DATETIME_FORMAT)
	private LocalDateTime locationUpdatedAt;

	private Integer traitorReports;

	private Inventory inventory;


	public RebelApi() {
	}

	public RebelApi(Integer rebelId, String name, LocalDate birth, Integer age, Location location, LocalDateTime locationUpdatedAt, Integer traitorReports, Inventory inventory) {
		this.rebelId = rebelId;
		this.name = name;
		this.birth = birth;
		this.age = age;
		this.location = location;
		this.locationUpdatedAt = locationUpdatedAt;
		this.traitorReports = traitorReports;
		this.inventory = inventory;
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

	public LocalDate getBirth() {
		return this.birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public RebelApi rebelId(Integer rebelId) {
		setRebelId(rebelId);
		return this;
	}

	public RebelApi name(String name) {
		setName(name);
		return this;
	}

	public RebelApi birth(LocalDate birth) {
		setBirth(birth);
		return this;
	}

	public RebelApi age(Integer age) {
		setAge(age);
		return this;
	}

	public RebelApi location(Location location) {
		setLocation(location);
		return this;
	}

	public RebelApi locationUpdatedAt(LocalDateTime locationUpdatedAt) {
		setLocationUpdatedAt(locationUpdatedAt);
		return this;
	}

	public RebelApi traitorReports(Integer traitorReports) {
		setTraitorReports(traitorReports);
		return this;
	}

	public RebelApi inventory(Inventory inventory) {
		setInventory(inventory);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" rebelId='" + getRebelId() + "'" +
			", name='" + getName() + "'" +
			", birth='" + getBirth() + "'" +
			", age='" + getAge() + "'" +
			", location='" + getLocation() + "'" +
			", locationUpdatedAt='" + getLocationUpdatedAt() + "'" +
			", traitorReports='" + getTraitorReports() + "'" +
			", inventory='" + getInventory() + "'" +
			"}";
	}


}
