package ma.hog.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.dao.MainDAO;
import ma.hog.entities.Conversation;
import ma.hog.entities.Participation;
import ma.hog.entities.User;
import ma.hog.utils.AppException;

/**
 * Servlet implementation class ConversationsServlet
 */
@WebServlet(urlPatterns = {"/conversations/create", "/conversations/show"})
public class ConversationsServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1148026059842151181L;

	@Override
	protected String create(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		
		MainDAO dao = MainDAO.getInstance();
		String subject = (String) request.getAttribute("subject");
		User user = null;
    	
    	try {
    		int userID = Integer.parseInt( request.getParameter("user") );
    		user = dao.findUser(userID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in user id");
    	}
    	
    	if( user == null ) {
    		throw new AppException(400, "User not found", "User not found");
    	}
    	
    	Conversation conversation = new Conversation();
    	conversation.setSubject(subject);
    	
    	Participation participation = new Participation();
    	participation.setConversation(conversation);
    	participation.setOwner(true);
    	participation.setUser(user);
    	
    	conversation.getParticipations().add(participation);
    	
    	dao.insertConversation(conversation);
    	
    	return conversation.toJSON();
	}
	
	@Override
	protected String show(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		MainDAO dao = MainDAO.getInstance();
		Conversation conversation = null;
		
		try {
    		int id = Integer.parseInt( request.getParameter("id") );
    		conversation = dao.findConversation(id);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in conversation id");
    	}
		
		if( conversation == null ) {
    		throw new AppException(400, "conversation not found", "conversation not found");
    	}
		
		return conversation.toJSON();
	}

}
