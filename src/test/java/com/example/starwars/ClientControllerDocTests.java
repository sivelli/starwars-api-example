package com.example.starwars;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.starwars.model.entity.api.ClientApi;
import com.example.starwars.model.entity.db.ClientDB;
import com.example.starwars.model.mapper.ClientMapper;
import com.example.starwars.model.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerDocTests {

	private final static LocalDate MOCKED_NOW = LocalDate.now();

	@Autowired private MockMvc mockMvc;
	@Autowired private Flyway flyway;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private ClientMapper clientMapper;
	@Autowired private ClientRepository clientRepository;

	@MockBean
	private Clock clock;

	@BeforeEach
	public void initMocks() {
		flyway.clean();
		flyway.migrate();
		given(clock.instant()).willReturn(MOCKED_NOW.atStartOfDay(ZoneId.systemDefault()).toInstant());
		given(clock.getZone()).willReturn(ZoneId.systemDefault());
	} 

	@BeforeEach
	public void setUpDoc(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
			.alwaysDo(MockMvcRestDocumentation.document("{method-name}", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
			.build();
	}
	 
	@Test
	public void create() throws Exception {
	}

	/*
	@Test
	public void create() throws Exception {
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
		Assertions.assertTrue(clientRepository.findById(1).isPresent());
	}

	@Test
	public void retrieve() throws Exception {
		ClientDB newClientDB = new ClientDB()
			.birth(LocalDate.of(2000,1,1))
			.cpf("12345678901")
			.email("test@email.com")
			.name("Tester");
		newClientDB = clientRepository.save(newClientDB);
		ClientApi clientExpected = clientMapper.clientToApi(newClientDB);
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/client/" + newClientDB.getClientId())
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(clientExpected)));
	}

	@Test
	public void replace() throws Exception {
		ClientDB oldClientDB = new ClientDB()
			.birth(LocalDate.of(2000,1,1))
			.cpf("12345678901")
			.email("test@email.com")
			.name("Tester")
			.createdAt(LocalDateTime.now());
		oldClientDB = clientRepository.save(oldClientDB);

		ClientApi newClientExpected = new ClientApi()
			.clientId(oldClientDB.getClientId())
			.birth(LocalDate.of(2001, 2, 2))
			.name("Other Tester");
		newClientExpected.age((int)ChronoUnit.YEARS.between(newClientExpected.getBirth(), LocalDate.now(clock)));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.put("/client/" + oldClientDB.getClientId())
					.content(objectMapper.writeValueAsString(newClientExpected).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(newClientExpected)));
		ClientDB replaced = clientRepository.findById(oldClientDB.getClientId()).get();
		Assertions.assertNotEquals(replaced.getCreatedAt(), replaced.getUpdatedAt());
		Assertions.assertEquals(LocalDateTime.now(clock), replaced.getUpdatedAt());
		replaced.updatedAt(null).createdAt(null);
		Assertions.assertEquals(replaced, clientMapper.clientToDb(newClientExpected));
	}

	@Test
	public void update() throws Exception {
		ClientDB oldClientDB = new ClientDB()
			.birth(LocalDate.of(2000,1,1))
			.cpf("12345678901")
			.email("test@email.com")
			.name("Tester")
			.createdAt(LocalDateTime.now());
		oldClientDB = clientRepository.save(oldClientDB);

		ClientApi clientUpdates = new ClientApi()
			.name("Other Tester");
		ClientApi newClientExpected = clientMapper.clientToApi(oldClientDB)
			.name(clientUpdates.getName());
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.patch("/client/" + oldClientDB.getClientId())
					.content(objectMapper.writeValueAsString(clientUpdates).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(newClientExpected)));
		ClientDB replaced = clientRepository.findById(oldClientDB.getClientId()).get();
		Assertions.assertNotEquals(replaced.getCreatedAt(), replaced.getUpdatedAt());
		Assertions.assertEquals(LocalDateTime.now(clock), replaced.getUpdatedAt());
		replaced.updatedAt(null).createdAt(null);
		Assertions.assertEquals(replaced, clientMapper.clientToDb(newClientExpected));
	}

	@Test
	public void find() throws Exception {
		ClientDB client1 = new ClientDB()
			.birth(LocalDate.of(2000,1,1))
			.cpf("12345678901")
			.email("test@email.com")
			.name("Tester 1")
			.createdAt(LocalDateTime.now());
		client1 = clientRepository.save(client1);
		ClientDB client2 = new ClientDB()
			.birth(LocalDate.of(2000,1,1))
			.cpf("12345678901")
			.email("test@email.com")
			.name("Tester 2")
			.createdAt(LocalDateTime.now());
		client2 = clientRepository.save(client2);
		ClientApi[] clientsExpected = new ClientApi[]{clientMapper.clientToApi(client1), clientMapper.clientToApi(client2)}; 
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/client")
						.param("firstName", "Tester")
						.param("lastName", "")
						.param("cpf", "12345678901")
						.param("birth",LocalDate.of(2000,1,1).format(DateTimeFormatter.ISO_DATE))
						.param("email", "test@email.com")
						.param("page", "0")
						.param("size", "5")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(clientsExpected)));
	}
	*/
}
