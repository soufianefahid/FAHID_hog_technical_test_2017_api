package ma.hog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ma.hog.utils.Tools;

@Entity
public class Conversation {
	
	@Id
	@GeneratedValue
	private int id;
	private String subject;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Participation> participations;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Message> messages;
	
	public Conversation() {
		subject = "";
		createdAt = new Date();
		participations = new ArrayList<Participation>();
		messages = new ArrayList<Message>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public String toJSON() {
		String rs = "{";
		
		rs += "\"id\":"+id+",";
		rs += "\"subject\":\""+subject+"\",";
		rs += "\"createdAt\":"+createdAt.getTime()+",";
		
		rs += "\"messages\":[";
		for( Message message : messages ) {
			rs += message.toJSON() + ",";
		}
		if( messages.size() > 0 ) {
			rs = rs.substring(0, rs.length()-1);
		}
		rs += "],";
		
		rs += "\"participations\":[";
		for( Participation participation : participations ) {
			rs += participation.toJSON() + ",";
		}
		if( participations.size() > 0 ) {
			rs = rs.substring(0, rs.length()-1);
		}
		rs += "],";
		
		rs = rs.substring(0, rs.length()-1);
		
		rs += "}";
		return rs;
	}
	
	public String toMiniJSON() {
		String rs = "{";
		
		rs += "\"id\":"+id+",";
		rs += "\"subject\":\""+subject+"\",";
		rs += "\"createdAt\":"+createdAt.getTime()+",";
		
		rs = rs.substring(0, rs.length()-1);
		rs += "}";
		
		return rs;
	}
	
	public boolean isValid() {
		return (!Tools.stringIsEmpty(subject) && participations != null && participations.size() > 0);
	}
	
	@Override
	public boolean equals(Object obj) {
		return id == ( (Conversation) obj ).id;
	}
}
