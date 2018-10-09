package com.eposi.fms.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	private static String DRIVER_PATTERN = "^[a-zA-Z0-9]{6,25}$";
	private static Pattern pattern;
	private static Matcher matcher;

	public static boolean validateDriver(String driver) {
		if(driver==null) return false;
		pattern = Pattern.compile(DRIVER_PATTERN);
		matcher = pattern.matcher(driver);
		return matcher.matches();
	}
}
