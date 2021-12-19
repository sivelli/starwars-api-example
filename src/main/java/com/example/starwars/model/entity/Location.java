package com.example.starwars.model.entity;

import java.util.Objects;

public class Location {
	private Double galaxyLatitude;

	private Double galaxyLongitude;

	private String galaxyName;


	public Location() {
	}

	public Location(Double galaxyLatitude, Double galaxyLongitude, String galaxyName) {
		this.galaxyLatitude = galaxyLatitude;
		this.galaxyLongitude = galaxyLongitude;
		this.galaxyName = galaxyName;
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

	public Location galaxyLatitude(Double galaxyLatitude) {
		setGalaxyLatitude(galaxyLatitude);
		return this;
	}

	public Location galaxyLongitude(Double galaxyLongitude) {
		setGalaxyLongitude(galaxyLongitude);
		return this;
	}

	public Location galaxyName(String galaxyName) {
		setGalaxyName(galaxyName);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Location)) {
			return false;
		}
		Location location = (Location) o;
		return Objects.equals(galaxyLatitude, location.galaxyLatitude) && Objects.equals(galaxyLongitude, location.galaxyLongitude) && Objects.equals(galaxyName, location.galaxyName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(galaxyLatitude, galaxyLongitude, galaxyName);
	}

	@Override
	public String toString() {
		return "{" +
			" galaxyLatitude='" + getGalaxyLatitude() + "'" +
			", galaxyLongitude='" + getGalaxyLongitude() + "'" +
			", galaxyName='" + getGalaxyName() + "'" +
			"}";
	}

	
}
