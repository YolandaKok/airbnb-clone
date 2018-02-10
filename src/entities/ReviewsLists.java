package entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReviewsLists {
    private List<Review> reviews_list = new ArrayList<Review>();

	public List<Review> getReviews_list() {
		return reviews_list;
	}
	
	public void setReviews_list(List<Review> reviews_list) {
		this.reviews_list = reviews_list;
	}

}
