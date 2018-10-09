package com.eposi.fms.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	private static String VEHICE_PATTERN = "^[a-zA-Z0-9]{4,25}$";
	private static String DEVICE_PATTERN = "^[a-zA-Z0-9]{5,15}$";
	private static String DRIVER_PATTERN = "^[a-zA-Z0-9]{5,15}$";
	private static String PHONE_PATTERN = "^[0-9_-_(_)_+_.]{9,20}$";
	private static String CODE_PATTERN  = "^[a-zA-Z0-9]{9,15}$";
	private static Pattern pattern;
	private static Matcher matcher;

	public static boolean validateVehice(String vehicle) {
		pattern = Pattern.compile(VEHICE_PATTERN);
		matcher = pattern.matcher(vehicle);
		return matcher.matches();
	}

	public static boolean validateDevice(String vehicle) {
		pattern = Pattern.compile(DEVICE_PATTERN);
		matcher = pattern.matcher(vehicle);
		return matcher.matches();
	}

	public static boolean validateDriverKey(String key) {
		pattern = Pattern.compile(DRIVER_PATTERN);
		matcher = pattern.matcher(key);
		return matcher.matches();
	}

	public static boolean validatePhone(String phone) {
		pattern = Pattern.compile(PHONE_PATTERN);
		matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	public static boolean validateCode(String code) {
		pattern = Pattern.compile(CODE_PATTERN);
		matcher = pattern.matcher(code);
		return matcher.matches();
	}

	public static void main(String[] args) throws Exception {
		String ss = "0241.0904090323";
		System.out.println(validatePhone(ss));
	}
}
