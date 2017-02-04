package ma.hog.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Tools {
	
	public static final SecureRandom random = new SecureRandom();
	
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
}
