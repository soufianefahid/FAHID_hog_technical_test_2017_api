package ma.hog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ma.hog.utils.Tools;

@Entity
public class Message {
	
	@Id
	@GeneratedValue
	private int id;
	private String content;
	@ManyToOne
	private User author;
	@ManyToOne
	private Conversation conversation;
	@ManyToMany
	private List<User> likedBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	public Message() {
		content = "";
		author = null;
		likedBy = new ArrayList<User>();
		createdAt = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<User> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(List<User> likedBy) {
		this.likedBy = likedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public String toJSON() {
		return toJSON(false, false);
	}
	
	public String toJSON(boolean inlcudeAuthor) {
		return toJSON(inlcudeAuthor, false);
	}
	
	public String toJSON(boolean inlcudeAuthor, boolean includeLikers) {
		String rs = "{";
		
		rs += "\"id\":"+id+",";
		rs += "\"content\":\""+content+"\",";
		rs += "\"createdAt\":"+createdAt.getTime()+",";
		rs += "\"conversation\":"+conversation.toMiniJSON()+",";
		
		if( inlcudeAuthor ) {
			rs += "\"author\":"+author.toJSON()+",";
		} else {
			rs += "\"author\":"+author.toMiniJSON()+",";
		}
		
		rs += "\"likedBy\":[";
		if( includeLikers ) {
			for( User user : likedBy ) {
				rs += user.toJSON() + ",";
			}
		} else {
			for( User user : likedBy ) {
				rs += user.toMiniJSON() + ",";
			}
		}
		if( likedBy.size() > 0 ) {
			rs = rs.substring(0, rs.length()-1);
		}
		rs += "],";
		
		rs = rs.substring(0, rs.length()-1);
		
		rs += "}";
		return rs;
	}
	
	public boolean isValid() {
		return (!Tools.stringIsEmpty(content) && author != null);
	}
	
	@Override
	public boolean equals(Object obj) {
		return id == ( (Message) obj ).id;
	}
}
