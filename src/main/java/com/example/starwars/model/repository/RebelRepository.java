package com.example.starwars.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.starwars.model.entity.InventoryItem;
import com.example.starwars.model.entity.db.RebelDB;

public interface RebelRepository extends JpaRepository<RebelDB, Integer> {
	@SuppressWarnings("unchecked")
	RebelDB save(RebelDB rebel);

	@Modifying
	@Transactional
	@Query("UPDATE Rebel r set r.galaxyLatitude = :latitude, r.galaxyLongitude = :longitude, r.galaxyName = :galaxy, r.locationUpdatedAt = current_timestamp() where r.rebelId = :rebelId")
	void updateLocation(Integer rebelId, Double latitude, Double longitude, String galaxy);

	@Modifying
	@Transactional
	@Query("UPDATE Rebel r set r.traitorReports = :reports where r.rebelId = :rebelId")
	void updateTraitorReports(Integer rebelId, Integer reports);

	@Query(value = "with dados as (select cast((json_each(inventory)).key as text), cast((json_each(inventory)).value as text), traitor_reports >= :minTraitorReports as traitor from rebel) SELECT key as item, traitor, sum(cast(value as int)) as quantity FROM dados GROUP BY key, traitor", nativeQuery = true)
	List<InventoryReportDB> getInventoryReport(int minTraitorReports);
	public interface InventoryReportDB {
		InventoryItem getItem();
		Long getQuantity();
		boolean isTraitor();
	}

	@Query(value = "SELECT count(*) as total, sum(case when traitor_reports >= :minTraitorReports then 1 else 0 end) as traitor from rebel", nativeQuery = true)
	RebelReport countRebels(int minTraitorReports);
	public interface RebelReport {
		long getTotal();
		long getTraitor();
	}


}
