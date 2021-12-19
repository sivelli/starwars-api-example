package com.example.starwars.model.entity.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class ClientDB {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "client_id")
	private Integer clientId;

	private String cpf;

	private String email;

	private String name;

	@Column(columnDefinition = "DATE")
	private LocalDate birth;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;


	public ClientDB() {
	}

	public ClientDB(Integer clientId, String cpf, String email, String name, LocalDate birth, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.clientId = clientId;
		this.cpf = cpf;
		this.email = email;
		this.name = name;
		this.birth = birth;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ClientDB clientId(Integer clientId) {
		setClientId(clientId);
		return this;
	}

	public ClientDB cpf(String cpf) {
		setCpf(cpf);
		return this;
	}

	public ClientDB email(String email) {
		setEmail(email);
		return this;
	}

	public ClientDB name(String name) {
		setName(name);
		return this;
	}

	public ClientDB birth(LocalDate birth) {
		setBirth(birth);
		return this;
	}

	public ClientDB createdAt(LocalDateTime createdAt) {
		setCreatedAt(createdAt);
		return this;
	}

	public ClientDB updatedAt(LocalDateTime updatedAt) {
		setUpdatedAt(updatedAt);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ClientDB)) {
			return false;
		}
		ClientDB clientDB = (ClientDB) o;
		return Objects.equals(clientId, clientDB.clientId) && Objects.equals(cpf, clientDB.cpf) && Objects.equals(email, clientDB.email) && Objects.equals(name, clientDB.name) && Objects.equals(birth, clientDB.birth) && Objects.equals(createdAt, clientDB.createdAt) && Objects.equals(updatedAt, clientDB.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientId, cpf, email, name, birth, createdAt, updatedAt);
	}

	@Override
	public String toString() {
		return "{" +
			" clientId='" + getClientId() + "'" +
			", cpf='" + getCpf() + "'" +
			", email='" + getEmail() + "'" +
			", name='" + getName() + "'" +
			", birth='" + getBirth() + "'" +
			", createdAt='" + getCreatedAt() + "'" +
			", updatedAt='" + getUpdatedAt() + "'" +
			"}";
	}

}
