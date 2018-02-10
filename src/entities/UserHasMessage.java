package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_has_messages database table.
 * 
 */
@Entity
@Table(name="user_has_messages")
@NamedQuery(name="UserHasMessage.findAll", query="SELECT u FROM UserHasMessage u")
public class UserHasMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String message;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long messageID;

	//uni-directional many-to-one association to User
	@JoinColumn(name="receiverID")
	private long receiverID;

	//uni-directional many-to-one association to User
	@JoinColumn(name="senderID")
	private long senderID;

	public UserHasMessage() {
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getMessageID() {
		return this.messageID;
	}

	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	public long getReceiverID() {
		return this.receiverID;
	}

	public void setReceiverID(long receiverID) {
		this.receiverID = receiverID;
	}

	public long getSenderID() {
		return this.senderID;
	}

	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}

}