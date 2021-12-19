package com.example.starwars.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.starwars.model.entity.db.BetrayalIndication;
import com.example.starwars.model.entity.db.BetrayalIndicationPK;

public interface BetrayalIndicationRepository extends JpaRepository<BetrayalIndication, BetrayalIndicationPK> {
	@SuppressWarnings("unchecked")
	BetrayalIndication save(BetrayalIndication betrayalIndication);
	
	@Query("SELECT count(*) from BetrayalIndication bi where bi.rebelReported = :rebelReported")
	Long betrayalIndications(Integer rebelReported);
}
