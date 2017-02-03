package ma.hog.utils;

import ma.hog.dao.MainDAO;
import ma.hog.entities.User;

public class Main {
	public static void main(String[] args) {
		MainDAO dao = MainDAO.getInstance();
		User user = new User();
		user.setLogin("admin");
		user.setPassword("admin");
		
		dao.insertUser(user);
		System.out.println("Finish");
	}
}
