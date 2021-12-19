package com.example.starwars.model.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.starwars.model.entity.db.ClientDB;

public interface ClientRepository extends JpaRepository<ClientDB, Integer> {
	@SuppressWarnings("unchecked")
	ClientDB save(ClientDB client);

	@Query("SELECT c FROM ClientDB c WHERE (:email is NULL OR c.email = :email) AND (c.name like :firstNameMask) AND (c.name like :lastNameMask) AND (coalesce(:birth, null) IS NULL OR c.birth = :birth) AND (:cpf IS NULL OR c.cpf = :cpf)")
	Slice<ClientDB> findByAttributes(String firstNameMask, String lastNameMask, String email, String cpf, LocalDate birth, Pageable pageable);
}
