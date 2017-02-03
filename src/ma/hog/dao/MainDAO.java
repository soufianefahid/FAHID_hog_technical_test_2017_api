package ma.hog.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
	
	public void removeUser(User obj) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(obj);
		tx.commit();
	}
}
