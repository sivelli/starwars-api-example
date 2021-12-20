package com.example.starwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.starwars.exception.RebelException;
import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.InventoryReport;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.entity.api.RebelApi;
import com.example.starwars.model.mapper.RebelMapper;
import com.example.starwars.model.repository.RebelPersistence;
import com.example.starwars.model.repository.RebelRepository.RebelReport;

@Service
public class RebelService {
	@Autowired
	private RebelMapper rebelMapper;

	@Autowired
	private RebelPersistence rebelPersistence;

	public static final int MIN_TRAITOR_INDICATIONS = 3;

	@Transactional
	public Rebel createRebelFromApi(RebelApi rebelApi) {
		Rebel rebel = rebelMapper.rebelFromApi(rebelApi);
		return rebelPersistence.persistRebel(rebel);
	}

	public RebelApi getRebelApi(Rebel rebel) {
		return rebelMapper.rebelToApi(rebel);
	}

	public void updateLocation(Integer rebelId, Location newLocation) {
		rebelPersistence.updateRebelLocation(rebelId, newLocation.getGalaxyLatitude(), newLocation.getGalaxyLongitude(), newLocation.getGalaxyName());
	}

	public void indicateTraitor(Integer rebelId, Integer traitorId) throws RebelException {
		if (rebelId == traitorId) {
			throw new RebelException("Cannot indicate himself as a traitor");
		}
		rebelPersistence.indicateTraitor(rebelId, traitorId);
	}

	public static boolean isTraitor(Rebel rebel) {
		return rebel.getTraitorReports() >= MIN_TRAITOR_INDICATIONS;
	}

	@Transactional
	public void tradeItems(Integer rebelId1, Inventory itemsTradeRebel1, Integer rebelId2, Inventory itemsTradeRebel2) throws RebelException {
		// validate parameters
		if (rebelId1 == null || rebelId2 == null) {
			throw new RebelException("Rebel missing");
		}
		if (itemsTradeRebel1 == null || itemsTradeRebel1.size() == 0) {
			throw new RebelException("No itens to offer");
		}
		if (itemsTradeRebel2 == null || itemsTradeRebel2.size() == 0) {
			throw new RebelException("No itens to receive");
		}
		Rebel rebel1 = rebelPersistence.getRebelFromId(rebelId1);
		Rebel rebel2 = rebelPersistence.getRebelFromId(rebelId2);

		// Verify if any of them is a traitor
		if (isTraitor(rebel1)) {
			throw new RebelException("Rebel is a traitor");
		}
		else if (isTraitor(rebel2)) {
			throw new RebelException("Rebel is a traitor");
		}
		// Verify if rebels have the items they trade
		if (itemsTradeRebel1.stream().anyMatch(itemQuantity -> !rebel1.getInventory().hasItem(itemQuantity.getFirst(), itemQuantity.getSecond()))) {
			throw new RebelException("Not enough itens to offer");
		}
		if (itemsTradeRebel2.stream().anyMatch(itemQuantity -> !rebel2.getInventory().hasItem(itemQuantity.getFirst(), itemQuantity.getSecond()))) {
			throw new RebelException("Not enough itens to receive");
		}
		// Verify if items have the same points
		if (itemsTradeRebel1.totalPoints() != itemsTradeRebel2.totalPoints()) {
			throw new RebelException("Points mismatch");
		}
		// Trade items
		itemsTradeRebel1.stream().forEach(itemQuantity -> {
			rebel1.changeItemQuantity(itemQuantity.getFirst(), -itemQuantity.getSecond());
			rebel2.changeItemQuantity(itemQuantity.getFirst(), itemQuantity.getSecond());
		});
		itemsTradeRebel2.stream().forEach(itemQuantity -> {
			rebel2.changeItemQuantity(itemQuantity.getFirst(), -itemQuantity.getSecond());
			rebel1.changeItemQuantity(itemQuantity.getFirst(), itemQuantity.getSecond());
		});
		// Update Rebels
		rebelPersistence.updateRebel(rebel1);
		rebelPersistence.updateRebel(rebel2);
	}

	public Rebel getRebelFromId(Integer id) throws RebelException {
		return rebelPersistence.getRebelFromId(id);
	}

	public RebelApi transformToRebelApi(Rebel rebel) {
		return rebelMapper.rebelToApi(rebel);
	}

	public Float getTraitorPercent() {
		RebelReport report = rebelPersistence.getRebelReport();
		if (report.getTotal() == 0) {
			return null;
		}
		else {
			return report.getTraitor() * 100.0F / report.getTotal();
		}
	}

	public Float getRebelPercent() {
		RebelReport report = rebelPersistence.getRebelReport();
		if (report.getTotal() == 0) {
			return null;
		}
		else {
			return (report.getTotal() - report.getTraitor()) * 100.0F / report.getTotal();
		}
	}

	public InventoryReport getInventoryAverage() {
		RebelReport report = rebelPersistence.getRebelReport();
		Inventory inventoryRebel = rebelPersistence.getInventoryReportRebel();
		InventoryReport inventoryReport = new InventoryReport();
		if (report.getTotal() > report.getTraitor()) {
			Float rebels = (float)report.getTotal() - report.getTraitor();
			inventoryRebel.stream().forEach(itemQuantity -> inventoryReport.getItems().put(itemQuantity.getFirst(), itemQuantity.getSecond() / rebels));
		}
		return inventoryReport;
	}

	public long getTraitorPoints() {
		Inventory inventoryTraitor = rebelPersistence.getInventoryReportTraitor();
		return inventoryTraitor.totalPoints();
	}
}
