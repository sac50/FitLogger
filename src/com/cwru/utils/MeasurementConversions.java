package com.cwru.utils;

/**
 * Class to convert different units of measurement.
 * @author scrilley, lkissling
 *
 */
public class MeasurementConversions {
	
	/**
	 * converts a number from one unit to another, based on string values
	 * of units represented in FitLogger's database
	 * 
	 * @param num
	 * @param from
	 * @param to
	 * @return double
	 */
	public static double convert(double num, String from, String to) throws IllegalArgumentException {
		if ("mile".equals(from) && "Km.".equals(to))
			return milesToKm(num);
		else if ("mile".equals(from) && "m.".equals(to))
			return milesToM(num);
		else if ("mile".equals(from) && "yd.".equals(to))
			return milesToYd(num);
		else if ("Km.".equals(from) && "mile".equals(to))
			return kmToMile(num);
		else if ("Km.".equals(from) && "m.".equals(to))
			return kmToM(num);
		else if ("Km.".equals(from) && "yd.".equals(to))
			return kmToYd(num);
		else if ("m.".equals(from) && "mile".equals(to))
			return mToMile(num);
		else if ("m.".equals(from) && "Km.".equals(to))
			return mToKm(num);
		else if ("m.".equals(from) && "yd.".equals(to))
			return mToYd(num);
		else if ("yd.".equals(from) && "mile".equals(to))
			return ydToMile(num);
		else if ("yd.".equals(from) && "Km.".equals(to))
			return ydToKm(num);
		else if ("yd.".equals(from) && "m.".equals(to))
			return ydToM(num);
		else if ("seconds".equals(from) && "minutes".equals(to))
			return secToMin(num);
		else if ("seconds".equals(from) && "hours".equals(to))
			return secToHour(num);
		else if ("minutes".equals(from) && "seconds".equals(to))
			return minToSec(num);
		else if ("minutes".equals(from) && "hours".equals(to))
			return minToHour(num);
		else if ("hours".equals(from) && "seconds".equals(to))
			return hourToSec(num);
		else if ("hours".equals(from) && "minutes".equals(to))
			return hourToMin(num);
		else if (from.equals(to))
			return num;
		else
			throw new IllegalArgumentException("Unrecognized and/or incompatible units: " + 
					from + " " + to);
	}

	/**
	 * Converts miles to kilometers
	 * @param miles
	 * @return
	 */
	public static double milesToKm(double miles) {
		return miles*1.609344;
	}
	
	/**
	 * Converts miles to meters
	 * @param miles
	 * @return
	 */
	public static double milesToM(double miles) {
		return miles*1609.344;
	}
	
	/**
	 * Converts miles to yards
	 * @param miles
	 * @return
	 */
	public static double milesToYd(double miles) {
		return miles*1760;
	}
	
	/**
	 * Converts kilometers to miles
	 * @param km
	 * @return
	 */
	public static double kmToMile(double km) {
		return km * 0.621371192;
	}
	
	/**
	 * Converts kilometers to meters
	 * @param km
	 * @return
	 */
	public static double kmToM(double km) {
		return km * 1000;
	}
	
	/**
	 * Converts kilometers to yards
	 * @param km
	 * @return
	 */
	public static double kmToYd(double km) {
		return km * 1093.6133;
	}
	
	/**
	 * Converts meters to miles
	 * @param m
	 * @return
	 */
	public static double mToMile(double m) {
		return m * 0.000621371192;
	}
	
	/**
	 * Converts meters to kilometers
	 * @param m
	 * @return
	 */
	public static double mToKm(double m) {
		return m * 0.001;
	}

	/**
	 * Converts meters to yards
	 * @param m
	 * @return
	 */
	public static double mToYd(double m) {
		return m * 1.0936133;
	}
	
	/**
	 * Converts yards to mile
	 * @param yd
	 * @return
	 */
	public static double ydToMile(double yd) {
		return yd * 0.000568181818;
	}
	
	/**
	 * Converts yards to kilometers
	 * @param yd
	 * @return
	 */
	public static double ydToKm(double yd) {
		return yd * 0.0009144;
	}
	
	/**
	 * Converts yards to meters
	 * @param yd
	 * @return
	 */
	public static double ydToM(double yd) {
		return yd * 0.9144;
	}
	
	/**
	 * Converts seconds to minutes
	 * @param sec
	 * @return
	 */
	public static double secToMin(double sec) {
		return sec / 60;
	}
	
	/**
	 * Converts seconds to hours
	 * @param sec
	 * @return
	 */
	public static double secToHour(double sec) {
		return sec / 3600;
	}
	
	/**
	 * Converts minutes to seconds
	 * @param min
	 * @return
	 */
	public static double minToSec(double min) {
		return min * 60;
	}
	
	/**
	 * Converts minutes to hours
	 * @param min
	 * @return
	 */
	public static double minToHour(double min) {
		return min / 60;
	}
	
	/**
	 * Converts hours to seconds
	 * @param hour
	 * @return
	 */
	public static double hourToSec(double hour) {
		return hour * 3600;
	}
	
	/**
	 * Converts hours to minutes
	 * @param hour
	 * @return
	 */
	public static double hourToMin(double hour) {
		return hour * 60;
	}
	
}
