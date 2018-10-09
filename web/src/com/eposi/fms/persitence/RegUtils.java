package com.eposi.fms.persitence;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TienManh on 4/14/2016.
 */
public class RegUtils {
    private  static String KEY_PATTERN    = "^[a-zA-Z0-9]{7,12}$";
    private  static String VEHICE_PATTERN = "^[a-zA-Z0-9]{4,25}$";
    private  static String DEVICE_PATTERN = "^[a-zA-Z0-9]{5,8}$";
    private  static String VIN_PATTERN = "^[a-zA-Z0-9]{12,20}$";

    private  static Pattern pattern;
    private  static Matcher matcher;

    public static boolean validateDriverKey(String vehicle){
        pattern = Pattern.compile(KEY_PATTERN);
        matcher = pattern.matcher(vehicle);
        return matcher.matches();
    }

    public static boolean validateVehicle(String vehicle){
        pattern = Pattern.compile(VEHICE_PATTERN);
        matcher = pattern.matcher(vehicle);
        StringUtils.isAlphanumeric(vehicle);
        return matcher.matches();

    }

    public static boolean validateDevice(String device){
        pattern = Pattern.compile(DEVICE_PATTERN);
        matcher = pattern.matcher(device);
        return matcher.matches();
    }

    public static boolean validateVIN(String VIN){
        pattern = Pattern.compile(VIN_PATTERN);
        matcher = pattern.matcher(VIN);
        return matcher.matches();
    }
}
