package ma.hog.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ma.hog.utils.Tools;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private int id;
	private String login;
	private String password;
	private String email;
	private String nom;
	private String prenom;
	private String accessToken;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Participation> participations;
	
	public User() {
		id = -1;
		login = "";
		password = "";
		email = "";
		nom = "";
		prenom = "";
		accessToken = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		if( login != null ) this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if( password != null ) this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if( email != null ) this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		if( nom != null ) this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		if( prenom != null ) this.prenom = prenom;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

	public String toJSON() {
		return this.toJSON(false);
	}
	
	public String getFullName() {
		if( Tools.stringIsEmpty(nom) && Tools.stringIsEmpty(prenom) ) return login;
		
		if( Tools.stringIsEmpty(nom) ) {
			return prenom;
		}
		
		if( Tools.stringIsEmpty(prenom) ) {
			return nom;
		}
		
		return nom + " " + prenom;
	}
	
	public String toJSON(boolean withToken) {
		String rs = "{";
		
		rs += "\"id\":"+id+",";
		rs += "\"login\":\""+login+"\",";
		rs += "\"password\":\""+password+"\",";
		rs += "\"email\":\""+email+"\",";
		rs += "\"nom\":\""+nom+"\",";
		rs += "\"prenom\":\""+prenom+"\",";
		
		if( withToken ) {
			rs += "\"token\":\""+accessToken+"\",";
		}
		
		rs = rs.substring(0, rs.length()-1);
		
		rs += "}";
		return rs;
	}
	
	public String toMiniJSON(boolean withFullName) {
		String rs = "{";
		rs += "\"id\":"+id+",";
		
		if( withFullName ) {
			rs += "\"name\":\""+this.getFullName()+"\",";
		}
		
		rs = rs.substring(0, rs.length()-1);
		rs += "}";
		
		return rs;
	}
	
	public String toMiniJSON() {
		return toMiniJSON(true);
	}
	
	public boolean isValid() {
		return !Tools.stringIsEmpty(login) && !Tools.stringIsEmpty(password) && Tools.emailIsValid(email);
	}
}
