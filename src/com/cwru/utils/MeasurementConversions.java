package com.cwru.utils;

/**
 * Class to convert different units of measurement.
 * @author scrilley
 *
 */
public class MeasurementConversions {

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
	
}
