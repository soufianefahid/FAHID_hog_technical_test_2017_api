package ma.hog.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class Tools {
	
	public static final SecureRandom random = new SecureRandom();
	public static final String[] allowedPaths = {"/authentification", "/users/create"};
	
	public static boolean stringIsEmpty(String obj) {
		return (obj == null || obj.length() == 0);
	}
	
	public static boolean emailIsValid(String obj) {
		if( stringIsEmpty(obj) ) return false;
		return obj.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}
	
	public static String generateAccessToken() {
		return new BigInteger(130, random).toString(32);
	}
	
	public static void renderError(ServletException exception, HttpServletResponse response) throws IOException {
		
		response.setContentType("application/json");
		int code = -1;
		String reason = null;
		String message = null;
		
		if( exception instanceof AppException ) {	
			AppException appException = (AppException) exception;
			code = appException.getCode();
			reason = appException.getReason();
			message = appException.getMessage();
		} else {
			code = 500;
			reason = "Unkown server error";
			message = exception.getMessage();
		}
		
		String error = "{";
		error += "\"code\":"+code+",";
		error += "\"error\":\""+reason+"\",";
		error += "\"message\":\""+message+"\"";
		error += "}";
		
		response.setStatus( code );
		
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.addHeader("Access-Control-Max-Age", "86400");
		
		response.getWriter().write(error);
	}
	
	public static boolean pathIsPublic(String path) {
		boolean isPublic = false;
		for( String elmt : allowedPaths ) {
			if( elmt.equals( path ) ) {
				isPublic = true;
				break;
			}
		}
		return isPublic;
	}
}
