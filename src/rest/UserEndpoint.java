package rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.io.FileUtils;




import db.UserDB;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.JSONUser;
import model.LoginInfo;

@Path("/users")
public class UserEndpoint {

	@POST
	@Consumes({ "application/json" })
	public Response create(final JSONUser user) {
		entities.User userd = new entities.User();
		userd.setFirstName(user.getFirstName());
		userd.setLastName(user.getLastName());
		userd.setUsername(user.getUsername());
		userd.setPassword(user.getPassword());
		userd.setEmail(user.getEmail());
		userd.setBirthday(user.getBirthday());
		userd.setHost(false);
		// set this as false
		userd.setVerifiedFromAdmin(false);
		UserDB userDao = new UserDB();
		long id = userDao.insertUser(userd);
		return Response.created(
				UriBuilder.fromResource(UserEndpoint.class)
						.path(String.valueOf(id)).build()).build();
	}
	
	@POST
	@Path("/login")
	@Consumes({"application/json"})
	@Produces({"text/plain"})
	public Response login(final LoginInfo loginInfo) {
		// Connection with the database data
		// New instance of this class
		UserDB userDao = new UserDB();
		// JPA entities: static object User
		entities.User userd = userDao.find(loginInfo.getUsername(), loginInfo.getPassword());
		if (userd != null) {
			// Create a new token
			String token = issueToken(loginInfo.getUsername());
			return Response.ok(token, "text/plain").build();
		}
		else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	// Creation of the Jason Web Token: Server Side
	
	private String issueToken(String username) {
		// jjwt API
		Key key = utilities.KeyHolder.key;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		long expMillis = nowMillis + 18000000L;
        Date exp = new Date(expMillis);
		String jws = Jwts.builder()
				  .setSubject(username)
				  .setIssuedAt(now)
				  .signWith(SignatureAlgorithm.HS512, key)
				  .setExpiration(exp)
				  .compact();
		return jws;
    }

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final long id) throws IOException {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		JSONUser user = null;
		if (userd != null) {
			// Creates simple object from model.User to convert it to JSON
			user = new JSONUser();
			user.setUserID(userd.getUserID());
			user.setLastName(userd.getLastName());
			user.setFirstName(userd.getFirstName());
			user.setPassword(userd.getPassword());
			user.setUsername(userd.getUsername());
			user.setEmail(userd.getEmail());
			user.setHost(userd.getHost());
			user.setVerifiedFromAdmin(userd.getVerifiedFromAdmin());
			if(userd.getImage_URL()!=null)
			{
				InputStream in = new FileInputStream(new File(userd.getImage_URL()));
		        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		        StringBuilder out = new StringBuilder();
		        String line;
		        while ((line = reader.readLine()) != null) {
		            out.append(line);
		        }
		        user.setFinal_image(out.toString());
		        reader.close();				
			}
			if(userd.getImage_URL()!=null)
				user.setImage_shortcut(userd.getImage_URL());
			user.setBirthday(userd.getBirthday());
		}
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(user).build();
	}
	
	// Return user by password and username
	
	@GET
	@Produces({ "application/json" })
	public List<JSONUser> listAll(byte[] byte_path) throws IOException {
		UserDB userDao = new UserDB();
		List<entities.User> usersd = userDao.getUsers();
		List<JSONUser> users = null;
		if (usersd != null && usersd.size()>0)
		{
			users = new ArrayList<JSONUser>();
			for (entities.User userd : usersd)
			{
				JSONUser user = new JSONUser();
				user.setUserID(userd.getUserID());
				user.setLastName(userd.getLastName());
				user.setFirstName(userd.getFirstName());
				user.setPassword(userd.getPassword());
				user.setUsername(userd.getUsername());
				user.setEmail(userd.getEmail());
				user.setHost(userd.getHost());
				user.setVerifiedFromAdmin(userd.getVerifiedFromAdmin());
				if(userd.getImage_URL()!=null)
				{
					InputStream in = new FileInputStream(new File(userd.getImage_URL()));
			        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			        StringBuilder out = new StringBuilder();
			        String line;
			        while ((line = reader.readLine()) != null) {
			            out.append(line);
			        }
			        user.setFinal_image(out.toString());
			        reader.close();
				}
				if(userd.getImage_URL()!=null)
					user.setImage_shortcut(userd.getImage_URL());
				user.setBirthday(userd.getBirthday());
				users.add(user);
			}
		}
		
		
		return users;
	}

	// Update Method for User Data
	
	@PUT
	@Path("/{id}")
	@Produces({ "application/json" })
	public Response updateUser(@PathParam("id") final long id, final JSONUser user){
		String image_path ="";
		System.out.print(user.getImage_URL());
		if(user.getImage_URL() != null){
		String logFileName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
		//System.out.print(user.getImage_URL());
		logFileName = "loggerFile_" + logFileName; //create a unique file
		image_path=System.getProperty("catalina.base")+"/temp/"+logFileName; //store it in a server path-->/home/dimitra/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/temp
		try {
			FileUtils.writeByteArrayToFile(new File(image_path), (user.getImage_URL()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		entities.User userd = new entities.User();
		//userd.setUserID(user.getUserID());
		userd.setFirstName(user.getFirstName());
		userd.setLastName(user.getLastName());
		userd.setUsername(user.getUsername());
		userd.setPassword(user.getPassword());
		userd.setEmail(user.getEmail());
		userd.setBirthday(user.getBirthday());
		if(user.getImage_URL()!=null)
			userd.setImage_URL(image_path);
		userd.setHost(user.getHost());
		userd.setVerifiedFromAdmin(user.isVerifiedFromAdmin());
		UserDB userDao = new UserDB(); 
		userDao.updateUser(userd, id);
		return Response.ok().build();
	}


	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the user matching by the given id 
		return Response.noContent().build();
	}

}
