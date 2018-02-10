package rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import db.ReviewDB;
import entities.ReviewsLists;
import model.JSONReview;

@Path("/reviews")
public class ReviewEndpoint {
	
	@GET
	@Produces({ "application/json" })
	public List<JSONReview> listAll() {
		ReviewDB reviewDao = new ReviewDB();
		List<entities.Review> reviewsd = reviewDao.getReviews();
		List<JSONReview> reviews = null;
		if (reviewsd != null && reviewsd.size()>0)
		{
			reviews = new ArrayList<JSONReview>();
			for (entities.Review reviewd : reviewsd)
			{
				JSONReview review = new JSONReview();
				// Review ID
				review.setReviewID(reviewd.getReviewID());
				// Comments
				review.setComments(reviewd.getComments());
				// Review Date
				review.setDate(reviewd.getDate());
				// House ID
				review.setHouseID(reviewd.getHouseID());
				//Host ID
				review.setReviewerID(reviewd.getReviewerID());
				//stars
				review.setStars(reviewd.getStars());
				reviews.add(review);
			}
		}	
		return reviews;
	}
	
	// Get a Review by ID
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final long id) throws IOException {
		ReviewDB reviewDao = new ReviewDB();
		entities.Review reviewd = reviewDao.getById(id);
		JSONReview review = null;
		if (reviewd != null) {
			// Creates simple object from model.House to convert it to JSON
			review = new JSONReview();
			// Host ID
			review.setHouseID(reviewd.getHouseID());
			// Review ID
			review.setReviewID(reviewd.getReviewID());
			// Comments
			review.setComments(reviewd.getComments());
			// Review Date
			review.setDate(reviewd.getDate());
			//Host ID
			review.setReviewerID(reviewd.getReviewerID());
			//stars
			review.setStars(reviewd.getStars());

		}
		if (review == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(review).build();
	}
	
	
	// POST a new House
	@POST
	@Consumes({ "application/json" })
	public Response create(final JSONReview review) {
		entities.Review reviewd = new entities.Review();
		// Review ID
		reviewd.setReviewID(review.getReviewID());
		// Comments
		reviewd.setComments(review.getComments());
		// Review Date
		reviewd.setDate(review.getDate());
		// House ID
		reviewd.setHouseID(review.getHouseID());
		//Host ID
		reviewd.setReviewerID(review.getReviewerID());
		//stars
		reviewd.setStars(review.getStars());
		
		
		ReviewDB reviewDao = new ReviewDB();
		long id = reviewDao.insertReview(reviewd);
		return Response.created(
				UriBuilder.fromResource(ReviewEndpoint.class)
						.path(String.valueOf(id)).build()).build();
	}
	
	
	@GET
	@Path("/export")
	public void export() throws JAXBException{
		String userHomeFolder = System.getProperty("user.home");
		File file = new File(userHomeFolder,"reviews.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(ReviewsLists.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		ReviewDB userDao = new ReviewDB();
		List<entities.Review> reviewd = userDao.getReviews();
		ReviewsLists reviews=new ReviewsLists();
		for(entities.Review unique_review :reviewd){
			reviews.getReviews_list().add(unique_review);
		}
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(reviews, file);
		jaxbMarshaller.marshal(reviews, System.out);
		

	}
}