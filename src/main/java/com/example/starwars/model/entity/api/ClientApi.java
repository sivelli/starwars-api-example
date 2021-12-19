package com.example.starwars.model.entity.api;

import java.time.LocalDate;

import com.example.starwars.config.AppConfig;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ClientApi {
	private Integer clientId;

	private String cpf;

	private String email;

	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConfig.API_DATE_FORMAT)
	private LocalDate birth;

	private Integer age;


	public ClientApi() {
	}

	public ClientApi(Integer clientId, String cpf, String email, String name, LocalDate birth, Integer age) {
		this.clientId = clientId;
		this.cpf = cpf;
		this.email = email;
		this.name = name;
		this.birth = birth;
		this.age = age;
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

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public ClientApi clientId(Integer clientId) {
		setClientId(clientId);
		return this;
	}

	public ClientApi cpf(String cpf) {
		setCpf(cpf);
		return this;
	}

	public ClientApi email(String email) {
		setEmail(email);
		return this;
	}

	public ClientApi name(String name) {
		setName(name);
		return this;
	}

	public ClientApi birth(LocalDate birth) {
		setBirth(birth);
		return this;
	}

	public ClientApi age(Integer age) {
		setAge(age);
		return this;
	}

	@Override
	public String toString() {
		return "{" +
			" clientId='" + getClientId() + "'" +
			", cpf='" + getCpf() + "'" +
			", email='" + getEmail() + "'" +
			", name='" + getName() + "'" +
			", birth='" + getBirth() + "'" +
			", age='" + getAge() + "'" +
			"}";
	}


}
