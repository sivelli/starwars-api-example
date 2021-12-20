package com.example.starwars;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.starwars.exception.RebelException;
import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.InventoryItem;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.repository.RebelPersistence;
import com.example.starwars.model.repository.RebelRepository.RebelReport;
import com.example.starwars.service.RebelService;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ActiveProfiles("test-containers")
public class RebelPersistenceTests {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final static LocalDate MOCKED_NOW = LocalDate.of (2020, 8, 1);

	@Autowired
	private RebelPersistence rebelPersistence;

	@Autowired private Flyway flyway;

	@MockBean
	private Clock clock;

	@BeforeEach
	public void initMocks() {
		flyway.clean();
		flyway.migrate();
		given(clock.instant()).willReturn(MOCKED_NOW.atStartOfDay(ZoneId.systemDefault()).toInstant());
		given(clock.getZone()).willReturn(ZoneId.systemDefault());
	}

	@Test
	public void persistence() throws RebelException {
		logger.info("persistence Test now=" + LocalDate.now(clock));
		Inventory inventory = new Inventory()
			.item(InventoryItem.WEAPON, 3)
			.item(InventoryItem.AMMO, 50)
			.item(InventoryItem.WATER, 3)
			.item(InventoryItem.FOOD, 2);
		Rebel rebel = new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.createdAt(LocalDateTime.now())
			.location(
				new Location()
					.galaxyLatitude(3.8D)
					.galaxyLongitude(-1.4D)
					.galaxyName("galaxy1")
			)
			.locationUpdatedAt(LocalDateTime.now())
			.inventory(inventory)
			.name("Test Rebel")
			.traitorReports(2);
		Rebel rebelPersisted = rebelPersistence.persistRebel(rebel);
		Rebel rebelFetched = rebelPersistence.getRebelFromId(rebelPersisted.getRebelId());
		rebel.setRebelId(rebelPersisted.getRebelId());
		Assertions.assertEquals(rebel, rebelFetched);
	}

	@Test
	public void reports() throws RebelException {
		logger.info("reports Test now=" + LocalDate.now(clock));
		List<Rebel> rebels = new ArrayList<>();
		rebels.add(new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.createdAt(LocalDateTime.now())
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 3)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.WATER, 3)
			)
			.name("Rebel1")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.createdAt(LocalDateTime.now())
			.inventory(new Inventory()
				.item(InventoryItem.AMMO, 11)
				.item(InventoryItem.WATER, 20)
				.item(InventoryItem.FOOD, 4)
			)
			.name("Rebel2")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS-1)
		);
		rebels.add(new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.createdAt(LocalDateTime.now())
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 2)
				.item(InventoryItem.AMMO, 100)
				.item(InventoryItem.WATER, 10)
				.item(InventoryItem.FOOD, 10)
			)
			.name("Rebel3")
			.traitorReports(0)
		);
		rebels.add(new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.createdAt(LocalDateTime.now())
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 5)
				.item(InventoryItem.AMMO, 200)
				.item(InventoryItem.FOOD, 40)
			)
			.name("Traitor1")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.add(new Rebel()
			.birth(LocalDate.of(2001, 5, 6))
			.createdAt(LocalDateTime.now())
			.inventory(new Inventory()
				.item(InventoryItem.WEAPON, 1)
				.item(InventoryItem.AMMO, 50)
				.item(InventoryItem.FOOD, 10)
			)
			.name("Traitor2")
			.traitorReports(RebelService.MIN_TRAITOR_INDICATIONS)
		);
		rebels.stream().forEach(rebel -> rebelPersistence.persistRebel(rebel));
		Inventory inventoryAll = rebelPersistence.getInventoryReportAll();
		Inventory inventoryTraitor = rebelPersistence.getInventoryReportTraitor();
		RebelReport rebelReport =  rebelPersistence.getRebelReport();
		Inventory inventoryAllExpected = rebels.stream().map(Rebel::getInventory).reduce(Inventory::merge).orElseGet(Inventory::new);
		Inventory inventoryTraitorExpected = rebels.stream().filter(RebelService::isTraitor).map(Rebel::getInventory).reduce(Inventory::merge).orElseGet(Inventory::new);
		long total = rebels.size();
		long totalTraitors = rebels.stream().filter(RebelService::isTraitor).count();
		Assertions.assertEquals(inventoryAllExpected, inventoryAll);
		Assertions.assertEquals(inventoryTraitorExpected, inventoryTraitor);
		Assertions.assertEquals(total, rebelReport.getTotal());
		Assertions.assertEquals(totalTraitors, rebelReport.getTraitor());
	}
}
