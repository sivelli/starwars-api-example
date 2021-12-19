package com.example.starwars.model.repository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.starwars.exception.RebelException;
import com.example.starwars.model.entity.Inventory;
import com.example.starwars.model.entity.Rebel;
import com.example.starwars.model.entity.db.BetrayalIndication;
import com.example.starwars.model.entity.db.BetrayalIndicationPK;
import com.example.starwars.model.entity.db.InventoryReportDB;
import com.example.starwars.model.entity.db.RebelDB;
import com.example.starwars.model.mapper.RebelMapper;

@Component
public class RebelPersistence {
	@Autowired
	private RebelRepository rebelRepository;

	@Autowired
	private BetrayalIndicationRepository betrayalIndicationRepository;

	@Autowired
	private RebelMapper rebelMapper;

	@Autowired
	private Clock clock;

	@Transactional
	public Rebel persistRebel(Rebel rebel) {
		rebel.setRebelId(null);
		rebel.setCreatedAt(LocalDateTime.now(clock));
		rebel.setTraitorReports(0);
		RebelDB rebelDb = rebelMapper.rebelToDB(rebel);
		rebelDb = rebelRepository.save(rebelDb);
		return rebelMapper.rebelFromDB(rebelDb);
	}

	@Transactional
	public Rebel updateRebel(Rebel rebel) throws RebelException {
		if (rebel.getRebelId() == null) {
			throw new RebelException();
		}
		RebelDB rebelDb = rebelMapper.rebelToDB(rebel);
		rebelDb = rebelRepository.save(rebelDb);
		return rebelMapper.rebelFromDB(rebelDb);
	}

	public void updateRebelLocation(Integer rebelId, Double latitude, Double longitude, String galaxy) {
		rebelRepository.updateLocation(rebelId, latitude, longitude, galaxy);
	}

	@Transactional
	public void indicateTraitor(Integer rebelId, Integer traitorId) {
		BetrayalIndicationPK id = new BetrayalIndicationPK().rebelReporter(rebelId).rebelReported(traitorId);
		BetrayalIndication betrayalIndication = betrayalIndicationRepository.findById(id).orElse(null);
		if (betrayalIndication == null) {
			betrayalIndication = new BetrayalIndication().rebelReporter(rebelId).rebelReported(traitorId);
		}
		betrayalIndication.setReportedAt(LocalDateTime.now(clock));
		betrayalIndicationRepository.save(betrayalIndication);
		Long totalIndications = betrayalIndicationRepository.betrayalIndications(traitorId);
		rebelRepository.updateTraitorReports(traitorId, totalIndications.intValue());
	}

	public Rebel getRebelFromId(Integer id) throws RebelException {
		if (id == null) {
			throw new RebelException();
		}
		try {
			RebelDB rebelDb = rebelRepository.findById(id).orElseThrow();
			System.out.println("rebelDb = " + rebelDb);
			return rebelMapper.rebelFromDB(rebelDb);

		}
		catch (NoSuchElementException e) {
			throw new RebelException();
		}
	}

	public Inventory getInventoryReport() {
		List<InventoryReportDB> listInventory = rebelRepository.getInventoryReport();
		System.out.println("listInventory=" + listInventory);
		Inventory inventory = new Inventory();
		listInventory.stream().forEach(item -> inventory.addItemNoCheck(item.getItem(), item.getQuantity()));
		return inventory;
	}


}
