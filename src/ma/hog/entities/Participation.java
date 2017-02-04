package ma.hog.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Participation {
	
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Conversation conversation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSeen;
	private boolean isOwner;
	
	public Participation() {
		user = null;
		conversation = null;
		lastSeen = new Date();
		isOwner = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Date getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}
	
	public String toJSON() {
		String rs = "{";
		
		rs += "\"id\":"+id+",";
		rs += "\"lastSeen\":"+lastSeen.getTime()+",";
		rs += "\"isOwner\":"+isOwner+",";
		
		rs += "\"user\":"+user.toMiniJSON()+",";
		rs += "\"conversation\":"+conversation.toMiniJSON()+",";
		
		rs = rs.substring(0, rs.length()-1);
		
		rs += "}";
		return rs;
	}
	
	public boolean isValid() {
		return (user != null && conversation != null);
	}
	
	@Override
	public boolean equals(Object obj) {
		return id == ( (Participation) obj ).id;
	}
}
