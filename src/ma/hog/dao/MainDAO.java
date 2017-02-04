package ma.hog.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ma.hog.entities.Conversation;
import ma.hog.entities.Message;
import ma.hog.entities.Participation;
import ma.hog.entities.User;

public class MainDAO {
	private static MainDAO dao = null;
	private EntityManagerFactory ef = null;
	private EntityManager em = null;
	
	public static MainDAO getInstance() {
		if( dao == null ) {
			dao = new MainDAO();
		}
		return dao;
	}
	
	private MainDAO() {
		ef = Persistence.createEntityManagerFactory("GESTION_HOTEL_DATA");
		em = ef.createEntityManager();
	}
	
	public void begin(){
		//em.getTransaction().begin();
	}
	
	public void commit() {
		//em.getTransaction().commit();
	}
	
	// User Area
	public void insertUser(User obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
	}
	
	public void updateUser(User obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(obj);
		tx.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Query query = em.createQuery("SELECT e FROM User e");
		List<User> rs = (List<User>) query.getResultList();
		return rs;
	}
	
	public User findUser(int id) {
		return em.find(User.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public User findUserByLogin(String login) {
		Query query = em.createQuery("SELECT e FROM User e WHERE e.login = :login");
		query.setParameter("login", login);
		List<User> rs = (List<User>) query.getResultList();
		return rs.size() > 0 ? rs.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public User findUserByAccessToken(String accessToken) {
		Query query = em.createQuery("SELECT e FROM User e WHERE e.accessToken = :accessToken");
		query.setParameter("accessToken", accessToken);
		List<User> rs = (List<User>) query.getResultList();
		return rs.size() > 0 ? rs.get(0) : null;
	}
	
	public void removeUser(User obj) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(obj);
		tx.commit();
	}
	
	// Participation Area
	public void insertParticipation(Participation obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
	}
	
	public void updateParticipation(Participation obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(obj);
		tx.commit();
	}
	
	public void removeParticipation(Participation obj) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(obj);
		tx.commit();
	}
	
	public Participation findParticipation(int id) {
		return em.find(Participation.class, id);
	}
	
	// Message Area
	public void insertMessage(Message obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
	}
	
	public void updateMessage(Message obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(obj);
		tx.commit();
	}
	
	public void removeMessage(Message obj) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(obj);
		tx.commit();
	}
	
	public Message findMessage(int id) {
		return em.find(Message.class, id);
	}
	
	// Conversation Area
	public void insertConversation(Conversation obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(obj);
		tx.commit();
	}
	
	public void updateConversation(Conversation obj) {		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(obj);
		tx.commit();
	}
	
	public Conversation findConversation(int id) {
		return em.find(Conversation.class, id);
	}
	
	public void removeConversation(Conversation obj) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(obj);
		tx.commit();
	}
}
