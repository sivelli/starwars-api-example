package com.example.starwars.model.mapper;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.InventoryItem;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.entity.api.RebelApi;
import com.example.starwars.model.entity.db.RebelDB;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class RebelMapper {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Clock clock;

	public RebelApi rebelToApi(Rebel rebel) {
		RebelApi rebelApi = modelMapper.map(rebel, RebelApi.class);
		rebelApi.age(rebel.getAge(LocalDate.now(clock)));
		return rebelApi;
	}

	public RebelDB rebelToDB(Rebel rebel) {
		RebelDB rebelDb = modelMapper.map(rebel, RebelDB.class);
		rebelDb.setInventory(inventoryToDB(rebel.getInventory()));
		if (rebel.getLocation() != null) {
			rebelDb.setGalaxyLatitude(rebel.getLocation().getGalaxyLatitude());
			rebelDb.setGalaxyLongitude(rebel.getLocation().getGalaxyLongitude());
			rebelDb.setGalaxyName(rebel.getLocation().getGalaxyName());
		}
		return rebelDb;
	}

	public Rebel rebelFromApi(RebelApi rebelApi) {
		Rebel rebel = modelMapper.map(rebelApi, Rebel.class);
		return rebel;
	}

	public Rebel rebelFromDB(RebelDB rebelDb) {
		Rebel rebel = modelMapper.map(rebelDb, Rebel.class);
		rebel.setInventory(inventoryFromDB(rebelDb.getInventory()));
		rebel.setLocation(
			new Location()
				.galaxyLatitude(rebelDb.getGalaxyLatitude())
				.galaxyLongitude(rebelDb.getGalaxyLongitude())
				.galaxyName(rebelDb.getGalaxyName())
		);
		return rebel;
	}

	public Inventory inventoryFromDB(JsonNode inventoryDB) {
		Inventory inventory = new Inventory();
		if (inventoryDB != null && inventoryDB.isObject()) {
			inventoryDB.fields().forEachRemaining(field -> {
				Optional<InventoryItem> item = InventoryItem.fromName(field.getKey());
				if (item.isPresent() && field.getValue().isIntegralNumber()) {
					inventory.addItemIfValid(item.get(), field.getValue().intValue());
				}
			});
		}
		return inventory;
	}

	public JsonNode inventoryToDB(Inventory inventory) {
		ObjectNode inventoryDB = objectMapper.createObjectNode();
		inventory.stream().forEach(itemQuantity -> inventoryDB.put(itemQuantity.getFirst().name(), itemQuantity.getSecond()));
		return inventoryDB;
	}

}
