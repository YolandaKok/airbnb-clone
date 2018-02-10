package entities;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class Main {
	public static void main(String [ ] args) {
	String s = "2014-05-01";
	String e = "2014-05-16";
	LocalDate start = LocalDate.parse(s);
	LocalDate end = LocalDate.parse(e);
	List<LocalDate> totalDates = new ArrayList<LocalDate>();
	while (!start.isAfter(end)) {
	    	totalDates.add(start);
	    	start = start.plusDays(1);
		}
	System.out.println(totalDates);
	}
}


/* em.getTransaction().begin();
for (int i = 1; i <= 1000000; i++) {
    Point point = new Point(i, i);
    em.persist(point);
    if ((i % 10000) == 0) {
        em.getTransaction().commit();
        em.clear();          
        em.getTransaction().begin();
    }
}
em.getTransaction().commit();
*/