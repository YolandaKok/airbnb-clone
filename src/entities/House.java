package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the houses database table.
 * 
 */
@Entity
@Table(name="houses")
@NamedQuery(name="House.findAll", query="SELECT h FROM House h")
public class House implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long houseID;

	private boolean ac;

	private int accommodates;

	private String amenities;

	@Column(name="availability_30")
	private int availability30;

	@Column(name="availability_365")
	private int availability365;

	@Column(name="availability_60")
	private int availability60;

	@Column(name="availability_90")
	private int availability90;

	private float bathrooms;

	@Column(name="bed_type")
	private String bedType;

	private int bedrooms;

	private int beds;

	@Column(name="calculated_host_listings_count")
	private int calculatedHostListingsCount;

	@Column(name="calendar_last_scraped")
	private String calendarLastScraped;

	@Column(name="calendar_updated")
	private String calendarUpdated;

	@Column(name="cancellation_policy")
	private String cancellationPolicy;

	private String city;

	@Column(name="cleaning_fee")
	private int cleaningFee;

	private String country;

	@Column(name="country_code")
	private String countryCode;

	private String description;

	private boolean elevator;

	@Column(name="experiences_offered")
	private String experiencesOffered;

	@Column(name="extra_people")
	private int extraPeople;

	@Column(name="first_review")
	private String firstReview;

	@Column(name="guests_included")
	private int guestsIncluded;

	@Column(name="has_availability")
	private String hasAvailability;

	private boolean heating;
	
	@Column(name="hostID")
	private long hostID;
	
	@Column(name="host_about")
	private String hostAbout;

	@Column(name="host_has_profile_pic")
	private String hostHasProfilePic;

	@Column(name="host_identity_verified")
	private String hostIdentityVerified;

	@Column(name="host_is_superhost")
	private String hostIsSuperhost;

	@Column(name="host_listings_count")
	private int hostListingsCount;

	@Column(name="host_location")
	private String hostLocation;

	@Column(name="host_name")
	private String hostName;

	@Column(name="host_neighbourhood")
	private String hostNeighbourhood;

	@Column(name="host_picture_url")
	private String hostPictureUrl;

	@Column(name="host_response_rate")
	private String hostResponseRate;

	@Column(name="host_response_time")
	private String hostResponseTime;

	@Temporal(TemporalType.DATE)
	@Column(name="host_since")
	private Date hostSince;

	@Column(name="host_thumbnail_url")
	private String hostThumbnailUrl;

	@Column(name="host_total_listings_count")
	private int hostTotalListingsCount;

	@Column(name="host_verifications")
	private String hostVerifications;

	private String hostURL;

	private String houseURL;

	@Column(name="instant_bookable")
	private String instantBookable;

	@Column(name="is_location_exact")
	private String isLocationExact;

	@Column(name="jurisdiction_names")
	private String jurisdictionNames;

	private boolean kitchen;

	@Column(name="last_review")
	private String lastReview;

	@Temporal(TemporalType.DATE)
	@Column(name="last_scraped")
	private Date lastScraped;

	private String latitude;

	private String license;

	private String longitude;

	private String market;

	@Column(name="maximum_nights")
	private int maximumNights;

	private String mediumURL;

	@Column(name="minimum_nights")
	private int minimumNights;

	@Column(name="monthly_price")
	private String monthlyPrice;

	private String name;

	@Column(name="neighborhood_overview")
	private String neighborhoodOverview;

	private String neighbourhood;

	@Column(name="neighbourhood_cleansed")
	private String neighbourhoodCleansed;

	@Column(name="neighbourhood_group_cleansed")
	private String neighbourhoodGroupCleansed;

	private String notes;

	@Column(name="number_of_reviews")
	private int numberOfReviews;

	private boolean parking;

	private String pictureURL;

	private int price;

	@Column(name="property_type")
	private String propertyType;

	@Column(name="require_guest_phone_verification")
	private String requireGuestPhoneVerification;

	@Column(name="require_guest_profile_picture")
	private String requireGuestProfilePicture;

	@Column(name="requires_license")
	private String requiresLicense;

	@Column(name="review_scores_accuracy")
	private int reviewScoresAccuracy;

	@Column(name="review_scores_checkin")
	private int reviewScoresCheckin;

	@Column(name="review_scores_cleanliness")
	private int reviewScoresCleanliness;

	@Column(name="review_scores_communication")
	private int reviewScoresCommunication;

	@Column(name="review_scores_location")
	private int reviewScoresLocation;

	@Column(name="review_scores_rating")
	private int reviewScoresRating;

	@Column(name="review_scores_value")
	private int reviewScoresValue;

	@Column(name="reviews_per_month")
	private float reviewsPerMonth;

	@Column(name="room_type")
	private String roomType;

	private String scrapeID;

	@Column(name="security_deposit")
	private String securityDeposit;

	private String space;

	@Column(name="square_feet")
	private int squareFeet;

	private String state;

	private String street;

	private String summary;

	private String thumbnailURL;

	private String transit;

	private boolean tv;

	@Column(name="weekly_price")
	private String weeklyPrice;

	private boolean wifi;

	private String xl_pictureURL;

	private int zipcode;



	public House() {
	}
	
	public long getHostID() {
		return this.hostID;
	}
	
	public void setHostID(long hostID) {
		this.hostID = hostID;
	}

	public long getHouseID() {
		return this.houseID;
	}

	public void setHouseID(long houseID) {
		this.houseID = houseID;
	}

	public boolean getAc() {
		return this.ac;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	public int getAccommodates() {
		return this.accommodates;
	}

	public void setAccommodates(int accommodates) {
		this.accommodates = accommodates;
	}

	public String getAmenities() {
		return this.amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	public int getAvailability30() {
		return this.availability30;
	}

	public void setAvailability30(int availability30) {
		this.availability30 = availability30;
	}

	public int getAvailability365() {
		return this.availability365;
	}

	public void setAvailability365(int availability365) {
		this.availability365 = availability365;
	}

	public int getAvailability60() {
		return this.availability60;
	}

	public void setAvailability60(int availability60) {
		this.availability60 = availability60;
	}

	public int getAvailability90() {
		return this.availability90;
	}

	public void setAvailability90(int availability90) {
		this.availability90 = availability90;
	}

	public float getBathrooms() {
		return this.bathrooms;
	}

	public void setBathrooms(float bathrooms) {
		this.bathrooms = bathrooms;
	}

	public String getBedType() {
		return this.bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public int getBedrooms() {
		return this.bedrooms;
	}

	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}

	public int getBeds() {
		return this.beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getCalculatedHostListingsCount() {
		return this.calculatedHostListingsCount;
	}

	public void setCalculatedHostListingsCount(int calculatedHostListingsCount) {
		this.calculatedHostListingsCount = calculatedHostListingsCount;
	}

	public String getCalendarLastScraped() {
		return this.calendarLastScraped;
	}

	public void setCalendarLastScraped(String calendarLastScraped) {
		this.calendarLastScraped = calendarLastScraped;
	}

	public String getCalendarUpdated() {
		return this.calendarUpdated;
	}

	public void setCalendarUpdated(String calendarUpdated) {
		this.calendarUpdated = calendarUpdated;
	}

	public String getCancellationPolicy() {
		return this.cancellationPolicy;
	}

	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCleaningFee() {
		return this.cleaningFee;
	}

	public void setCleaningFee(int cleaningFee) {
		this.cleaningFee = cleaningFee;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getElevator() {
		return this.elevator;
	}

	public void setElevator(boolean elevator) {
		this.elevator = elevator;
	}

	public String getExperiencesOffered() {
		return this.experiencesOffered;
	}

	public void setExperiencesOffered(String experiencesOffered) {
		this.experiencesOffered = experiencesOffered;
	}

	public int getExtraPeople() {
		return this.extraPeople;
	}

	public void setExtraPeople(int extraPeople) {
		this.extraPeople = extraPeople;
	}

	public String getFirstReview() {
		return this.firstReview;
	}

	public void setFirstReview(String firstReview) {
		this.firstReview = firstReview;
	}

	public int getGuestsIncluded() {
		return this.guestsIncluded;
	}

	public void setGuestsIncluded(int guestsIncluded) {
		this.guestsIncluded = guestsIncluded;
	}

	public String getHasAvailability() {
		return this.hasAvailability;
	}

	public void setHasAvailability(String hasAvailability) {
		this.hasAvailability = hasAvailability;
	}

	public boolean getHeating() {
		return this.heating;
	}

	public void setHeating(boolean heating) {
		this.heating = heating;
	}

	public String getHostAbout() {
		return this.hostAbout;
	}

	public void setHostAbout(String hostAbout) {
		this.hostAbout = hostAbout;
	}

	public String getHostHasProfilePic() {
		return this.hostHasProfilePic;
	}

	public void setHostHasProfilePic(String hostHasProfilePic) {
		this.hostHasProfilePic = hostHasProfilePic;
	}

	public String getHostIdentityVerified() {
		return this.hostIdentityVerified;
	}

	public void setHostIdentityVerified(String hostIdentityVerified) {
		this.hostIdentityVerified = hostIdentityVerified;
	}

	public String getHostIsSuperhost() {
		return this.hostIsSuperhost;
	}

	public void setHostIsSuperhost(String hostIsSuperhost) {
		this.hostIsSuperhost = hostIsSuperhost;
	}

	public int getHostListingsCount() {
		return this.hostListingsCount;
	}

	public void setHostListingsCount(int hostListingsCount) {
		this.hostListingsCount = hostListingsCount;
	}

	public String getHostLocation() {
		return this.hostLocation;
	}

	public void setHostLocation(String hostLocation) {
		this.hostLocation = hostLocation;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostNeighbourhood() {
		return this.hostNeighbourhood;
	}

	public void setHostNeighbourhood(String hostNeighbourhood) {
		this.hostNeighbourhood = hostNeighbourhood;
	}

	public String getHostPictureUrl() {
		return this.hostPictureUrl;
	}

	public void setHostPictureUrl(String hostPictureUrl) {
		this.hostPictureUrl = hostPictureUrl;
	}

	public String getHostResponseRate() {
		return this.hostResponseRate;
	}

	public void setHostResponseRate(String hostResponseRate) {
		this.hostResponseRate = hostResponseRate;
	}

	public String getHostResponseTime() {
		return this.hostResponseTime;
	}

	public void setHostResponseTime(String hostResponseTime) {
		this.hostResponseTime = hostResponseTime;
	}

	public Date getHostSince() {
		return this.hostSince;
	}

	public void setHostSince(Date hostSince) {
		this.hostSince = hostSince;
	}

	public String getHostThumbnailUrl() {
		return this.hostThumbnailUrl;
	}

	public void setHostThumbnailUrl(String hostThumbnailUrl) {
		this.hostThumbnailUrl = hostThumbnailUrl;
	}

	public int getHostTotalListingsCount() {
		return this.hostTotalListingsCount;
	}

	public void setHostTotalListingsCount(int hostTotalListingsCount) {
		this.hostTotalListingsCount = hostTotalListingsCount;
	}

	public String getHostVerifications() {
		return this.hostVerifications;
	}

	public void setHostVerifications(String hostVerifications) {
		this.hostVerifications = hostVerifications;
	}

	public String getHostURL() {
		return this.hostURL;
	}

	public void setHostURL(String hostURL) {
		this.hostURL = hostURL;
	}

	public String getHouseURL() {
		return this.houseURL;
	}

	public void setHouseURL(String houseURL) {
		this.houseURL = houseURL;
	}

	public String getInstantBookable() {
		return this.instantBookable;
	}

	public void setInstantBookable(String instantBookable) {
		this.instantBookable = instantBookable;
	}

	public String getIsLocationExact() {
		return this.isLocationExact;
	}

	public void setIsLocationExact(String isLocationExact) {
		this.isLocationExact = isLocationExact;
	}

	public String getJurisdictionNames() {
		return this.jurisdictionNames;
	}

	public void setJurisdictionNames(String jurisdictionNames) {
		this.jurisdictionNames = jurisdictionNames;
	}

	public boolean getKitchen() {
		return this.kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	public String getLastReview() {
		return this.lastReview;
	}

	public void setLastReview(String lastReview) {
		this.lastReview = lastReview;
	}

	public Date getLastScraped() {
		return this.lastScraped;
	}

	public void setLastScraped(Date lastScraped) {
		this.lastScraped = lastScraped;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMarket() {
		return this.market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public int getMaximumNights() {
		return this.maximumNights;
	}

	public void setMaximumNights(int maximumNights) {
		this.maximumNights = maximumNights;
	}

	public String getMediumURL() {
		return this.mediumURL;
	}

	public void setMediumURL(String mediumURL) {
		this.mediumURL = mediumURL;
	}

	public int getMinimumNights() {
		return this.minimumNights;
	}

	public void setMinimumNights(int minimumNights) {
		this.minimumNights = minimumNights;
	}

	public String getMonthlyPrice() {
		return this.monthlyPrice;
	}

	public void setMonthlyPrice(String monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNeighborhoodOverview() {
		return this.neighborhoodOverview;
	}

	public void setNeighborhoodOverview(String neighborhoodOverview) {
		this.neighborhoodOverview = neighborhoodOverview;
	}

	public String getNeighbourhood() {
		return this.neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getNeighbourhoodCleansed() {
		return this.neighbourhoodCleansed;
	}

	public void setNeighbourhoodCleansed(String neighbourhoodCleansed) {
		this.neighbourhoodCleansed = neighbourhoodCleansed;
	}

	public String getNeighbourhoodGroupCleansed() {
		return this.neighbourhoodGroupCleansed;
	}

	public void setNeighbourhoodGroupCleansed(String neighbourhoodGroupCleansed) {
		this.neighbourhoodGroupCleansed = neighbourhoodGroupCleansed;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getNumberOfReviews() {
		return this.numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	public boolean getParking() {
		return this.parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public String getPictureURL() {
		return this.pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPropertyType() {
		return this.propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getRequireGuestPhoneVerification() {
		return this.requireGuestPhoneVerification;
	}

	public void setRequireGuestPhoneVerification(String requireGuestPhoneVerification) {
		this.requireGuestPhoneVerification = requireGuestPhoneVerification;
	}

	public String getRequireGuestProfilePicture() {
		return this.requireGuestProfilePicture;
	}

	public void setRequireGuestProfilePicture(String requireGuestProfilePicture) {
		this.requireGuestProfilePicture = requireGuestProfilePicture;
	}

	public String getRequiresLicense() {
		return this.requiresLicense;
	}

	public void setRequiresLicense(String requiresLicense) {
		this.requiresLicense = requiresLicense;
	}

	public int getReviewScoresAccuracy() {
		return this.reviewScoresAccuracy;
	}

	public void setReviewScoresAccuracy(int reviewScoresAccuracy) {
		this.reviewScoresAccuracy = reviewScoresAccuracy;
	}

	public int getReviewScoresCheckin() {
		return this.reviewScoresCheckin;
	}

	public void setReviewScoresCheckin(int reviewScoresCheckin) {
		this.reviewScoresCheckin = reviewScoresCheckin;
	}

	public int getReviewScoresCleanliness() {
		return this.reviewScoresCleanliness;
	}

	public void setReviewScoresCleanliness(int reviewScoresCleanliness) {
		this.reviewScoresCleanliness = reviewScoresCleanliness;
	}

	public int getReviewScoresCommunication() {
		return this.reviewScoresCommunication;
	}

	public void setReviewScoresCommunication(int reviewScoresCommunication) {
		this.reviewScoresCommunication = reviewScoresCommunication;
	}

	public int getReviewScoresLocation() {
		return this.reviewScoresLocation;
	}

	public void setReviewScoresLocation(int reviewScoresLocation) {
		this.reviewScoresLocation = reviewScoresLocation;
	}

	public int getReviewScoresRating() {
		return this.reviewScoresRating;
	}

	public void setReviewScoresRating(int reviewScoresRating) {
		this.reviewScoresRating = reviewScoresRating;
	}

	public int getReviewScoresValue() {
		return this.reviewScoresValue;
	}

	public void setReviewScoresValue(int reviewScoresValue) {
		this.reviewScoresValue = reviewScoresValue;
	}

	public float getReviewsPerMonth() {
		return this.reviewsPerMonth;
	}

	public void setReviewsPerMonth(float reviewsPerMonth) {
		this.reviewsPerMonth = reviewsPerMonth;
	}

	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getScrapeID() {
		return this.scrapeID;
	}

	public void setScrapeID(String scrapeID) {
		this.scrapeID = scrapeID;
	}

	public String getSecurityDeposit() {
		return this.securityDeposit;
	}

	public void setSecurityDeposit(String securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getSpace() {
		return this.space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public int getSquareFeet() {
		return this.squareFeet;
	}

	public void setSquareFeet(int squareFeet) {
		this.squareFeet = squareFeet;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThumbnailURL() {
		return this.thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	public String getTransit() {
		return this.transit;
	}

	public void setTransit(String transit) {
		this.transit = transit;
	}

	public boolean getTv() {
		return this.tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public String getWeeklyPrice() {
		return this.weeklyPrice;
	}

	public void setWeeklyPrice(String weeklyPrice) {
		this.weeklyPrice = weeklyPrice;
	}

	public boolean getWifi() {
		return this.wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public String getXl_pictureURL() {
		return this.xl_pictureURL;
	}

	public void setXl_pictureURL(String xl_pictureURL) {
		this.xl_pictureURL = xl_pictureURL;
	}

	public int getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}







}