package ma.hog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	
	public String toJSON() {
		return this.toJSON(false);
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
}
