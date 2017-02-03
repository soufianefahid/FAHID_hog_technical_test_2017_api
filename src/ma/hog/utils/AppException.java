package ma.hog.utils;

import javax.servlet.ServletException;

public class AppException extends ServletException {
	
	private static final long serialVersionUID = 9119837777865813879L;
	
	private String reason;
	private String message;
	private int code;
	
	public AppException() {
		// TODO Auto-generated constructor stub
		reason = "Unkonwn reason";
		message = "";
		code = 500;
	}
	
	public AppException(String reason, String message) {
		this.reason = reason;
		this.message = message;
		code = 500;
	}
	
	public AppException(int code, String reason, String message) {
		this.reason = reason;
		this.message = message;
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
