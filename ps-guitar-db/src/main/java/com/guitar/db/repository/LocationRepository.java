package com.guitar.db.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.Location;

@Repository
public class LocationRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private LocationJpeRepository locationJpeRepository;
	

	/**
	 * Create
	 */
	public Location create(Location loc) {
		//entityManager.persist(loc);
		//entityManager.flush();
		//return loc;
		return this.locationJpeRepository.saveAndFlush(loc);
		
		
	}

	/**
	 * Update
	 */
	public Location update(Location loc) {
	 //	loc = entityManager.merge(loc);
	//	entityManager.flush();
	//	return loc;
		
		return locationJpeRepository.saveAndFlush(loc);
	}

	/**
	 * Delete
	 */
	public void delete(Location loc) {
		//entityManager.remove(loc);
		//entityManager.flush();
		this.locationJpeRepository.delete(loc);
	}

	/**
	 * Find
	 */
	public Location find(Long id) {
	//	return entityManager.find(Location.class, id);
		
		return this.locationJpeRepository.findOne(id);
	}

	/**
	 * Custom finder
	 */
	public List<Location> getLocationByStateName(String name) {
	/*	@SuppressWarnings("unchecked")
		List<Location> locs = entityManager
				.createQuery("select l from Location l where l.state like :state")
				.setParameter("state", name + "%").getResultList();
		return locs; */
		
		return locationJpeRepository.findByStateLike(name);
	}
	
	
}
