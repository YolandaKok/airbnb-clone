package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userID;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String email;

	@Column(name="first_name")
	private String firstName;

	private boolean host;

	private String image_URL;

	@Column(name="last_name")
	private String lastName;

	private String password;
	
	private String username;

	@Column(name="verified_from_admin")
	private boolean verifiedFromAdmin;

	public User() {
	}

	public long getUserID() {
		return this.userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean getHost() {
		return this.host;
	}

	public void setHost(boolean host) {
		this.host = host;
	}

	public String getImage_URL() {
		return this.image_URL;
	}

	public void setImage_URL(String image_URL) {
		this.image_URL = image_URL;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getVerifiedFromAdmin() {
		return this.verifiedFromAdmin;
	}

	public void setVerifiedFromAdmin(boolean verifiedFromAdmin) {
		this.verifiedFromAdmin = verifiedFromAdmin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}