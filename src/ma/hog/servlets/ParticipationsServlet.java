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
		User user = (User) request.getAttribute("user");
		Conversation conversation = null;
		User newParticipant = null;
    	
    	try {
    		int conversationID = Integer.parseInt( request.getParameter("conversation") );
    		conversation = dao.findConversation(conversationID);
    		int participantID = Integer.parseInt( request.getParameter("participant") );
    		newParticipant = dao.findUser(participantID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in user id / conversation id");
    	}
    	
    	if( user == null ) {
    		throw new AppException(400, "user not found", "user not found");
    	}
    	if( conversation == null ) {
    		throw new AppException(400, "conversation not found", "conversation not found");
    	}
    	if( newParticipant == null ) {
    		throw new AppException(400, "participant not found", "participant not found");
    	}
    	
    	boolean canCreate = false;
    	boolean alreadyIn = false;
    	
    	for( Participation participation : conversation.getParticipations() ) {
    		if( participation.getUser().getId() == user.getId() && participation.isOwner() ) {
    			canCreate = true;
    		}
    		if( participation.getUser().getId() == newParticipant.getId() ) {
    			alreadyIn = true;
    		}
    		
    		if( canCreate && alreadyIn ) break;
    	}

    	if( !canCreate ) {
    		throw new AppException(403, "authorzation error", "authentificated user can't add users to conversation");
    	}
    	if( alreadyIn ) {
    		throw new AppException(401, "user already in the conversation", "user already in the conversation");
    	}
    	
    	Participation participation = new Participation();
    	participation.setConversation(conversation);
    	participation.setUser(newParticipant);
    	
    	conversation.getParticipations().add(participation);
    	newParticipant.getParticipations().add(participation);
    	
    	dao.insertParticipation(participation);
    	dao.updateConversation(conversation);
    	dao.updateUser(newParticipant);
    	
    	return participation.toJSON();
	}
	
	@Override
	protected String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		MainDAO dao = MainDAO.getInstance();
		Participation participation = null;
		User user = (User) request.getAttribute("user");
		
		try {
    		int participationID = Integer.parseInt( request.getParameter("id") );
    		participation = dao.findParticipation(participationID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in participation / user id");
    	}
		
		if( participation == null ) {
    		throw new AppException(400, "participation not found", "participation not found");
    	}
		
		if( user == null ) {
    		throw new AppException(400, "user not found", "user not found");
    	}
		
		if( participation.getUser().getId() != user.getId() ) {
			throw new AppException(403, "authorization error", "user can't update participation : not owner");
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
		User user = (User) request.getAttribute("user");
		
		try {
    		int participationID = Integer.parseInt( request.getParameter("id") );
    		participation = dao.findParticipation(participationID);
    	} catch(Exception e) {
    		throw new AppException(400, "Invalid input", "Error in participation / user id");
    	}
		
		if( participation == null ) {
    		throw new AppException(400, "participation not found", "participation not found");
    	}
		
		if( user == null ) {
    		throw new AppException(400, "user not found", "user not found");
    	}
		
		User owner = null;
		for( Participation p : participation.getConversation().getParticipations() ) {
			if( p.isOwner() ) {
				owner = p.getUser();
				break;
			}
		}
		
		if( participation.getUser().getId() != user.getId() && ( owner != null && owner.getId() != user.getId() ) ) {
			throw new AppException(403, "authorization error", "authentified user can't remove this participation");
		}
		
		Conversation conversation = participation.getConversation();
		conversation.getParticipations().remove(participation);
		User participant = participation.getUser();
		participant.getParticipations().remove(participation);
		
		dao.updateConversation(conversation);
		dao.updateUser(participant);
		
		dao.removeParticipation(participation);
		
		return "{\"deleted\": true}";
	}
	
}
