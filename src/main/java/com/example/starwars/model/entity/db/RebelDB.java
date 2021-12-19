package com.example.starwars.model.entity.db;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.JsonNode;

@Entity(name = "Rebel")
@Table(name = "rebel")
public class RebelDB {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "rebel_id")
	private Integer rebelId;

	private String name;

	private String gender;

	@Column(columnDefinition = "DATE")
	private LocalDate birth;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "galaxy_latitude")
	private Double galaxyLatitude;

	@Column(name = "galaxy_longitude")
	private Double galaxyLongitude;

	@Column(name = "galaxy_name")
	private String galaxyName;

	@Column(name = "location_updated_at")
	private LocalDateTime locationUpdatedAt;

	@Column(name = "traitor_reports")
	private Integer traitorReports;

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private JsonNode inventory;


	public RebelDB() {
	}

	public RebelDB(Integer rebelId, String name, String gender, LocalDate birth, LocalDateTime createdAt, Double galaxyLatitude, Double galaxyLongitude, String galaxyName, LocalDateTime locationUpdatedAt, Integer traitorReports, JsonNode inventory) {
		this.rebelId = rebelId;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.createdAt = createdAt;
		this.galaxyLatitude = galaxyLatitude;
		this.galaxyLongitude = galaxyLongitude;
		this.galaxyName = galaxyName;
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

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Double getGalaxyLatitude() {
		return this.galaxyLatitude;
	}

	public void setGalaxyLatitude(Double galaxyLatitude) {
		this.galaxyLatitude = galaxyLatitude;
	}

	public Double getGalaxyLongitude() {
		return this.galaxyLongitude;
	}

	public void setGalaxyLongitude(Double galaxyLongitude) {
		this.galaxyLongitude = galaxyLongitude;
	}

	public String getGalaxyName() {
		return this.galaxyName;
	}

	public void setGalaxyName(String galaxyName) {
		this.galaxyName = galaxyName;
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

	public JsonNode getInventory() {
		return this.inventory;
	}

	public void setInventory(JsonNode inventory) {
		this.inventory = inventory;
	}

	public RebelDB rebelId(Integer rebelId) {
		setRebelId(rebelId);
		return this;
	}

	public RebelDB name(String name) {
		setName(name);
		return this;
	}

	public RebelDB gender(String gender) {
		setGender(gender);
		return this;
	}

	public RebelDB birth(LocalDate birth) {
		setBirth(birth);
		return this;
	}

	public RebelDB createdAt(LocalDateTime createdAt) {
		setCreatedAt(createdAt);
		return this;
	}

	public RebelDB galaxyLatitude(Double galaxyLatitude) {
		setGalaxyLatitude(galaxyLatitude);
		return this;
	}

	public RebelDB galaxyLongitude(Double galaxyLongitude) {
		setGalaxyLongitude(galaxyLongitude);
		return this;
	}

	public RebelDB galaxyName(String galaxyName) {
		setGalaxyName(galaxyName);
		return this;
	}

	public RebelDB locationUpdatedAt(LocalDateTime locationUpdatedAt) {
		setLocationUpdatedAt(locationUpdatedAt);
		return this;
	}

	public RebelDB traitorReports(Integer traitorReports) {
		setTraitorReports(traitorReports);
		return this;
	}

	public RebelDB inventory(JsonNode inventory) {
		setInventory(inventory);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" rebelId='" + getRebelId() + "'" +
			", name='" + getName() + "'" +
			", gender='" + getGender() + "'" +
			", birth='" + getBirth() + "'" +
			", createdAt='" + getCreatedAt() + "'" +
			", galaxyLatitude='" + getGalaxyLatitude() + "'" +
			", galaxyLongitude='" + getGalaxyLongitude() + "'" +
			", galaxyName='" + getGalaxyName() + "'" +
			", locationUpdatedAt='" + getLocationUpdatedAt() + "'" +
			", traitorReports='" + getTraitorReports() + "'" +
			", inventory='" + getInventory() + "'" +
			"}";
	}

	
}
