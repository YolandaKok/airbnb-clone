package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the reviews database table.
 * 
 */
@Entity
@Table(name="reviews")
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r")
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long reviewID;

	private String comments;

	@Temporal(TemporalType.DATE)
	private Date date;

	
	private long reviewerID;
	
	private long houseID;

	@Column(name="stars")
	private long stars;



	public Review() {
	}

	public long getReviewID() {
		return this.reviewID;
	}

	public void setReviewID(long reviewID) {
		this.reviewID = reviewID;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getStars() {
		return this.stars;
	}

	public void setStars(long stars) {
		this.stars = stars;
	}



	public long getReviewerID() {
		return reviewerID;
	}

	public void setReviewerID(long reviewerID) {
		this.reviewerID = reviewerID;
	}

	public long getHouseID() {
		return houseID;
	}

	public void setHouseID(long houseID) {
		this.houseID = houseID;
	}

}