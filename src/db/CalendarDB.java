package db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import entities.Calendar;
import model.JSONCalendar;

public class CalendarDB {
	
	// Find the available places for these dates
	@SuppressWarnings("unchecked")
	public JSONCalendar availableHouses(String startDate, String endDate) {
		// Create a new list with house ID'sz
		List<String> houseIDs = new ArrayList<>();
		List<Calendar> dates = null;
		List<String> housed = null;
		List<Calendar> d = null;
		JSONCalendar ca = new JSONCalendar();
		EntityManager em = JPAResource.factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		// Find the houses id's
		
		Query q1 = em.createQuery("SELECT h.houseID from House h");
		// It has all the houseIDs
		housed = q1.getResultList();
		//System.out.println(housed);
		
		Query q = em.createNamedQuery("Calendar.findAll");
		dates = q.getResultList();
		
		for( int i = 0; i < housed.size(); i++ ) {
			// Select all the dates for every house
			int k = 0;
			Query q2 = em.createQuery("SELECT c from Calendar c WHERE c.houseID = :id AND c.date >= :startDate AND c.date < :endDate");
			q2.setParameter("id", housed.get(i));
			q2.setParameter("startDate", startDate);
			q2.setParameter("endDate", endDate);
			d = q2.getResultList();
			
			long idHouse = 0;
			for(int j = 0; j < d.size(); j++) {
				//System.out.println(d.get(j).getHouseID());
				idHouse = d.get(j).getHouseID();
				if(d.get(j).getAvailable() == true) {
					k++;
					// System.out.println(k);
				}
				// Find out if the houses are available these days
				
			}
			if(k == d.size() && k != 0) {
				// System.out.println(k);
				houseIDs.add(String.valueOf(idHouse));
			}
			
		}
		// Select all the houses
		
		ca.setResults(houseIDs);
		ca.setNumDates(d.size());
		// System.out.println(startDate + " " + endDate);
		// System.out.println(houseIDs);
		
		
		return ca;
	}
	
	
	public List<Calendar> getReservations()
    {
        List<Calendar> reservations = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from User u");
        Query q = em.createQuery("Select c from Calendar c where c.available=:available");
        q.setParameter("available", false);
        reservations =  q.getResultList();
		
        tx.commit();
        em.close();
        return reservations;
    }
	
	
	
	@SuppressWarnings("unchecked")
	public boolean updateCalendar(String startDate, String endDate, long houseid, long guest) {
		
		EntityManager em = JPAResource.factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// Select Calendar with this house id
        Query q1 = em.createQuery("UPDATE Calendar calendar SET calendar.available = :available, calendar.guestID = :guest WHERE calendar.houseID = :id AND calendar.date >= :startDate AND calendar.date < :endDate");
        q1.setParameter("id", houseid);
        q1.setParameter("available", false);
        q1.setParameter("startDate", startDate);
        q1.setParameter("guest", guest);
        q1.setParameter("endDate", endDate);
		
        q1.executeUpdate();
        
        tx.commit();
        em.close();
        
		return true;
	}
	
}