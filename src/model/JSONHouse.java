package model;

import java.util.Date;

public class JSONHouse {

	private long houseID;

	private int accommodates;

	private String amenities;
	
	private int availability30;

	private int availability365;

	private int availability60;

	private int availability90;

	private float bathrooms;

	private String bedType;

	private int bedrooms;

	private int beds;

	private int calculatedHostListingsCount;

	private String calendarLastScraped;

	// SOS DATE
	private String calendarUpdated;

	private String cancellationPolicy;

	private String city;

	private int cleaningFee;

	private String country;

	private String countryCode;

	private String description;

	private String experiencesOffered;

	private int extraPeople;

	private String firstReview;

	private int guestsIncluded;

	private String hasAvailability;

	private String hostAbout;

	private long hostID;
	
	private String hostHasProfilePic;

	private String hostIdentityVerified;

	private String hostIsSuperhost;

	private int hostListingsCount;

	private String hostLocation;

	private String hostName;

	private String hostNeighbourhood;

	private String hostPictureUrl;

	private String hostResponseRate;

	private String hostResponseTime;

	private Date hostSince;

	private String hostThumbnailUrl;

	private int hostTotalListingsCount;

	private String hostVerifications;

	private String hostURL;

	private String houseURL;

	private String instantBookable;

	private String isLocationExact;

	private String jurisdictionNames;

	private String lastReview;

	private Date lastScraped;

	private String latitude;

	private String license;

	private String longitude;

	private String market;

	private int maximumNights;

	private String mediumURL;

	private int minimumNights;

	private String monthlyPrice;

	private String name;

	private String neighborhoodOverview;

	private String neighbourhood;

	private String neighbourhoodCleansed;

	private String neighbourhoodGroupCleansed;

	private String notes;

	private int numberOfReviews;

	// The final image of the house base64 GET method
	private String pictureURL;
	
	// The base64 code in POST
	private byte[] image_URL;

	// The image shortcut to open the file 
	private String image_shortcut;
	
	private int price;

	private String propertyType;

	private String requireGuestPhoneVerification;

	private String requireGuestProfilePicture;

	private String requiresLicense;

	private int reviewScoresAccuracy;

	private int reviewScoresCheckin;

	private int reviewScoresCleanliness;

	private int reviewScoresCommunication;

	private int reviewScoresLocation;

	private int reviewScoresRating;

	private int reviewScoresValue;

	private float reviewsPerMonth;

	private String roomType;

	private String scrapeID;

	private String securityDeposit;

	private String space;

	private int squareFeet;

	private String state;

	private String street;

	private String summary;

	private String thumbnailURL;

	private String transit;

	private String weeklyPrice;

	private String xl_pictureURL;

	private int zipcode;
	
	// Amenities
	
	private boolean ac;
	
	private boolean elevator;
	
	private boolean heating;
	
	private boolean kitchen;
	
	private boolean parking;
	
	private boolean tv;
	
	private boolean wifi;


	public long getHouseID() {
		return houseID;
	}

	public void setHouseID(long houseID) {
		this.houseID = houseID;
	}

	public int getAccommodates() {
		return accommodates;
	}

	public void setAccommodates(int accommodates) {
		this.accommodates = accommodates;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}
	
	public int getAvailability30() {
		return availability30;
	}

	public void setAvailability30(int availability30) {
		this.availability30 = availability30;
	}

	public int getAvailability365() {
		return availability365;
	}

	public void setAvailability365(int availability365) {
		this.availability365 = availability365;
	}

	public int getAvailability60() {
		return availability60;
	}

	public void setAvailability60(int availability60) {
		this.availability60 = availability60;
	}

	public int getAvailability90() {
		return availability90;
	}

	public void setAvailability90(int availability90) {
		this.availability90 = availability90;
	}

	public float getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(float bathrooms) {
		this.bathrooms = bathrooms;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public int getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getCalculatedHostListingsCount() {
		return calculatedHostListingsCount;
	}

	public void setCalculatedHostListingsCount(int calculatedHostListingsCount) {
		this.calculatedHostListingsCount = calculatedHostListingsCount;
	}

	public String getCalendarLastScraped() {
		return calendarLastScraped;
	}

	public void setCalendarLastScraped(String calendarLastScraped) {
		this.calendarLastScraped = calendarLastScraped;
	}

	public String getCalendarUpdated() {
		return calendarUpdated;
	}

	public void setCalendarUpdated(String calendarUpdated) {
		this.calendarUpdated = calendarUpdated;
	}

	public String getCancellationPolicy() {
		return cancellationPolicy;
	}

	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCleaningFee() {
		return cleaningFee;
	}

	public void setCleaningFee(int cleaningFee) {
		this.cleaningFee = cleaningFee;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExperiencesOffered() {
		return experiencesOffered;
	}

	public void setExperiencesOffered(String experiencesOffered) {
		this.experiencesOffered = experiencesOffered;
	}

	public int getExtraPeople() {
		return extraPeople;
	}

	public void setExtraPeople(int extraPeople) {
		this.extraPeople = extraPeople;
	}

	public String getFirstReview() {
		return firstReview;
	}

	public void setFirstReview(String firstReview) {
		this.firstReview = firstReview;
	}

	public int getGuestsIncluded() {
		return guestsIncluded;
	}

	public void setGuestsIncluded(int guestsIncluded) {
		this.guestsIncluded = guestsIncluded;
	}

	public String getHasAvailability() {
		return hasAvailability;
	}

	public void setHasAvailability(String hasAvailability) {
		this.hasAvailability = hasAvailability;
	}

	public String getHostAbout() {
		return hostAbout;
	}

	public void setHostAbout(String hostAbout) {
		this.hostAbout = hostAbout;
	}

	public String getHostHasProfilePic() {
		return hostHasProfilePic;
	}

	public void setHostHasProfilePic(String hostHasProfilePic) {
		this.hostHasProfilePic = hostHasProfilePic;
	}

	public String getHostIdentityVerified() {
		return hostIdentityVerified;
	}

	public void setHostIdentityVerified(String hostIdentityVerified) {
		this.hostIdentityVerified = hostIdentityVerified;
	}

	public String getHostIsSuperhost() {
		return hostIsSuperhost;
	}

	public void setHostIsSuperhost(String hostIsSuperhost) {
		this.hostIsSuperhost = hostIsSuperhost;
	}

	public int getHostListingsCount() {
		return hostListingsCount;
	}

	public void setHostListingsCount(int hostListingsCount) {
		this.hostListingsCount = hostListingsCount;
	}

	public String getHostLocation() {
		return hostLocation;
	}

	public void setHostLocation(String hostLocation) {
		this.hostLocation = hostLocation;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostNeighbourhood() {
		return hostNeighbourhood;
	}

	public void setHostNeighbourhood(String hostNeighbourhood) {
		this.hostNeighbourhood = hostNeighbourhood;
	}

	public String getHostPictureUrl() {
		return hostPictureUrl;
	}

	public void setHostPictureUrl(String hostPictureUrl) {
		this.hostPictureUrl = hostPictureUrl;
	}

	public String getHostResponseRate() {
		return hostResponseRate;
	}

	public void setHostResponseRate(String hostResponseRate) {
		this.hostResponseRate = hostResponseRate;
	}

	public String getHostResponseTime() {
		return hostResponseTime;
	}

	public void setHostResponseTime(String hostResponseTime) {
		this.hostResponseTime = hostResponseTime;
	}

	public Date getHostSince() {
		return hostSince;
	}

	public void setHostSince(Date hostSince) {
		this.hostSince = hostSince;
	}

	public String getHostThumbnailUrl() {
		return hostThumbnailUrl;
	}

	public void setHostThumbnailUrl(String hostThumbnailUrl) {
		this.hostThumbnailUrl = hostThumbnailUrl;
	}

	public int getHostTotalListingsCount() {
		return hostTotalListingsCount;
	}

	public void setHostTotalListingsCount(int hostTotalListingsCount) {
		this.hostTotalListingsCount = hostTotalListingsCount;
	}

	public String getHostVerifications() {
		return hostVerifications;
	}

	public void setHostVerifications(String hostVerifications) {
		this.hostVerifications = hostVerifications;
	}

	public String getHostURL() {
		return hostURL;
	}

	public void setHostURL(String hostURL) {
		this.hostURL = hostURL;
	}

	public String getHouseURL() {
		return houseURL;
	}

	public void setHouseURL(String houseURL) {
		this.houseURL = houseURL;
	}

	public String getInstantBookable() {
		return instantBookable;
	}

	public void setInstantBookable(String instantBookable) {
		this.instantBookable = instantBookable;
	}

	public String getIsLocationExact() {
		return isLocationExact;
	}

	public void setIsLocationExact(String isLocationExact) {
		this.isLocationExact = isLocationExact;
	}

	public String getJurisdictionNames() {
		return jurisdictionNames;
	}

	public void setJurisdictionNames(String jurisdictionNames) {
		this.jurisdictionNames = jurisdictionNames;
	}

	public String getLastReview() {
		return lastReview;
	}

	public void setLastReview(String lastReview) {
		this.lastReview = lastReview;
	}

	public Date getLastScraped() {
		return lastScraped;
	}

	public void setLastScraped(Date lastScraped) {
		this.lastScraped = lastScraped;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public int getMaximumNights() {
		return maximumNights;
	}

	public void setMaximumNights(int maximumNights) {
		this.maximumNights = maximumNights;
	}

	public String getMediumURL() {
		return mediumURL;
	}

	public void setMediumURL(String mediumURL) {
		this.mediumURL = mediumURL;
	}

	public int getMinimumNights() {
		return minimumNights;
	}

	public void setMinimumNights(int minimumNights) {
		this.minimumNights = minimumNights;
	}

	public String getMonthlyPrice() {
		return monthlyPrice;
	}

	public void setMonthlyPrice(String monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNeighborhoodOverview() {
		return neighborhoodOverview;
	}

	public void setNeighborhoodOverview(String neighborhoodOverview) {
		this.neighborhoodOverview = neighborhoodOverview;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getNeighbourhoodCleansed() {
		return neighbourhoodCleansed;
	}

	public void setNeighbourhoodCleansed(String neighbourhoodCleansed) {
		this.neighbourhoodCleansed = neighbourhoodCleansed;
	}

	public String getNeighbourhoodGroupCleansed() {
		return neighbourhoodGroupCleansed;
	}

	public void setNeighbourhoodGroupCleansed(String neighbourhoodGroupCleansed) {
		this.neighbourhoodGroupCleansed = neighbourhoodGroupCleansed;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getRequireGuestPhoneVerification() {
		return requireGuestPhoneVerification;
	}

	public void setRequireGuestPhoneVerification(String requireGuestPhoneVerification) {
		this.requireGuestPhoneVerification = requireGuestPhoneVerification;
	}

	public String getRequireGuestProfilePicture() {
		return requireGuestProfilePicture;
	}

	public void setRequireGuestProfilePicture(String requireGuestProfilePicture) {
		this.requireGuestProfilePicture = requireGuestProfilePicture;
	}

	public String getRequiresLicense() {
		return requiresLicense;
	}

	public void setRequiresLicense(String requiresLicense) {
		this.requiresLicense = requiresLicense;
	}

	public int getReviewScoresAccuracy() {
		return reviewScoresAccuracy;
	}

	public void setReviewScoresAccuracy(int reviewScoresAccuracy) {
		this.reviewScoresAccuracy = reviewScoresAccuracy;
	}

	public int getReviewScoresCheckin() {
		return reviewScoresCheckin;
	}

	public void setReviewScoresCheckin(int reviewScoresCheckin) {
		this.reviewScoresCheckin = reviewScoresCheckin;
	}

	public int getReviewScoresCleanliness() {
		return reviewScoresCleanliness;
	}

	public void setReviewScoresCleanliness(int reviewScoresCleanliness) {
		this.reviewScoresCleanliness = reviewScoresCleanliness;
	}

	public int getReviewScoresCommunication() {
		return reviewScoresCommunication;
	}

	public void setReviewScoresCommunication(int reviewScoresCommunication) {
		this.reviewScoresCommunication = reviewScoresCommunication;
	}

	public int getReviewScoresLocation() {
		return reviewScoresLocation;
	}

	public void setReviewScoresLocation(int reviewScoresLocation) {
		this.reviewScoresLocation = reviewScoresLocation;
	}

	public int getReviewScoresRating() {
		return reviewScoresRating;
	}

	public void setReviewScoresRating(int reviewScoresRating) {
		this.reviewScoresRating = reviewScoresRating;
	}

	public int getReviewScoresValue() {
		return reviewScoresValue;
	}

	public void setReviewScoresValue(int reviewScoresValue) {
		this.reviewScoresValue = reviewScoresValue;
	}

	public float getReviewsPerMonth() {
		return reviewsPerMonth;
	}

	public void setReviewsPerMonth(float reviewsPerMonth) {
		this.reviewsPerMonth = reviewsPerMonth;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getScrapeID() {
		return scrapeID;
	}

	public void setScrapeID(String scrapeID) {
		this.scrapeID = scrapeID;
	}

	public String getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(String securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public int getSquareFeet() {
		return squareFeet;
	}

	public void setSquareFeet(int squareFeet) {
		this.squareFeet = squareFeet;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	public String getTransit() {
		return transit;
	}

	public void setTransit(String transit) {
		this.transit = transit;
	}

	public String getWeeklyPrice() {
		return weeklyPrice;
	}

	public void setWeeklyPrice(String weeklyPrice) {
		this.weeklyPrice = weeklyPrice;
	}

	public String getXl_pictureURL() {
		return xl_pictureURL;
	}

	public void setXl_pictureURL(String xl_pictureURL) {
		this.xl_pictureURL = xl_pictureURL;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public long gethostID() {
		return hostID;
	}

	public void sethostID(long hostID) {
		this.hostID = hostID;
	}

	public byte[] getImage_URL() {
		return image_URL;
	}

	public void setImage_URL(byte[] image_URL) {
		this.image_URL = image_URL;
	}

	public String getImage_shortcut() {
		return image_shortcut;
	}

	public void setImage_shortcut(String image_shortcut) {
		this.image_shortcut = image_shortcut;
	}

	public boolean getAc() {
		return ac;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	public boolean getElevator() {
		return elevator;
	}

	public void setElevator(boolean elevator) {
		this.elevator = elevator;
	}

	public boolean getHeating() {
		return heating;
	}

	public void setHeating(boolean heating) {
		this.heating = heating;
	}

	public boolean getKitchen() {
		return kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	public boolean getParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean getTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public boolean getWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}
	
}
