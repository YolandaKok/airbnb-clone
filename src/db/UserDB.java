package db;

import entities.User;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


public class UserDB {

    
    @SuppressWarnings("unchecked")
	public List<User> getUsers()
    {
        List<User> users = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from User u");
        Query q = em.createNamedQuery("User.findAll");
        users =  q.getResultList();
		
        tx.commit();
        em.close();
        return users;
    }
    
    public User find(String username, String password)
    {
    	// Find one user
        User user = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // JPA Query API
        Query q = em.createQuery("Select u from User u where u.username = :username and u.password = :password");
        q.setParameter("username", username);
        q.setParameter("password", password);
        @SuppressWarnings("rawtypes")
		List users =  q.getResultList();
        tx.commit();
        em.close();
        
        if (users != null && users.size() == 1)
        {
            user = (User) users.get(0);
        }

        return user;
        
    }
    
    public long insertUser(User user)
    {
    	long id = -1;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            em.persist(user);
            em.flush();
            id = user.getUserID();
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
    
    public User getById(long id)
    {
        User user = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        user = em.find(User.class, id);
	
        tx.commit();
        em.close();
        
        
        return user;
        
    }
    
    // Update User Information
    
    public void updateUser(User user, long id)
    {
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
    	// update user information
        String username = user.getUsername();
        String firstname = user.getFirstName();
        String lastname = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        Date birthday = user.getBirthday();
        String image_URL=user.getImage_URL();
        boolean host = user.getHost();
        boolean verifiedFromAdmin = user.getVerifiedFromAdmin();
        // If password is null
        if (password == null && image_URL == null) {
        	Query q1 = em.createQuery("UPDATE User user SET user.username = :username, user.firstName = :firstname, user.lastName = :lastname, user.birthday = :birthday, user.email = :email, user.host =:host, user.verifiedFromAdmin =:verifiedFromAdmin WHERE user.userID = :id");
        	q1.setParameter("username", username);
            q1.setParameter("firstname", firstname);
            q1.setParameter("lastname", lastname);
            q1.setParameter("email", email);
            q1.setParameter("birthday", birthday);
            q1.setParameter("host", host);
            q1.setParameter("verifiedFromAdmin", verifiedFromAdmin);
            q1.setParameter("id", id);
            // commit
            q1.executeUpdate();

        }
        else if (password == null && image_URL != null) {
        	Query q2 = em.createQuery("UPDATE User user SET user.username = :username, user.firstName = :firstname, user.lastName = :lastname, user.birthday = :birthday, user.email = :email, user.host =:host, user.image_URL =:image_URL, user.verifiedFromAdmin =:verifiedFromAdmin WHERE user.userID = :id");
        	q2.setParameter("username", username);
            q2.setParameter("firstname", firstname);
            q2.setParameter("lastname", lastname);
            q2.setParameter("email", email);
            q2.setParameter("birthday", birthday);
            q2.setParameter("host", host);
            q2.setParameter("image_URL", image_URL);
            q2.setParameter("verifiedFromAdmin", verifiedFromAdmin);
            q2.setParameter("id", id);
            // commit
            q2.executeUpdate();
        }
        else if (password != null && image_URL == null) {
        	Query q3 = em.createQuery("UPDATE User user SET user.username = :username, user.firstName = :firstname, user.lastName = :lastname, user.birthday = :birthday, user.email = :email, user.host =:host, user.password =:password, user.verifiedFromAdmin =:verifiedFromAdmin WHERE user.userID = :id");
        	q3.setParameter("username", username);
            q3.setParameter("firstname", firstname);
            q3.setParameter("lastname", lastname);
            q3.setParameter("email", email);
            q3.setParameter("birthday", birthday);
            q3.setParameter("host", host);
            q3.setParameter("password", password);
            q3.setParameter("verifiedFromAdmin", verifiedFromAdmin);
            q3.setParameter("id", id);
            // commit
            q3.executeUpdate();
        }
        else {
        Query q = em.createQuery("UPDATE User user SET user.username = :username, user.firstName = :firstname, user.lastName = :lastname, user.birthday = :birthday, user.email = :email, user.password = :password, user.image_URL =:image_URL, user.host =:host, user.verifiedFromAdmin =:verifiedFromAdmin WHERE user.userID = :id");
        q.setParameter("username", username);
        q.setParameter("firstname", firstname);
        q.setParameter("lastname", lastname);
        q.setParameter("email", email);
        q.setParameter("birthday", birthday);
        q.setParameter("password", password);
        q.setParameter("image_URL", image_URL);
        q.setParameter("host", host);
        q.setParameter("verifiedFromAdmin", verifiedFromAdmin);
        q.setParameter("id", id);
        // commit
        q.executeUpdate();
        }
    	tx.commit();
    	em.close();
    }
    
}