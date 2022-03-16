package id.co.bsi.scm.h2h.api.util;

/**
 * Author   : kw
 * Date     : 3/1/22 03:03 PM
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String objectToString(ObjectMapper mapper, Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(dateString);
        } catch(Exception e) {
            return new Date();
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.format(date);
        } catch (Exception e) {
            return date.toString();
        }
    }
    
    public static long getTimeNowMilisecon() {
       return new Date().getTime();
    }
    
    
    
    
}
