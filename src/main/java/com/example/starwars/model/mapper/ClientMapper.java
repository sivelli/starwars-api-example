package com.example.starwars.model.mapper;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.starwars.model.entity.api.ClientApi;
import com.example.starwars.model.entity.db.ClientDB;

@Component
public class ClientMapper {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Clock clock;

	public ClientApi clientToApi(ClientDB clientDB) {
		ClientApi clientApi = modelMapper.map(clientDB, ClientApi.class);
		if (clientDB.getBirth() != null) {
			clientApi.age((int)ChronoUnit.YEARS.between(clientDB.getBirth(), LocalDate.now(clock)));
		}
		return clientApi;
	}

	public ClientDB clientToDb(ClientApi clientApi) {
		ClientDB clientDB = modelMapper.map(clientApi, ClientDB.class);
		return clientDB;
	}
}
