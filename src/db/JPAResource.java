package db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Administrator
 */
public class JPAResource {
    
    public static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("airbnb");
    
}
