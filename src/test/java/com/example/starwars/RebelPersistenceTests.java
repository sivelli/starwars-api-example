package com.example.starwars;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.starwars.exception.RebelException;
import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.InventoryItem;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.entity.api.RebelApi;
import com.example.starwars.model.entity.db.RebelDB;
import com.example.starwars.model.mapper.RebelMapper;
import com.example.starwars.model.repository.RebelPersistence;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RebelPersistenceTests {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final static LocalDate MOCKED_NOW = LocalDate.of (2020, 8, 1);

	@Autowired
	private RebelPersistence rebelPersistence;

	@MockBean
	private Clock clock;

	@BeforeEach
	public void initMocks() {
		given(clock.instant()).willReturn(MOCKED_NOW.atStartOfDay(ZoneId.systemDefault()).toInstant());
		given(clock.getZone()).willReturn(ZoneId.systemDefault());
	}

	@Test
	public void persistence() throws RebelException {
		logger.info("convertRebelDB Test now=" + LocalDate.now(clock));
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
		Inventory inventoryReport = rebelPersistence.getInventoryReport();
		System.out.println("inventoryReport=" + inventoryReport);
	}

}
