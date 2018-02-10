package db;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Review;

public class ReviewDB {
	 @SuppressWarnings("unchecked")
		public List<Review> getReviews()
	    {
	        List<Review> reviews = null;
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        //Query q = em.createQuery("Select u from User u");
	        Query q = em.createNamedQuery("Review.findAll");
	        reviews =  q.getResultList();
			
	        tx.commit();
	        em.close();
	        return reviews;
	    }
	    
	   
	    public long insertReview(Review review)
	    {
	    	long id = -1;
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        try 
	        {
	            em.persist(review);
	            em.flush();
	            id = review.getReviewID();
	            tx.commit();
	            return id;
	        }
	        catch (PersistenceException e)
	        {
	            if (tx.isActive()) tx.rollback();
	            return id;
	        }
	        finally 
	        {
	            em.close();
	        }
	    }
	    
	    public Review getById(long id)
	    {
	        Review review = null;
	        
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        review = em.find(Review.class, id);
		
	        tx.commit();
	        em.close();
	        
	        
	        return review;
	        
	    }
}
