package com.acme.application.shared.common;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtility {
	
	public static ZoneId DEFAULT_ZONE = ZoneId.of("UTC");
	public static String DEFAULT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter
			.ofPattern(DEFAULT_PATTERN)
			.withZone(DEFAULT_ZONE); 

	
	/**
	 * Convenience method to create string that may be directly saved in the database.
	 */
	public static String nowInUtcAsString() {
		return convert(nowInUtc());
	}
	
	public static ZonedDateTime nowInUtc() {
		return ZonedDateTime.now(Clock.systemUTC());
	}

	/**
	 * This function is design to convert ZonedDateTime object from other system to 
	 * a stable ISO dateTime String. 
	 * {@see https://stackoverflow.com/questions/43634226/zoneddatetime-tostring-compatability-with-iso-8601}
	 * @param date zoned date time {@link ZonedDateTime}
	 * @return 'yyyy-MM-ddTHH:mm:ssZ' format ISO dateTime string
	 */
	public static String convert(ZonedDateTime timeInUtc) {
		return timeInUtc == null ? null :  
			ZonedDateTime
			.ofInstant(timeInUtc.toInstant(), DEFAULT_ZONE)
			.format(DEFAULT_FORMATTER);
	}

	public static ZonedDateTime convert(String timeInUtc) {
		return ZonedDateTime.parse(timeInUtc, DEFAULT_FORMATTER);
	}
	
	public static Date convertToDate(String timeInUtc) {
		return Date.from(convert(timeInUtc).toInstant());
	}
}
