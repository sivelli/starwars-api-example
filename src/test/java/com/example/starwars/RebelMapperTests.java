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

import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.InventoryItem;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.entity.api.RebelApi;
import com.example.starwars.model.entity.db.RebelDB;
import com.example.starwars.model.mapper.RebelMapper;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RebelMapperTests {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final static LocalDate MOCKED_NOW = LocalDate.of (2020, 8, 1);

	@Autowired
	private RebelMapper rebelMapper;

	@MockBean
	private Clock clock;

	@BeforeEach
	public void initMocks() {
		given(clock.instant()).willReturn(MOCKED_NOW.atStartOfDay(ZoneId.systemDefault()).toInstant());
		given(clock.getZone()).willReturn(ZoneId.systemDefault());
	}

	@Test
	public void convertRebelDB() {
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
			.rebelId(5)
			.traitorReports(2);
		RebelDB rebelDb = rebelMapper.rebelToDB(rebel);
		Rebel rebelConverted = rebelMapper.rebelFromDB(rebelDb);
		Assertions.assertEquals(rebel, rebelConverted);
	}

	@Test
	public void convertRebelApi() {
		logger.info("convertRebelApi Test now=" + LocalDate.now(clock));
		Inventory inventory = new Inventory()
			.item(InventoryItem.WEAPON, 1)
			.item(InventoryItem.AMMO, 20)
			.item(InventoryItem.WATER, 3)
			.item(InventoryItem.FOOD, 3);
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
			.rebelId(5)
			.traitorReports(3);
		RebelApi rebelApi = rebelMapper.rebelToApi(rebel);
		Assertions.assertEquals(rebel.getAge(LocalDate.now(clock)), rebelApi.getAge());
		Rebel rebelConverted = rebelMapper.rebelFromApi(rebelApi);
		rebel.setCreatedAt(null); // Absent in Api
		Assertions.assertEquals(rebel, rebelConverted);
	}

}
