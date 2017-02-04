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
		String subject = (String) request.getParameter("subject");
		User user = (User) request.getAttribute("user");
    	
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
    	user.getParticipations().add(participation);
    	
    	if( !conversation.isValid() ) {
    		throw new AppException(400, "invalid inputs", "conversation not valid");
    	}
    	
    	dao.insertConversation(conversation);
    	dao.updateUser(user);
    	
    	return conversation.toJSON();
	}
	
	@Override
	protected String show(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		MainDAO dao = MainDAO.getInstance();
		Conversation conversation = null;
		User user = (User) request.getAttribute("user");
		
		try {
    		int id = Integer.parseInt( request.getParameter("id") );
    		conversation = dao.findConversation(id);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in conversation id");
    	}
		
		if( conversation == null || user == null ) {
    		throw new AppException(400, "conversation / user not found", "conversation / user not found");
    	}
		
		boolean canShow = false;
		
		for( Participation participation : conversation.getParticipations() ) {
			if( participation.getUser().getId() == user.getId() ) {
				canShow = true;
				break;
			}
		}
		
		if( !canShow ) {
			throw new AppException(403, "authorization error", "authentificated user can't show the conversation");
		}
		
		return conversation.toJSON();
	}

}
