package ma.hog.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.dao.MainDAO;
import ma.hog.entities.Conversation;
import ma.hog.entities.Participation;
import ma.hog.entities.User;
import ma.hog.utils.AppException;

/**
 * Servlet implementation class ParticipationsServlet
 */
@WebServlet(urlPatterns = {"/participations/create", "/participations/update", "/participations/delete"})
public class ParticipationsServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1653866756968987500L;

	@Override
	protected String create(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		
		MainDAO dao = MainDAO.getInstance();
		User user = null;
		Conversation conversation = null;
    	
    	try {
    		int userID = Integer.parseInt( request.getParameter("user") );
    		user = dao.findUser(userID);
    		int conversationID = Integer.parseInt( request.getParameter("conversation") );
    		conversation = dao.findConversation(conversationID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in user id / conversation id");
    	}
    	
    	if( user == null ) {
    		throw new AppException(400, "user not found", "user not found");
    	}
    	if( conversation == null ) {
    		throw new AppException(400, "conversation not found", "conversation not found");
    	}
    	
    	Participation participation = new Participation();
    	participation.setConversation(conversation);
    	participation.setUser(user);
    	
    	dao.insertParticipation(participation);
    	
    	return participation.toJSON();
	}
	
	@Override
	protected String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		MainDAO dao = MainDAO.getInstance();
		Participation participation = null;
		
		try {
    		int participationID = Integer.parseInt( request.getParameter("id") );
    		participation = dao.findParticipation(participationID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in participation id");
    	}
		
		if( participation == null ) {
    		throw new AppException(400, "participation not found", "participation not found");
    	}
		
		participation.setLastSeen(new Date());
		
		dao.updateParticipation(participation);
		
		return participation.toJSON();
	}
	
	@Override
	protected String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		MainDAO dao = MainDAO.getInstance();
		Participation participation = null;
		
		try {
    		int participationID = Integer.parseInt( request.getParameter("id") );
    		participation = dao.findParticipation(participationID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in participation id");
    	}
		
		if( participation == null ) {
    		throw new AppException(400, "participation not found", "participation not found");
    	}
		
		dao.removeParticipation(participation);
		
		return "{\"deleted\": true}";
	}
	
}
