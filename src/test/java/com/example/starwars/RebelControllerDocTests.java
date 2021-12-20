package com.example.starwars;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.InventoryItem;
import com.example.starwars.model.entity.InventoryReport;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.entity.api.ItemsTrade;
import com.example.starwars.model.entity.api.RebelApi;
import com.example.starwars.model.mapper.RebelMapper;
import com.example.starwars.model.repository.RebelPersistence;
import com.example.starwars.service.RebelService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test-containers")
public class RebelControllerDocTests {

	private final static LocalDate MOCKED_NOW = LocalDate.now();

	@Autowired private MockMvc mockMvc;
	@Autowired private Flyway flyway;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private RebelMapper rebelMapper;
	@Autowired private RebelPersistence rebelPersistence;

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
		RebelApi newRebelApi = new RebelApi()
			.birth(LocalDate.of(2001, 5, 6))
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.location(
				new Location()
					.galaxyLatitude(3.8D)
					.galaxyLongitude(-1.4D)
					.galaxyName("galaxy1")
			)
			.traitorReports(0)
			.name("Rebel1");
		RebelApi newRebelApiExpected = rebelMapper.rebelToApi(rebelMapper.rebelFromApi(newRebelApi));
		newRebelApiExpected.setRebelId(1);
		System.out.println("newRebelApi=" + newRebelApi);
		System.out.println("newRebelApiExpected=" + newRebelApiExpected);
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.post("/rebel")
					.content(objectMapper.writeValueAsString(newRebelApi).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isCreated())
			.andExpect(content().json(objectMapper.writeValueAsString(newRebelApiExpected)));
		Assertions.assertEquals(rebelMapper.rebelFromApi(newRebelApiExpected), rebelPersistence.getRebelFromId(1).createdAt(null));
	}

	@Test
	public void retrieve() throws Exception {
		Rebel newRebel = new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.location(
				new Location()
					.galaxyLatitude(3.8D)
					.galaxyLongitude(-1.4D)
					.galaxyName("galaxy1")
			)
			.traitorReports(0)
			.name("Rebel1");
		Rebel rebelPersisted = rebelPersistence.persistRebel(newRebel);
		RebelApi rebelExpected = rebelMapper.rebelToApi(rebelPersisted);
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/rebel/" + rebelPersisted.getRebelId())
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(rebelExpected)));
	}

	@Test
	public void updateLocation() throws Exception {
		Rebel newRebel = new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.location(
				new Location()
					.galaxyLatitude(3.8D)
					.galaxyLongitude(-1.4D)
					.galaxyName("galaxy1")
			)
			.traitorReports(0)
			.name("Rebel1");
		Rebel rebelPersisted = rebelPersistence.persistRebel(newRebel);
		Location newLocation = new Location()
			.galaxyLatitude(-5.8D)
			.galaxyLongitude(-1.1D)
			.galaxyName("galaxy2");

		mockMvc
			.perform(
				MockMvcRequestBuilders
					.put("/rebel/" + rebelPersisted.getRebelId() + "/location")
					.content(objectMapper.writeValueAsString(newLocation).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk());
		Assertions.assertEquals(newLocation, rebelPersistence.getRebelFromId(rebelPersisted.getRebelId()).getLocation());
	}

	@Test
	public void reportTraitor() throws Exception {
		Rebel newRebel = new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.traitorReports(0)
			.name("Rebel1");
		Rebel rebel1Persisted = rebelPersistence.persistRebel(newRebel);
		Rebel newRebel2 = new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.traitorReports(0)
			.name("Rebel2");
		Rebel rebel2Persisted = rebelPersistence.persistRebel(newRebel2);

		mockMvc
			.perform(
				MockMvcRequestBuilders
					.post("/rebel/" + rebel1Persisted.getRebelId() + "/traitor/" + rebel2Persisted.getRebelId())
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk());
		Assertions.assertEquals(0, rebelPersistence.getRebelFromId(rebel1Persisted.getRebelId()).getTraitorReports());
		Assertions.assertEquals(1, rebelPersistence.getRebelFromId(rebel2Persisted.getRebelId()).getTraitorReports());
	}

	@Test
	public void trade() throws Exception {
		Inventory inventory1 = new Inventory()
			.item(InventoryItem.WEAPON, 3)
			.item(InventoryItem.AMMO, 50)
			.item(InventoryItem.WATER, 3);
		Rebel newRebel = new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.traitorReports(0)
			.inventory(inventory1)
			.name("Rebel1");
		Rebel rebel1Persisted = rebelPersistence.persistRebel(newRebel);
		Inventory inventory2 = new Inventory()
			.item(InventoryItem.AMMO, 10)
			.item(InventoryItem.WATER, 10)
			.item(InventoryItem.FOOD, 10);
		Rebel newRebel2 = new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.inventory(inventory2)
			.traitorReports(0)
			.name("Rebel2");
		Rebel rebel2Persisted = rebelPersistence.persistRebel(newRebel2);
		ItemsTrade itemsTrade = new ItemsTrade()
			.itemsOffer(new Inventory()
				.item(InventoryItem.WEAPON, 2)
				.item(InventoryItem.AMMO, 1)
			)
			.itemsReceive(new Inventory()
				.item(InventoryItem.WATER, 4)
				.item(InventoryItem.FOOD, 3)
			);
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.post("/rebel/" + rebel1Persisted.getRebelId() + "/trade/" + rebel2Persisted.getRebelId())
					.content(objectMapper.writeValueAsString(itemsTrade).getBytes())
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk());
		itemsTrade.getItemsOffer().stream().forEach(itemQuantity -> {
			inventory1.addItemNoCheck(itemQuantity.getFirst(), -itemQuantity.getSecond());
			inventory2.addItemNoCheck(itemQuantity.getFirst(), itemQuantity.getSecond());
		});
		itemsTrade.getItemsReceive().stream().forEach(itemQuantity -> {
			inventory1.addItemNoCheck(itemQuantity.getFirst(), itemQuantity.getSecond());
			inventory2.addItemNoCheck(itemQuantity.getFirst(), -itemQuantity.getSecond());
		});
		Assertions.assertEquals(inventory1, rebelPersistence.getRebelFromId(rebel1Persisted.getRebelId()).getInventory());
		Assertions.assertEquals(inventory2, rebelPersistence.getRebelFromId(rebel2Persisted.getRebelId()).getInventory());
	}

	@Test
	public void traitorPercent() throws Exception {
		List<Rebel> rebels = new ArrayList<>();
		rebels.add(new Rebel()
			.name("Rebel1")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel2")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel3")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Traitor1")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.add(new Rebel()
			.name("Traitor2")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.stream().forEach(rebel -> rebelPersistence.persistRebel(rebel));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/rebel/report/traitorsPercent")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(Float.valueOf(40F))));
	}

	@Test
	public void rebelPercent() throws Exception {
		List<Rebel> rebels = new ArrayList<>();
		rebels.add(new Rebel()
			.name("Rebel1")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel2")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel3")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Traitor1")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.add(new Rebel()
			.name("Traitor2")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.stream().forEach(rebel -> rebelPersistence.persistRebel(rebel));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/rebel/report/rebelsPercent")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(Float.valueOf(60F))));
	}

	@Test
	public void TraitorPoints() throws Exception {
		List<Rebel> rebels = new ArrayList<>();
		rebels.add(new Rebel()
			.name("Rebel1")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel2")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel3")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Traitor1")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 43)
				.item(InventoryItem.AMMO, 150)
				.item(InventoryItem.WATER, 3)
				.item(InventoryItem.FOOD, 44)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.add(new Rebel()
			.name("Traitor2")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 33)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.stream().forEach(rebel -> rebelPersistence.persistRebel(rebel));
		long pointsExpected = rebels.stream().filter(RebelService::isTraitor).mapToLong(r -> r.getInventory().totalPoints()).sum();
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/rebel/report/traitorPoints")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(Long.valueOf(pointsExpected))));
	}

	@Test
	public void inventoryAverage() throws Exception {
		List<Rebel> rebels = new ArrayList<>();
		rebels.add(new Rebel()
			.name("Rebel1")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel2")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel3")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Rebel4")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 43)
				.item(InventoryItem.AMMO, 150)
				.item(InventoryItem.WATER, 3)
				.item(InventoryItem.FOOD, 44)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.name("Traitor1")
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 33)
			)
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.stream().forEach(rebel -> rebelPersistence.persistRebel(rebel));
		Inventory inventoryRebels = rebels.stream().filter(r -> !RebelService.isTraitor(r)).map(Rebel::getInventory).reduce(Inventory::merge).orElseGet(Inventory::new);
		float rebelsNumber = (float)rebels.stream().filter(r -> !RebelService.isTraitor(r)).count();
		InventoryReport inventoryAverage = new InventoryReport();
		inventoryRebels.stream().forEach(itemQuantity -> inventoryAverage.getItems().put(itemQuantity.getFirst(), itemQuantity.getSecond() / rebelsNumber));
		mockMvc
			.perform(
				MockMvcRequestBuilders
					.get("/rebel/report/inventoryAverage")
					.accept(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(inventoryAverage)));
	}
}
