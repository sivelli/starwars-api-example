package com.example.starwars.model.entity.db;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class BetrayalIndicationPK implements Serializable {
	Integer rebelReporter;
	Integer rebelReported;

	public BetrayalIndicationPK() {
	}

	public BetrayalIndicationPK(Integer rebelReporter, Integer rebelReported) {
		this.rebelReporter = rebelReporter;
		this.rebelReported = rebelReported;
	}

	public Integer getRebelReporter() {
		return this.rebelReporter;
	}

	public void setRebelReporter(Integer rebelReporter) {
		this.rebelReporter = rebelReporter;
	}

	public Integer getRebelReported() {
		return this.rebelReported;
	}

	public void setRebelReported(Integer rebelReported) {
		this.rebelReported = rebelReported;
	}

	public BetrayalIndicationPK rebelReporter(Integer rebelReporter) {
		setRebelReporter(rebelReporter);
		return this;
	}

	public BetrayalIndicationPK rebelReported(Integer rebelReported) {
		setRebelReported(rebelReported);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof BetrayalIndicationPK)) {
			return false;
		}
		BetrayalIndicationPK betrayalIndicationPK = (BetrayalIndicationPK) o;
		return Objects.equals(rebelReporter, betrayalIndicationPK.rebelReporter) && Objects.equals(rebelReported, betrayalIndicationPK.rebelReported);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rebelReporter, rebelReported);
	}

	@Override
	public String toString() {
		return "{" +
			" rebelReporter='" + getRebelReporter() + "'" +
			", rebelReported='" + getRebelReported() + "'" +
			"}";
	}
	
}
