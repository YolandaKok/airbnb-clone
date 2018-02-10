package rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;


import db.MessageDB;
import model.JSONMessage;


@Path("/messages")
public class MessageEndpoint {
	
	@GET
	@Produces({ "application/json" })
	public List<JSONMessage> listAll() {
		MessageDB messageDao = new MessageDB();
		List<entities.UserHasMessage> messagesd = messageDao.getMessages();
		List<JSONMessage> messages = null;
		if (messagesd != null && messagesd.size()>0)
		{
			messages = new ArrayList<JSONMessage>();
			for (entities.UserHasMessage messaged : messagesd)
			{
				JSONMessage message = new JSONMessage();
				
				// Message ID
				message.setMessageID(messaged.getMessageID());
				// Message
				message.setMessage(messaged.getMessage());
				// ReceiverID
				message.setReceiverID(messaged.getReceiverID());
				// Sender ID
				message.setSenderID(messaged.getSenderID());
				
				messages.add(message);
			}
		}	
		return messages;
	}
	

	// Get a Message by ID
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final long id) throws IOException {
		MessageDB messageDao = new MessageDB();
		entities.UserHasMessage messaged = messageDao.getById(id);
		JSONMessage message = null;
		if (messaged != null) {
			// Creates simple object from model.House to convert it to JSON
			message = new JSONMessage();
			// Message ID
			message.setMessageID(messaged.getMessageID());
			// Message
			message.setMessage(messaged.getMessage());
			// ReceiverID
			message.setReceiverID(messaged.getReceiverID());
			// Sender ID
			message.setSenderID(messaged.getSenderID());

		}
		if (message == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(message).build();
	}
	// POST a new Message
	@POST
	@Consumes({ "application/json" })
	public Response create(final JSONMessage message) {
		entities.UserHasMessage messaged = new entities.UserHasMessage();
		messaged.setMessageID(message.getMessageID());
		// Message
		messaged.setMessage(message.getMessage());
		// ReceiverID
		messaged.setReceiverID(message.getReceiverID());
		// Sender ID
		messaged.setSenderID(message.getSenderID());
		
		MessageDB messageDao = new MessageDB();
		long id = messageDao.insertMessage(messaged);
		return Response.created(
				UriBuilder.fromResource(MessageEndpoint.class)
					.path(String.valueOf(id)).build()).build();
	}
	

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the user matching by the given id 
		MessageDB messageDao = new MessageDB();
		messageDao.delete(id);
		return Response.noContent().build();
	}

	
}