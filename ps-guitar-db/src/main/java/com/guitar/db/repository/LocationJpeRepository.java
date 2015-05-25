package com.guitar.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.Location;

@Repository
public interface LocationJpeRepository extends JpaRepository<Location, Long> {

	List <Location> findByStateLike(String stateName);
	
	List <Location> findByStateOrCountry(String value1, String value2);
	List <Location> findByStateAndCountry(String value1, String value2);
	List <Location> findByStateStartingWith(String stateName);
	
}

///State--mora biti atribut u klasi Location koji se mora tako zvati. 
//Like je a ne toèni upit.