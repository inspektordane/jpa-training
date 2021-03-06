package com.guitar.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.Manufacturer;

 //@Repository
public interface ManufacturerJpaRepository extends JpaRepository<Manufacturer, Long> {
	 List<Manufacturer> findByFoundedDateBefore(Date val);
	 List<Manufacturer> findByActiveTrue();
	 List<Manufacturer> findByActiveFalse();
	 
	 

}


 
 /*
 
 
 public List<Manufacturer> getManufacturersFoundedBeforeDate(Date date) {
	@SuppressWarnings("unchecked")
	List<Manufacturer> mans = entityManager
			.createQuery("select m from Manufacturer m where m.foundedDate < :date")
			.setParameter("date", date).getResultList();
	return mans;
 
 */