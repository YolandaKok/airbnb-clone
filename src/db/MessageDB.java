package db;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.UserHasMessage;

public class MessageDB {
	 @SuppressWarnings("unchecked")
		public List<UserHasMessage> getMessages()
	    {
	        List<UserHasMessage> messages = null;
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        //Query q = em.createQuery("Select u from User u");
	        Query q = em.createNamedQuery("UserHasMessage.findAll");
	        messages =  q.getResultList();
			
	        tx.commit();
	        em.close();
	        return messages;
	    }
	 
	 
	 public long insertMessage(UserHasMessage message)
	    {
	    	long id = -1;
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        try 
	        {
	            em.persist(message);
	            em.flush();
	            id = message.getMessageID();
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
	 
	 public UserHasMessage getById(long id)
	    {
	        UserHasMessage message = null;
	        
	        EntityManager em = JPAResource.factory.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        message = em.find(UserHasMessage.class, id);
		
	        tx.commit();
	        em.close();
	        
	        
	        return message;
	        
	    }
	 
	 public void delete(long id)
	 {
		 UserHasMessage message = null;
	        
	     EntityManager em = JPAResource.factory.createEntityManager();
	     EntityTransaction tx = em.getTransaction();
	     tx.begin();
	     message = em.find(UserHasMessage.class, id);
	     em.remove(message);
	     tx.commit();
	     em.close();
	        	        
	 }
	    
}
