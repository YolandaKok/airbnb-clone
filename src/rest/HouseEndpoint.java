package rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;

import db.HouseDB;
import model.JSONHouse;
import model.JSONUser;
import entities.House;		
import entities.HousesLists;
import javax.xml.bind.JAXBContext;		
import javax.xml.bind.JAXBException;		
import javax.xml.bind.Marshaller;		
import javax.xml.bind.Unmarshaller;


@Path("/houses")
public class HouseEndpoint {
	
	@GET
	@Produces({ "application/json" })
	public List<JSONHouse> listAll() throws IOException {
		HouseDB userDao = new HouseDB();
		List<entities.House> housesd = userDao.getHouses();
		List<JSONHouse> houses = null;
		if (housesd != null && housesd.size()>0)
		{
			houses = new ArrayList<JSONHouse>();
			for (entities.House housed : housesd)
			{
				JSONHouse house = new JSONHouse();
				
				if( housed.getPictureURL() != null )
				{
					InputStream in = new FileInputStream(new File(housed.getPictureURL()));
			        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			        StringBuilder out = new StringBuilder();
			        String line;
			        try {
						while ((line = reader.readLine()) != null) {
						    out.append(line);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        house.setPictureURL(out.toString());
			        reader.close();				
				}
				
				if(housed.getPictureURL()!=null)
					house.setImage_shortcut(housed.getPictureURL());
				
				
				// Host ID
				house.sethostID(housed.getHostID());
				// HouseID
				house.setHouseID(housed.getHouseID());
				// House Name
				house.setName(housed.getName());
				// Summary
				house.setSummary(housed.getSummary());
				// Space
				house.setSpace(housed.getSpace());
				// Description
				house.setDescription(housed.getDescription());
				// Country
				house.setCountry(housed.getCountry());
				// Country Code
				house.setCountryCode(housed.getCountryCode());
				// City
				house.setCity(housed.getCity());
				// State
				house.setState(housed.getState());
				// Zipcode
				house.setZipcode(housed.getZipcode());
				// Street
				house.setStreet(housed.getStreet());
				// Neighborhood
				house.setNeighbourhood(housed.getNeighbourhood());
				// Transportation
				house.setTransit(housed.getTransit());
				// Bathrooms
				house.setBathrooms(housed.getBathrooms());
				// Bedrooms
				house.setBedrooms(housed.getBedrooms());
				// Beds
				house.setBeds(housed.getBeds());
				// SquareFeet
				house.setSquareFeet(housed.getSquareFeet());
				// Latitude
				house.setLatitude(housed.getLatitude());
				// Longtitude
				house.setLongitude(housed.getLongitude());
				// Accommodates
				house.setAccommodates(housed.getAccommodates());
				// Price
				house.setPrice(housed.getPrice());
				// WeeklyPrice
				house.setWeeklyPrice(housed.getWeeklyPrice());
				// MonthlyPrice
				house.setMonthlyPrice(housed.getMonthlyPrice());
				// extraPeople
				house.setExtraPeople(housed.getExtraPeople());
				// cleaningFee
				house.setCleaningFee(housed.getCleaningFee());
				// Property Type
				house.setPropertyType(housed.getPropertyType());
				// BedType
				house.setBedType(housed.getBedType());
				// Roomtype
				house.setRoomType(housed.getRoomType());
				// AC, Pets allowed etc 
				house.setAmenities(housed.getAmenities());
				//private int availability30;
				//private int availability365;
				//private int availability60;
				//private int availability90;
				//private int calculatedHostListingsCount;
				//private String calendarLastScraped;
				//private String calendarUpdated;
				//private String cancellationPolicy;
				//private String experiencesOffered;
				//private String firstReview;
				//private int guestsIncluded;
				//private String hasAvailability;
				house.setHostAbout(housed.getHostAbout());
				//private String hostHasProfilePic;
				//private String hostIdentityVerified;
				//private String hostIsSuperhost;
				//private int hostListingsCount;
				house.setHostLocation(housed.getHostLocation());
				house.setHostName(housed.getHostName());
				//private String hostNeighbourhood;
				//private String hostPictureUrl;
				//private String hostResponseRate;
				//private String hostResponseTime;
				//private Date hostSince;
				//private String hostThumbnailUrl;
				//private int hostTotalListingsCount;
				//private String hostVerifications;
				house.setHostURL(housed.getHostURL());
				//private String houseURL;
				//private String instantBookable;
				//private String isLocationExact;
				//private String jurisdictionNames;
				//private String lastReview;
				//private Date lastScraped;
				//private String license;
				//private String market;
				//private int maximumNights;
				//private String mediumURL;
				//private int minimumNights;
				//private String neighborhoodOverview;
				//private String neighbourhoodCleansed;
				//private String neighbourhoodGroupCleansed;
				//private String notes;
				//private int numberOfReviews;
				//private String pictureURL;
				//private String requireGuestPhoneVerification;
				//private String requireGuestProfilePicture;
				//private String requiresLicense;
				//private int reviewScoresAccuracy;
				//private int reviewScoresCheckin;
				//private int reviewScoresCleanliness;
				//private int reviewScoresCommunication;
				//private int reviewScoresLocation;
				//private int reviewScoresRating;
				//private int reviewScoresValue;
				//private float reviewsPerMonth;
				house.setScrapeID(housed.getScrapeID());
				//private String securityDeposit;
				//private String space;
				//private String thumbnailURL;
				//private String xl_pictureURL;
				// Amenities
				house.setAc(housed.getAc());
				house.setElevator(housed.getElevator());
				house.setHeating(housed.getHeating());
				house.setKitchen(housed.getKitchen());
				house.setParking(housed.getKitchen());
				house.setTv(housed.getTv());
				house.setWifi(housed.getWifi());
				
				houses.add(house);
			}
		}	
		return houses;
	}
	
	// Get a House by ID
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final long id) throws IOException {
		HouseDB houseDao = new HouseDB();
		entities.House housed = houseDao.getById(id);
		JSONHouse house = null;
		if (housed != null) {
			// Creates simple object from model.House to convert it to JSON
			house = new JSONHouse();
			// house.(housed);
			// Host ID
			house.sethostID(housed.getHostID());
			// HouseID
			house.setHouseID(housed.getHouseID());
			// House Name
			house.setName(housed.getName());
			// Summary
			house.setSummary(housed.getSummary());
			// Space
			house.setSpace(housed.getSpace());
			// Description
			house.setDescription(housed.getDescription());
			// Country
			house.setCountry(housed.getCountry());
			// Country Code
			house.setCountryCode(housed.getCountryCode());
			// City
			house.setCity(housed.getCity());
			// State
			house.setState(housed.getState());
			// Zipcode
			house.setZipcode(housed.getZipcode());
			// Street
			house.setStreet(housed.getStreet());
			// Neighborhood
			house.setNeighbourhood(housed.getNeighbourhood());
			// Transportation
			house.setTransit(housed.getTransit());
			// Bathrooms
			house.setBathrooms(housed.getBathrooms());
			// Bedrooms
			house.setBedrooms(housed.getBedrooms());
			// Beds
			house.setBeds(housed.getBeds());
			// SquareFeet
			house.setSquareFeet(housed.getSquareFeet());
			// Latitude
			house.setLatitude(housed.getLatitude());
			// Longtitude
			house.setLongitude(housed.getLongitude());
			// Accommodates
			house.setAccommodates(housed.getAccommodates());
			// Price
			house.setPrice(housed.getPrice());
			// WeeklyPrice
			house.setWeeklyPrice(housed.getWeeklyPrice());
			// MonthlyPrice
			house.setMonthlyPrice(housed.getMonthlyPrice());
			// extraPeople
			house.setExtraPeople(housed.getExtraPeople());
			// cleaningFee
			house.setCleaningFee(housed.getCleaningFee());
			// Property Type
			house.setPropertyType(housed.getPropertyType());
			// BedType
			house.setBedType(housed.getBedType());
			// Roomtype
			house.setRoomType(housed.getRoomType());
			//amenities
			house.setAmenities(housed.getAmenities());
			
			house.setAc(housed.getAc());
			house.setElevator(housed.getElevator());
			house.setHeating(housed.getHeating());
			house.setKitchen(housed.getKitchen());
			house.setParking(housed.getKitchen());
			house.setTv(housed.getTv());
			house.setWifi(housed.getWifi());
			
			if( housed.getPictureURL() != null )
			{
				InputStream in = new FileInputStream(new File(housed.getPictureURL()));
		        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		        StringBuilder out = new StringBuilder();
		        String line;
		        while ((line = reader.readLine()) != null) {
		            out.append(line);
		        }
		        house.setPictureURL(out.toString());
		        reader.close();				
			}
			if(housed.getPictureURL()!=null)
				house.setImage_shortcut(housed.getPictureURL());
			
			
		}
		if (house == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(house).build();
	}
	
	
	// POST a new House
	@POST
	@Consumes({ "application/json" })
	public Response create(final JSONHouse house) {
		entities.House housed = new entities.House();
		
		String image_path ="";
		
		String logFileName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
		//System.out.print(user.getImage_URL());
		logFileName = "loggerFile_" + logFileName; //create a unique file
		image_path=System.getProperty("catalina.base")+"/temp/"+logFileName; //store it in a server path-->/home/dimitra/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/temp
		try {
			FileUtils.writeByteArrayToFile(new File(image_path), (house.getImage_URL()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Image path in the server filesystem
		housed.setPictureURL(image_path);

		
		// HostID
		housed.setHostID(house.gethostID());
		// Name
		housed.setName(house.getName());
		// Summary
		housed.setSummary(house.getSummary());
		// Space
		housed.setSpace(house.getSpace());
		// Description
		housed.setDescription(house.getDescription());
		// Country
		housed.setCountry(house.getCountry());
		// City
		housed.setCity(house.getCity());
		// State
		housed.setState(house.getState());
		// zipcode
		housed.setZipcode(house.getZipcode());
		// Street 
		housed.setStreet(house.getStreet());
		// Neighborhood
		housed.setNeighbourhood(house.getNeighbourhood());
		// Transportation
		housed.setTransit(house.getTransit());
		// Bathrooms
		housed.setBathrooms(house.getBathrooms());
		// Bedrooms
		housed.setBedrooms(house.getBedrooms());
		// Beds
		housed.setBeds(house.getBeds());
		// Square Feet
		housed.setSquareFeet(house.getSquareFeet());
		// Monthly Price
		housed.setMonthlyPrice(house.getMonthlyPrice());
		// Latitude
		housed.setLatitude(house.getLatitude());
		// Longitude
		housed.setLongitude(house.getLongitude());
		// Accommodates
		housed.setAccommodates(house.getAccommodates());
		// Price
		housed.setPrice(house.getPrice());
		// WeeklyPrice
		housed.setWeeklyPrice(house.getWeeklyPrice());
		// MonthlyPrice
		housed.setMonthlyPrice(house.getMonthlyPrice());
		// extraPeople
		housed.setExtraPeople(house.getExtraPeople());
		// cleaningFee
		housed.setCleaningFee(house.getCleaningFee());
		// Property Type
		housed.setPropertyType(house.getPropertyType());
		// BedType
		housed.setBedType(house.getBedType());
		// Roomtype
		housed.setRoomType(house.getRoomType());
		// amenities 
		housed.setAmenities(house.getAmenities());
		
		housed.setAc(house.getAc());
		housed.setElevator(house.getElevator());
		housed.setHeating(house.getHeating());
		housed.setKitchen(house.getKitchen());
		housed.setParking(house.getParking());
		housed.setTv(house.getTv());
		housed.setWifi(house.getWifi());
		
		housed.setCalendarUpdated(house.getCalendarUpdated());
		
		HouseDB houseDao = new HouseDB();
		long id = houseDao.insertHouse(housed);
		return Response.created(
				UriBuilder.fromResource(UserEndpoint.class)
						.path(String.valueOf(id)).build()).build();
	}
	
	@GET
	@Produces({ "application/json" })
	@Path("/host/{hostid}")
	public List<JSONHouse> hostHouses(@PathParam("hostid") final long hostid) throws IOException {
		List<JSONHouse> houses = null;
		
		HouseDB houseDao = new HouseDB();
		List<entities.House> housesd = houseDao.getHostHouses(hostid);
		
		if(housesd != null && housesd.size() > 0) {
			houses = new ArrayList<JSONHouse>();
			for(entities.House housed : housesd)
			{
				JSONHouse house = new JSONHouse();
				
				// Host ID
				house.sethostID(housed.getHostID());
				// HouseID
				house.setHouseID(housed.getHouseID());
				// House Name
				house.setName(housed.getName());
				// Summary
				house.setSummary(housed.getSummary());
				// Space
				house.setSpace(housed.getSpace());
				// Description
				house.setDescription(housed.getDescription());
				// Country
				house.setCountry(housed.getCountry());
				// Country Code
				house.setCountryCode(housed.getCountryCode());
				// City
				house.setCity(housed.getCity());
				// State
				house.setState(housed.getState());
				// Zipcode
				house.setZipcode(housed.getZipcode());
				// Street
				house.setStreet(housed.getStreet());
				// Neighborhood
				house.setNeighbourhood(housed.getNeighbourhood());
				// Transportation
				house.setTransit(housed.getTransit());
				// Bathrooms
				house.setBathrooms(housed.getBathrooms());
				// Bedrooms
				house.setBedrooms(housed.getBedrooms());
				// Beds
				house.setBeds(housed.getBeds());
				// SquareFeet
				house.setSquareFeet(housed.getSquareFeet());
				// Latitude
				house.setLatitude(housed.getLatitude());
				// Longtitude
				house.setLongitude(housed.getLongitude());
				// Accommodates
				house.setAccommodates(housed.getAccommodates());
				// Price
				house.setPrice(housed.getPrice());
				// WeeklyPrice
				house.setWeeklyPrice(housed.getWeeklyPrice());
				// MonthlyPrice
				house.setMonthlyPrice(housed.getMonthlyPrice());
				// extraPeople
				house.setExtraPeople(housed.getExtraPeople());
				// cleaningFee
				house.setCleaningFee(housed.getCleaningFee());
				// Property Type
				house.setPropertyType(housed.getPropertyType());
				// BedType
				house.setBedType(housed.getBedType());
				// Roomtype
				house.setRoomType(housed.getRoomType());
				//amenities
				house.setAmenities(housed.getAmenities());
				
				house.setAc(housed.getAc());
				house.setElevator(housed.getElevator());
				house.setHeating(housed.getHeating());
				house.setKitchen(housed.getKitchen());
				house.setParking(housed.getKitchen());
				house.setTv(housed.getTv());
				house.setWifi(housed.getWifi());
				
				if( housed.getPictureURL() != null )
				{
					InputStream in = new FileInputStream(new File(housed.getPictureURL()));
			        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			        StringBuilder out = new StringBuilder();
			        String line;
			        while ((line = reader.readLine()) != null) {
			            out.append(line);
			        }
			        house.setPictureURL(out.toString());
			        reader.close();				
				}
				if(housed.getPictureURL()!=null)
					house.setImage_shortcut(housed.getPictureURL());
				
				houses.add(house);
			}
		}
		
		return houses;
	}
	
	// Update House Information 
	
	@PUT
	@Produces({ "application/json" })
	@Path("update/{id}")
	public Response updateHouse(@PathParam("id") final long id, final JSONHouse house) {
		
		entities.House housed = new entities.House();
		housed.setName(house.getName());
		housed.setBathrooms(house.getBathrooms());
		housed.setBedrooms(house.getBedrooms());
		housed.setBeds(house.getBeds());
		housed.setAccommodates(house.getAccommodates());
		housed.setSummary(house.getSummary());
		housed.setCity(house.getCity());
		housed.setCountry(house.getCountry());
		housed.setPropertyType(house.getPropertyType());
		housed.setBedType(house.getBedType());
		housed.setPrice(house.getPrice());
		housed.setWeeklyPrice(house.getWeeklyPrice());
		housed.setMonthlyPrice(house.getMonthlyPrice());
		housed.setExtraPeople(house.getExtraPeople());
		housed.setCleaningFee(house.getCleaningFee());
		housed.setRoomType(house.getRoomType());
		housed.setWifi(house.getWifi());
		housed.setTv(house.getTv());
		housed.setParking(house.getParking());
		housed.setKitchen(house.getKitchen());
		housed.setElevator(house.getElevator());
		housed.setHeating(house.getHeating());
		housed.setAc(house.getAc());
		housed.setSquareFeet(house.getSquareFeet());
		
		HouseDB houseDao = new HouseDB();
		houseDao.updateHouse(housed, id);
		
		
		return Response.ok().build();
	}

	
	
	@GET
	@Path("/export")		
	public void export() throws JAXBException{		
		String userHomeFolder = System.getProperty("user.home");		
		File file = new File(userHomeFolder,"houses.xml");		
		JAXBContext jaxbContext = JAXBContext.newInstance(HousesLists.class);		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();		
		HouseDB userDao = new HouseDB();		
		List<entities.House> housesd = userDao.getHouses();		
		HousesLists houses=new HousesLists();		
		for(entities.House unique_house :housesd) {		
			houses.getHouses_list().add(unique_house);		
		}		
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);		
		jaxbMarshaller.marshal(houses, file);		
		//jaxbMarshaller.marshal(houses, System.out);		
				
	}
	
	
}
