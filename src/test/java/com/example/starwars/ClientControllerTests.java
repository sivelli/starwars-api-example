package com.example.starwars;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.starwars.model.entity.api.ClientApi;
import com.example.starwars.model.entity.db.ClientDB;
import com.example.starwars.model.mapper.ClientMapper;
import com.example.starwars.model.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTests {

	@Autowired private MockMvc mockMvc;
	@Autowired private Flyway flyway;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private ClientMapper clientMapper;
	@Autowired private ClientRepository clientRepository;

	@BeforeEach
	public void initMocks() {
		flyway.clean();
		flyway.migrate();
	} 

	@Test
	public void testCreate() throws Exception {
	}

	/*
	@Test
	public void testCreate() throws Exception {
		ClientApi newClient = new ClientApi()
			.birth(LocalDate.of(2000,1,1))
			.clientId(null)
			.cpf("12345678901")
			.email("test@email.com")
			.name("Tester");
		ClientApi clientExpected = clientMapper.clientToApi(clientMapper.clientToDb(newClient).clientId(1));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.post("/client")
					.content(objectMapper.writeValueAsString(newClient).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(clientExpected)));
		// Allows creating a client with the same attributes 
		clientExpected.clientId(2);
		newClient.clientId(5);
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.post("/client")
					.content(objectMapper.writeValueAsString(newClient).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(clientExpected)));
		// Name cannot be absent
		newClient.name(null);
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.post("/client")
					.content(objectMapper.writeValueAsString(newClient).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isBadRequest());
	}
	@Test
	public void testFind() throws Exception {
		ClientDB client1 = new ClientDB()
			.birth(LocalDate.of(2000,1,1))
			.cpf("12345678901")
			.email("test1@email.com")
			.name("Tester 1")
			.createdAt(LocalDateTime.now());
		client1 = clientRepository.save(client1);
		ClientDB client2 = new ClientDB()
			.birth(LocalDate.of(2000,1,2))
			.cpf("12345678902")
			.email("test2@email.com")
			.name("Tester 2")
			.createdAt(LocalDateTime.now());
		client2 = clientRepository.save(client2);
		ClientDB client3 = new ClientDB()
			.birth(LocalDate.of(2000,1,3))
			.cpf("12345678903")
			.email("test3@email.com")
			.name("Tester 3")
			.createdAt(LocalDateTime.now());
		client3 = clientRepository.save(client3);
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/client")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(new ClientApi[]{clientMapper.clientToApi(client1), clientMapper.clientToApi(client2), clientMapper.clientToApi(client3)})));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/client")
					.param("lastName", "2")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(new ClientApi[]{clientMapper.clientToApi(client2)})));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/client")
					.param("firstName", "Tester")
					.param("cpf", "12345678901")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(new ClientApi[]{clientMapper.clientToApi(client1)})));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/client")
					.param("email", "test3@email.com")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(new ClientApi[]{clientMapper.clientToApi(client3)})));
			mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/client")
					.param("lastName", "2")
					.param("email", "test3@email.com")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(new ClientApi[0])));
	}
	*/
}
