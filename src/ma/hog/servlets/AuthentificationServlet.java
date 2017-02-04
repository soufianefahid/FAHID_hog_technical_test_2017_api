package ma.hog.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.dao.MainDAO;
import ma.hog.entities.User;
import ma.hog.utils.AppException;

/**
 * Servlet implementation class AuthentificationServlet
 */
@WebServlet("/authentification")
public class AuthentificationServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter( "login" );
		String password = request.getParameter( "password" );
		
		if( login == null || password == null || login.length() == 0 || password.length() == 0 ) {
			AppException exception = new AppException(400, "Authentification error", "Missing parameters");
			throw exception;
		}
		
		MainDAO dao = MainDAO.getInstance();
		User user = dao.findUserByLogin(login);
		
		if( user == null || !user.getPassword().equals(password)) {
			AppException exception = new AppException(401, "Authentification error", "Error in User / Password");
			throw exception;
		}
		
		return user.toJSON(true);
	}
}
