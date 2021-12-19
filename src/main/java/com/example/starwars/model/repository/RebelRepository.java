package com.example.starwars.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.starwars.model.entity.db.InventoryReportDB;
import com.example.starwars.model.entity.db.RebelDB;

public interface RebelRepository extends JpaRepository<RebelDB, Integer> {
	@SuppressWarnings("unchecked")
	RebelDB save(RebelDB rebel);

	@Query("UPDATE Rebel r set r.galaxyLatitude = :latitude, r.galaxyLongitude = :longitude, r.galaxyName = :galaxy, r.locationUpdatedAt = current_timestamp() where r.rebelId = :rebelId")
	void updateLocation(Integer rebelId, Double latitude, Double longitude, String galaxy);

	@Query("UPDATE Rebel r set r.traitorReports = :reports where r.rebelId = :rebelId")
	void updateTraitorReports(Integer rebelId, Integer reports);

	@Query(value = "with dados as (select (json_each(inventory)).key::text, (json_each(inventory)).value::text::int from rebel) SELECT new com.example.starwars.model.entity.db.InventoryReportDB(key, sum(value)) FROM dados GROUP BY key", nativeQuery = true)
	List<InventoryReportDB> getInventoryReport();

}
