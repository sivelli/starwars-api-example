package com.example.starwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.starwars.exception.RebelException;
import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.Location;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.entity.api.RebelApi;
import com.example.starwars.model.mapper.RebelMapper;
import com.example.starwars.model.repository.RebelPersistence;

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
			throw new RebelException();
		}
		rebelPersistence.indicateTraitor(rebelId, traitorId);
	}

	private boolean isTraitor(Rebel rebel) {
		return rebel.getTraitorReports() >= MIN_TRAITOR_INDICATIONS;
	}

	@Transactional
	public void tradeItems(Integer rebelId1, Inventory itemsTradeRebel1, Integer rebelId2, Inventory itemsTradeRebel2) throws RebelException {
		// validate parameters
		if (rebelId1 == null || rebelId2 == null) {
			throw new RebelException();
		}
		if (itemsTradeRebel1 == null || itemsTradeRebel1.size() == 0) {
			throw new RebelException();
		}
		if (itemsTradeRebel2 == null || itemsTradeRebel2.size() == 0) {
			throw new RebelException();
		}
		Rebel rebel1 = rebelPersistence.getRebelFromId(rebelId1);
		Rebel rebel2 = rebelPersistence.getRebelFromId(rebelId2);

		// Verify if any of them is a traitor
		if (isTraitor(rebel1)) {
			throw new RebelException();
		}
		else if (isTraitor(rebel2)) {
			throw new RebelException();
		}
		// Verify if rebels have the items they trade
		if (rebel1.streamItems().anyMatch(itemQuantity -> !itemsTradeRebel1.hasItem(itemQuantity.getFirst(), itemQuantity.getSecond()))) {
			throw new RebelException();
		}
		if (rebel2.streamItems().anyMatch(itemQuantity -> !itemsTradeRebel2.hasItem(itemQuantity.getFirst(), itemQuantity.getSecond()))) {
			throw new RebelException();
		}
		// Verify if items have the same points
		if (itemsTradeRebel1.totalPoints() != itemsTradeRebel2.totalPoints()) {
			throw new RebelException();
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

	public Rebel getRebelFromId(Integer id) {
		return null;
	}

	public RebelApi transformToRebelApi(Rebel rebel) {
		return rebelMapper.rebelToApi(rebel);
	}

}
