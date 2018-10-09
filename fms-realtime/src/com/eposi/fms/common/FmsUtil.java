package com.eposi.fms.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Random;

/**
 * Created by tuanpa on 6/20/2015.
 */
public class FmsUtil {
    ////////////////////////////////////////////////////////////////
    // Token
    ////////////////////////////////////////////////////////////////
    private static final Random random = new Random();
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ0123456789";

    public static Date getBeginDay(Date date) {
        String sBeginDay = DateFormatUtils.format(date, "yyyy/MM/dd");
        try {
            Date beginDay = parseDate(sBeginDay,"yyyy/MM/dd");
            return beginDay;
        }catch (Exception e){
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseDate(String strDate, String strFormat) throws ParseException {
        return DateUtils.parseDate(strDate, new String[]{strFormat});
    }

    public static String getToken(int length) {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }

    public static int[] getArray(String strArray) {
        if(StringUtils.isNotEmpty(strArray)) {
            strArray = StringUtils.replace(strArray, "[", "");
            strArray = StringUtils.replace(strArray, "]", "");
            String[] sArray = StringUtils.split(strArray, ",");
            int[] result = new int[sArray.length];
            for(int i=0;i<sArray.length;i++){
                result[i] = Integer.parseInt(sArray[i].trim());
            }
            return result;
        }

        return null;
    }

    public static float getShortDouble(double km) {
        long km1000X = (long)(km*1000);
        return (km1000X/1000f);
    }

}
