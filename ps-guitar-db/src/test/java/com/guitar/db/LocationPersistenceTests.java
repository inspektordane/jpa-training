package com.guitar.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.guitar.db.model.Location;
import com.guitar.db.repository.LocationJpeRepository;
import com.guitar.db.repository.LocationRepository;
//classpath*:**/<context>.xml

//@ContextConfiguration(locations={"classpath:**/<context>.xml"})
@ContextConfiguration(locations={"classpath:com/guitar/db/applicationTests-context.xml"})

@RunWith(SpringJUnit4ClassRunner.class)

public class LocationPersistenceTests {
	//@Autowired
	//private LocationRepository locationRepository;
    
	@Autowired
	private LocationJpeRepository  locationJpaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		Location location = new Location();
		location.setCountry("Canada");
		location.setState("British Columbia");
		location = locationJpaRepository.saveAndFlush(location);
		
		// clear the persistence context so we don't return the previously cached location object
		// this is a test only thing and normally doesn't need to be done in prod code
		entityManager.clear();

		Location otherLocation = locationJpaRepository.findOne(location.getId());
		assertEquals("Canada", otherLocation.getCountry());
		assertEquals("British Columbia", otherLocation.getState());
		
		//delete BC location now
		locationJpaRepository.delete(otherLocation);
	}

	@Test
	public void testFindWithLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateLike("New%");
		assertEquals(4, locs.size());
	}

	@Test
	@Transactional  //note this is needed because we will get a lazy load exception unless we are in a tx
	public void testFindWithChildren() throws Exception {
		Location arizona = locationJpaRepository.findOne(3L);
		assertEquals("United States", arizona.getCountry());
		assertEquals("Arizona", arizona.getState());
		
		assertEquals(1, arizona.getManufacturers().size());
		
		assertEquals("Fender Musical Instruments Corporation", arizona.getManufacturers().get(0).getName());
	}
	
	@Test
	public void testJpafind()
	{
		List<Location> locations = locationJpaRepository.findAll();
		assertNotNull(locations);
	}
	
	@Test
	public void testJpafindAND()
	{
		List<Location> locations = locationJpaRepository.findByStateAndCountry("Utah", "United States");
		assertNotNull(locations);
		assertEquals("Utah", locations.get(0).getState());
	}
	
	@Test
	public void testJpafindOR()
	{
		List<Location> locations = locationJpaRepository.findByStateOrCountry("Utah", "Utah");
		assertNotNull(locations);
		assertEquals("Utah", locations.get(0).getState());
	}
	
	
	@Test
	public void testJpaStartWith()
	{
//		List<Location> locs = locationJpaRepository.findByStateStartingWith("New");
		//assertEquals(4, locs.size());
		
		
	}
	
	@Test
	public void testNotLike()
	{
		List<Location> locs = locationJpaRepository.findByStateNotLikeOrderByStateAsc("New%");
		//when LIKE is used must use "%" sign
		assertEquals(46, locs.size());
		
		for(Location i: locs)
		{
			System.out.println(i.getState());
		}
		
		
	}
	
	@Test
	public void testStartWith()
	{
		List<Location> locs = locationJpaRepository.findByStateIgnoreCaseStartingWith("new");
		//when LIKE is used must use "%" sign
		assertEquals(4, locs.size());
		
		
	}
}
