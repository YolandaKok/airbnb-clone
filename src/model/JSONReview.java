package model;

import java.util.Date;

import entities.House;

public class JSONReview {
	private long reviewID;
	
	private String comments;
	
	private Date date;
	
	private long stars;
		
	private long houseID;
	
	private long reviewerID;

	public long getReviewID() {
		return reviewID;
	}

	public void setReviewID(long reviewID) {
		this.reviewID = reviewID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getHouseID() {
		return houseID;
	}

	public void setHouseID(long house) {
		this.houseID = house;
	}


	
	public void setStars(long stars){
		this.stars= stars;
	}
	
	public long getStars(){
		return stars;
	}

	public long getReviewerID() {
		return reviewerID;
	}

	public void setReviewerID(long reviewerID) {
		this.reviewerID = reviewerID;
	}

}