package com.example.starwars.model.entity.db;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "betrayal_indication")
@IdClass(value = BetrayalIndicationPK.class)
public class BetrayalIndication {
	@Id
	@Column(name = "rebel_reporter")
	Integer rebelReporter;
	@Id
	@Column(name = "rebel_reported")
	Integer rebelReported;

	@Column(name = "reported_at")
	LocalDateTime reportedAt;


	public BetrayalIndication(BetrayalIndicationPK id, LocalDateTime reportedAt) {
		this(id.getRebelReporter(), id.getRebelReported(), reportedAt);
	}

	public BetrayalIndication() {
	}

	public BetrayalIndication(Integer rebelReporter, Integer rebelReported, LocalDateTime reportedAt) {
		this.rebelReporter = rebelReporter;
		this.rebelReported = rebelReported;
		this.reportedAt = reportedAt;
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

	public LocalDateTime getReportedAt() {
		return this.reportedAt;
	}

	public void setReportedAt(LocalDateTime reportedAt) {
		this.reportedAt = reportedAt;
	}

	public BetrayalIndication rebelReporter(Integer rebelReporter) {
		setRebelReporter(rebelReporter);
		return this;
	}

	public BetrayalIndication rebelReported(Integer rebelReported) {
		setRebelReported(rebelReported);
		return this;
	}

	public BetrayalIndication reportedAt(LocalDateTime reportedAt) {
		setReportedAt(reportedAt);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" rebelReporter='" + getRebelReporter() + "'" +
			", rebelReported='" + getRebelReported() + "'" +
			", reportedAt='" + getReportedAt() + "'" +
			"}";
	}

}
