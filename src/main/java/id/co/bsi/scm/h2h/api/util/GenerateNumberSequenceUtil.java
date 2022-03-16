package id.co.bsi.scm.h2h.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateNumberSequenceUtil {
	
	public static String refrenceNumber() {
		int length = 10;
	    boolean useLetters = false;
	    boolean useNumbers = true;
	    String randomNumber = RandomStringUtils.random(length, useLetters, useNumbers);
	    
	    DateFormat df = new SimpleDateFormat("yyyyMMdd");
	    String todayAsString = df.format(new Date());
	    
	    String generatedString = AppsUtil.nameAppsDVC
	    							.concat("_")
	    							.concat(todayAsString)
	    							.concat(randomNumber);
		return generatedString;
	}
}
