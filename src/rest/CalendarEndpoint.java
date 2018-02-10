package rest;

import java.io.File;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.JSONCalendar;
import db.CalendarDB;
import entities.CalendarLists;

@Path("/calendar")
public class CalendarEndpoint {
	@GET
	@Path("{startDate}/{endDate}")
	@Produces({ "application/json" })
	public JSONCalendar listAll(@PathParam("startDate") final String startDate, @PathParam("endDate") final String endDate) {
		CalendarDB calendarDao = new CalendarDB();
		JSONCalendar ca = new JSONCalendar();
		ca = calendarDao.availableHouses(startDate, endDate);
		
		return ca;
	}
	
	// Make a put request to update the availability
	
	@PUT
	@Path("{startDate}/{endDate}/{houseid}/{guest}")
	@Produces({ "application/json" })
	public Response updateCalendar(@PathParam("startDate") final String startDate, @PathParam("endDate") final String endDate, @PathParam("houseid") final long houseid, @PathParam("guest") final long guest) {
		CalendarDB calendarDao = new CalendarDB();
		calendarDao.updateCalendar(startDate, endDate, houseid, guest);
		
		// Update the data for the house with this id
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/export")		
	public void export() throws JAXBException{		
		String userHomeFolder = System.getProperty("user.home");		
		File file = new File(userHomeFolder,"calendar.xml");		
		JAXBContext jaxbContext = JAXBContext.newInstance(CalendarLists.class);		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();		
		CalendarDB calendarDao = new CalendarDB();		
		List<entities.Calendar> calendarsd = calendarDao.getReservations();		
		CalendarLists calendars=new CalendarLists();		
		for(entities.Calendar unique_calendar: calendarsd){		
			calendars.getCalendar_list().add(unique_calendar);		
		}		
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);		
		jaxbMarshaller.marshal(calendars, file);		
		//jaxbMarshaller.marshal(houses, System.out);		
				
	}
	
	
}
