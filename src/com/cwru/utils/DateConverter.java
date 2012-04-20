package com.cwru.utils;

import java.util.GregorianCalendar;

/**
 * 
 * @author lkissling
 *
 */
public class DateConverter {
	
	/**
	 * Constructor.
	 */
	public DateConverter(){};
	
	/**
	 * Converts string date of format yyyy/mm/dd to int of format yyyymmdd
	 * 
	 * @param str
	 * @return int
	 * @throws NumberFormatException
	 */
	public int stringDateToInt(String str) throws NumberFormatException {
		int result = -1;
		try {
			result =  Integer.parseInt(str.replaceAll("\\D", ""));
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Improperly formatted date could not be converted to int: " + str);
		}
		
		return result;
	}
	
	/**
	 * Converts int dtate of format yyyymmdd to string of format yyyy/mm/dd
	 * 
	 * @param i
	 * @return string
	 * @throws NumberFormatException
	 */
	public String intDateToString(int i) throws NumberFormatException {
		String iString = Integer.toString(i);
		String result = null;
		if (iString.length() == 8) {
			result = iString.substring(0, 4) + "/" + iString.substring(4, 6) + "/" + iString.substring(6);
		} else {
			throw new NumberFormatException("Improperly formatted int date could not be converted to string date:" + i);
		}
		return result;
	}
	
//	public GregorianCalendar stringDateToCalendar(String str) {
//		
//	}
//	
//	public GregorianCalendar intDateToCalendar(int i) {
//		
//	}
}