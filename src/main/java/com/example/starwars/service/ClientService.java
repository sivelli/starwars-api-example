package com.example.starwars.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.starwars.model.entity.api.ClientApi;
import com.example.starwars.model.entity.db.ClientDB;
import com.example.starwars.model.mapper.ClientMapper;
import com.example.starwars.model.repository.ClientRepository;

@Service
public class ClientService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientMapper clientMapper;
	
	@Autowired
	private Clock clock;

	@Transactional
	public ClientDB createClient(ClientApi clientApi) {
		clientApi.clientId(null);
		logger.info("createClient " + clientApi);
		validateCompleteClient(clientApi);
		ClientDB clientDB = clientMapper.clientToDb(clientApi);
		clientDB.setCreatedAt(LocalDateTime.now(clock));
		clientDB.setUpdatedAt(clientDB.getCreatedAt());
		return clientRepository.save(clientDB);
	}

	private void validateCompleteClient(ClientApi clientApi) {
		if (!StringUtils.hasText(clientApi.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name missing");
		}
	}

	public ClientApi clientDBToApi(ClientDB clientDB) {
		return clientMapper.clientToApi(clientDB);
	}

	public ClientDB getById(Integer id) {
		return clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not found"));
	}

	@Transactional
	public ClientDB replaceClient(Integer id, ClientApi clientApi) {
		validateCompleteClient(clientApi);
		ClientDB client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found"));
		client.setBirth(clientApi.getBirth());
		client.setCpf(clientApi.getCpf());
		client.setEmail(clientApi.getEmail());
		client.setName(clientApi.getName());
		client.setUpdatedAt(LocalDateTime.now(clock));
		return client;
	}

	@Transactional
	public ClientDB updateClient(Integer id, ClientApi clientApi) {
		ClientDB client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found"));
		boolean updated = false;
		if (clientApi.getBirth() != null) {
			client.setBirth(clientApi.getBirth());
			updated = true;
		}
		if (clientApi.getCpf() != null) {
			client.setCpf(clientApi.getCpf());
			updated = true;
		}
		if (clientApi.getEmail() != null) {
			client.setEmail(clientApi.getEmail());
			updated = true;
		}
		if (clientApi.getName() != null) {
			client.setName(clientApi.getName());
			updated = true;
		}
		if (updated) {
			client.setUpdatedAt(LocalDateTime.now(clock));
		}
		return client;
	}

	public List<ClientDB> find(String firstName, String lastName, String email, String cpf, LocalDate birth, Pageable pageable) {
		logger.info("find " + firstName + "," + lastName + "," + email + "," + cpf + "," + birth + "," + pageable);
		String firstNameMask = Optional.ofNullable(firstName).map(fn -> fn + "%").orElse("%");
		String lastNameMask = Optional.ofNullable(lastName).map(ln -> "%" + ln).orElse("%");
		return clientRepository.findByAttributes(firstNameMask, lastNameMask, email, cpf, birth, pageable).getContent();
	}
}
