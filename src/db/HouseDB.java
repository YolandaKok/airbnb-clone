package db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.joda.time.LocalDate;

import entities.Calendar;
import entities.House;

public class HouseDB {
	
		// Get all the Houses as a list
		@SuppressWarnings("unchecked")
		public List<House> getHouses()
	    {
	        List<House> houses = null;
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        //Query q = em.createQuery("Select u from User u");
	        Query q = em.createNamedQuery("House.findAll");
	        houses =  q.getResultList();
			
	        tx.commit();
	        em.close();
	        return houses;
	    }
	   
		// Insert a House in this list
		
	    public long insertHouse(House house)
	    {
	    	// Create All dates between two dates /////////////////////
	    	
	    	String s = house.getCalendarUpdated();
	    	String e = "2018-09-16";
	    	LocalDate start = LocalDate.parse(s);
	    	LocalDate end = LocalDate.parse(e);
	    	List<LocalDate> totalDates = new ArrayList<LocalDate>();
	    	while (!start.isAfter(end)) {
	    	    	totalDates.add(start);
	    	    	start = start.plusDays(1);
	    	}
	    	
	    	//////////////////////////////////////////////////////////
	    	
	    	long id = -1;
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        try 
	        {
	            em.persist(house);
	            em.flush();
	            id = house.getHouseID();
		        tx.commit();
	            
	            return id;
	        }
	        catch (PersistenceException e1)
	        {
	            if (tx.isActive()) tx.rollback();
	            return id;
	        }
	        finally 
	        {
	            em.close();
		        EntityManager em1 = JPAResource.factory.createEntityManager();
		        EntityTransaction tx1 = em1.getTransaction();
	            for (int i = 0; i < totalDates.size(); i++) {
		        	tx1.begin();
		        	Calendar date = new Calendar();
		        	date.setAvailable(true);
		        	date.setDate(totalDates.get(i).toString());
		        	date.setHouseID(id);
		        	date.setPrice(house.getPrice());
			        em1.persist(date);
			        em1.flush();
			        tx1.commit();
		        }
	            em1.close();
	            
	        }
	        
	        
	        
	    }
	    
	    // Get House by Id
	    
	    public House getById(long id)
	    {
	        House house = null;
	        
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        house = em.find(House.class, id);
		
	        tx.commit();
	        em.close();
	        
	        return house;
	        
	    }
	    
	    @SuppressWarnings("unchecked")
		public List<House> getHostHouses(long hostid) {
	    	List<House> houses = null;
	    	EntityManager em = JPAResource.factory.createEntityManager();
	    	EntityTransaction tx = em.getTransaction();
	    	
	    	tx.begin();
	    	// select all houses with this hostid
	    	Query q = em.createQuery("Select h from House h WHERE h.hostID = :hostid");
	    	q.setParameter("hostid", hostid);
	    	
	    	houses = q.getResultList();
	    	
	    	em.close();
	    	
	    	return houses;
	    }
	    
	    
	    // UpdateHouse information
	    public void updateHouse(House house, long id)
	    {
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        String name = house.getName();
	        float bathrooms = house.getBathrooms();
	        int bedrooms = house.getBedrooms();
	        int beds = house.getBeds();
	        int accommodates = house.getAccommodates();
	        String summary = house.getSummary();
	        String city = house.getCity();
	        String country = house.getCountry();
	        String propertyType = house.getPropertyType();
	        String bedType = house.getBedType();
	        int price = house.getPrice();
	        String weeklyPrice = house.getWeeklyPrice();
	        String monthlyPrice = house.getMonthlyPrice();
	        int extraPeople = house.getExtraPeople();
	        int cleaningFee = house.getCleaningFee();
	        String roomType = house.getRoomType();
	        boolean wifi = house.getWifi();
	        boolean tv = house.getTv();
	        boolean parking = house.getParking();
	        boolean elevator = house.getElevator();
	        boolean kitchen = house.getKitchen();
	        boolean ac = house.getAc();
	        boolean heating = house.getHeating();
	        int squareFeet = house.getSquareFeet();
	        
        	Query q1 = em.createQuery("UPDATE House house SET house.name = :name, house.bathrooms = :bathrooms, house.bedrooms = :bedrooms, house.beds = :beds, house.accommodates = :accommodates, house.summary =:summary, house.city =:city, "
        			+ "house.country =:country, house.propertyType =:propertyType, house.bedType =:bedType, house.price =:price, house.weeklyPrice =:weeklyPrice"
        			+ ", house.monthlyPrice =:monthlyPrice, house.extraPeople =:extraPeople, house.cleaningFee =:cleaningFee, house.roomType =:roomType, "
        			+ "house.wifi =:wifi, house.tv =:tv, house.parking =:parking, house.kitchen =:kitchen, house.elevator =:elevator"
        			+ ", house.heating =:heating, house.ac =:ac, house.squareFeet =:squareFeet WHERE house.houseID = :id");
        	q1.setParameter("id", id);
        	q1.setParameter("name", name);
        	q1.setParameter("bathrooms", bathrooms);
        	q1.setParameter("bedrooms", bedrooms);
        	q1.setParameter("beds", beds);
        	q1.setParameter("accommodates", accommodates);
        	q1.setParameter("summary", summary);
        	q1.setParameter("city", city);
        	q1.setParameter("country", country);
        	q1.setParameter("propertyType", propertyType);
        	q1.setParameter("bedType", bedType);
        	q1.setParameter("price", price);
        	q1.setParameter("weeklyPrice", weeklyPrice);
        	q1.setParameter("monthlyPrice", monthlyPrice);
        	q1.setParameter("cleaningFee", cleaningFee);
        	q1.setParameter("roomType", roomType);
        	q1.setParameter("extraPeople", extraPeople);
        	q1.setParameter("wifi", wifi);
        	q1.setParameter("tv", tv);
        	q1.setParameter("parking", parking);
        	q1.setParameter("elevator", elevator);
        	q1.setParameter("kitchen", kitchen);
        	q1.setParameter("ac", ac);
        	q1.setParameter("heating", heating);
        	q1.setParameter("squareFeet", squareFeet);
        	
        	q1.executeUpdate();
	        
	        
	    	tx.commit();
	    	em.close();
	        
	        
	    }
		
}
