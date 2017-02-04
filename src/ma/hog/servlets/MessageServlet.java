package ma.hog.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.utils.AppException;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet(urlPatterns = {"/messages/create", "/messages/update"})
public class MessageServlet extends BaseServlet {
	
	private static final long serialVersionUID = -7198298456533367573L;

	@Override
	protected String create(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		// TODO Auto-generated method stub
		return super.create(request, response);
	}
	
	@Override
	protected String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		// TODO Auto-generated method stub
		return super.update(request, response);
	}
}
