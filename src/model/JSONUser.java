package model;

import java.util.Date;

public class JSONUser {
	
	private long userID;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private boolean host;
	
	private Date birthday;
	
	private boolean verifiedFromAdmin;
	
	private String image_shortcut;
	
	private String final_image;
	
	private byte[] image_URL;
	
	
	
	public long getUserID() {
		return userID;
	}
	
	public void setUserID(long l) {
		this.userID = l;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public byte[] getImage_URL() {
		return image_URL;
	}
	
	public void setImage_URL(byte[] image_URL) {
		this.image_URL = image_URL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getHost() {
		return host;
	}

	public void setHost(boolean host) {
		this.host = host;
	}

	public boolean isVerifiedFromAdmin() {
		return verifiedFromAdmin;
	}

	public void setVerifiedFromAdmin(boolean verifiedFromAdmin) {
		this.verifiedFromAdmin = verifiedFromAdmin;
	}

	public String getImage_shortcut() {
		return image_shortcut;
	}

	public void setImage_shortcut(String image_shortcut) {
		this.image_shortcut = image_shortcut;
	}

	public String getFinal_image() {
		return final_image;
	}

	public void setFinal_image(String final_image) {
		this.final_image = final_image;
	}
	
}
