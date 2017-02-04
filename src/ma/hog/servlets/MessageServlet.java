package ma.hog.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.hog.dao.MainDAO;
import ma.hog.entities.Conversation;
import ma.hog.entities.Message;
import ma.hog.entities.Participation;
import ma.hog.entities.User;
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
		MainDAO dao = MainDAO.getInstance();
		String content = (String) request.getParameter("content");
		User user = (User) request.getAttribute("user");
		Conversation conversation = null;
    	
    	try {
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
    	
    	boolean canCreate = false;
    	for( Participation participation : conversation.getParticipations() ) {
    		if( participation.getUser().equals( user ) ) {
    			canCreate = true;
    			break;
    		}
    	}
    	
    	if( !canCreate ) {
    		throw new AppException(403, "authorization error", "authentified user can't create message in this conversation");
    	}
    	
    	Message message = new Message();
    	message.setAuthor(user);
    	message.setConversation(conversation);
    	message.setContent(content);
    	
    	if( !message.isValid() ) {
    		throw new AppException(400, "invalid inputs", "message not valid");
    	}
    	
    	conversation.getMessages().add(message);
    	dao.insertMessage(message);
    	dao.updateConversation(conversation);
    	
    	return message.toJSON();
	}
	
	@Override
	protected String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		MainDAO dao = MainDAO.getInstance();
		Message message = null;
		User user = (User) request.getAttribute("user");
		
		try {
    		int messageID = Integer.parseInt( request.getParameter("id") );
    		message = dao.findMessage(messageID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in message id");
    	}
		
		if( message == null ) {
    		throw new AppException(400, "message not found", "message not found");
    	}
		
		boolean canLike = false;
		boolean alreadyLiked = false;
		
		for( Participation participation : message.getConversation().getParticipations() ) {
			if( participation.getUser().getId() == user.getId() ) {
				canLike = true;
				break;
			}
		}
		
		for( User participant : message.getLikedBy() ) {
			if( participant.equals(user) ) {
				alreadyLiked = true;
				break;
			}
		}
		
		if( !canLike ) {
			throw new AppException(403, "authorization error", "authentified user can't like this message");
		}
		
		if( !alreadyLiked ) {
			message.getLikedBy().add(user);
		} else {
			message.getLikedBy().remove(user);
		}
		
		
		dao.updateMessage(message);
		
		return message.toJSON();
	}
}
