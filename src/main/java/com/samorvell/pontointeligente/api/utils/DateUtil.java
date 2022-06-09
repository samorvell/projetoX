package com.samorvell.pontointeligente.api.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samorvell.pontointeligente.api.RuntimeException.ApplicationRuntimeException;



/**
 * Class created to handle date operations
 * 
 * @author bruno
 *
 */
public class DateUtil {

	/**
	 * Logging instance
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * Milisseconds quantity contained in a day
	 */
	private static final int DAY_MILISECONDS = 60 * 60 * 24 * 1000;

	/**
	 * Property that defines a day month yearh format
	 */
	public static final String DATE_FMT_BR = "dd/MM/yyyy";
	
	/**
	 * Property that defines a day month yearh format
	 */
	public static final String DATE_HOUR_FMT_BR = "dd/MM/yyyy HH:mm";

	/**
	 * Property that defines a day month yearh format
	 */
	public static final String DATE_FMT_BR_ONLY_NUMBER = "ddMMyyyy";

	/**
	 * Property that defines a yearh month day format
	 */
	public static final String DATE_FMT_USA = "yyyy-MM-dd";

	/**
	 * Property that defines a united states date format
	 */
	public static final String DATEHOUR_FMT_USA = "yyyy-MM-dd'T'HH:mm:ss";
	
	/** The Constant FMT_BR_YEAR_MONTH. */
	public static final String FMT_BR_YEAR_MONTH = "MM/yyyy";
	
	/** The Constant FMT_BR_YEAR_MONTH. */
	public static final String FMT_USA = "yyyyMMdd";
	
	/**
	 * Checks if is date before.
	 *
	 * @param startDate the start date
	 * @param finalDate the final date
	 * @return true, if is date before
	 */
	public static boolean isDateBefore(Date startDate, Date finalDate) {
		return (toLocalDate(startDate).isBefore(toLocalDate(finalDate)));
	}
	
	
	/**
	 * Days between.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the long
	 */
	public static Long totalDayBetween(Date startDate, Date endDate) {
		Long result = null;
		try {
			result = ChronoUnit.DAYS.between(toLocalDate(startDate), toLocalDate(endDate));
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	
	/**
	 * At start of the day.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static Date atStartOfTheDay(String date) {
		
		Date startDate = null;
		try {
			if(StringUtils.isNotBlank(date)) {
				LocalDateTime dateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FMT_BR)).atStartOfDay();
				startDate = DateUtil.toDate(dateTime);
			}
		} catch (Exception e) {
			throw new ApplicationRuntimeException("An error occurred while convert date to start of the day.", e);
		}
		return startDate;
	}
	
	/**
	 * At end of the day.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static Date atEndOfTheDay(String date) {
		
		Date finalDate = null;
		try {
			if (StringUtils.isNotBlank(date)) {
				LocalDateTime dateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atTime(LocalTime.MAX).truncatedTo(ChronoUnit.SECONDS);
				finalDate = DateUtil.toDate(dateTime);
			}
		} catch (Exception e) {
			throw new ApplicationRuntimeException("An error occurred while convert date to end of the day.", e);
		}
		return finalDate;
	}

	/**
	 * Method created to return the number of days between two dates
	 * 
	 * @param dtStart
	 * @param dtEnd
	 * @return iint
	 */
	public static int numberOfDays(Date dtStart, Date dtEnd) {

		if (dtStart == null) {
			throw new ApplicationRuntimeException("Parameter [dtStart] must not be null");
		}

		if (dtEnd == null) {
			throw new ApplicationRuntimeException("Parameter [dtEnd] must not be null");
		}

		// Remove the hour from tha dates
		Date dtStartTrunc = DateUtils.truncate(dtStart, Calendar.DAY_OF_MONTH);
		Date dtEndTrunc = DateUtils.truncate(dtEnd, Calendar.DAY_OF_MONTH);

		return (int) ((dtEndTrunc.getTime() - dtStartTrunc.getTime()) / DAY_MILISECONDS);
	}
	
	/**
	 * Method created to make a copy from Date
	 * 
	 * @param value
	 * @return Date
	 */
	public static Date copyDate(Date value) {
		if (value == null) {
			return null;
		}

		// Create a new instance date
		return new Date(value.getTime());
	}

	/**
	 * Check if contains date between 2 dates
	 * 
	 * @param startDate
	 * @param finalDate
	 * @param checkDate
	 * @return boolean
	 */
	public static boolean isDateInBetween(Date startDate, Date finalDate, LocalDate checkDate) {

		return !(checkDate.isBefore(toLocalDate(startDate))
				|| checkDate.isAfter(toLocalDate(finalDate)));
	}

	/**
	 * Check if same week
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return boolean
	 */
	public static boolean isSameWeek(LocalDate firstDate, Integer weekDay) {

		return firstDate.getDayOfWeek().getValue() == weekDay;
	}

	/**
	 * Check if same month and day
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return boolean
	 */
	public static boolean isSameMonthAndDay(LocalDate firstDate, LocalDate secondDate) {

		return firstDate.getDayOfMonth() == secondDate.getDayOfMonth()
				&& firstDate.getMonthValue() == secondDate.getMonthValue();
	}
	
	/**
	 * Check if same day
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return boolean
	 */
	public static boolean isSameDay(LocalDate firstDate, LocalDate secondDate) {

		return firstDate.getDayOfMonth() == secondDate.getDayOfMonth();
	}
	
	/**
	 * Check if same date
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return boolean
	 */
	public static boolean isSameDate(LocalDate firstDate, Date secondDate) {

		return firstDate.equals(toLocalDate(secondDate));
	}
	
	/**
	 * Check if same date
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return boolean
	 */
	public static boolean isSameDate(Date firstDate, Date secondDate) {

		return toLocalDate(firstDate).equals(toLocalDate(secondDate));
	}

	/**
	 * Check if same date
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return boolean
	 */
	public static boolean isSameDate(LocalDate firstDate, LocalDate secondDate) {

		return firstDate.equals(secondDate);
	}

	/**
	 * Method created to verify if a date ir bigger or equals the beginning date
	 * 
	 * @param date
	 * @param startDate
	 * @return boolean
	 */
	public static boolean afterOrEqual(LocalDate finalDate, Date startDate) {

		return finalDate.isAfter(toLocalDate(startDate)) || isSameDay(toLocalDate(startDate), finalDate);
	}
	
	/**
	 * Method created to verify if a date ir bigger or equals the beginning date
	 * 
	 * @param date
	 * @param startDate
	 * @return boolean
	 */
	public static boolean afterOrEqual(LocalDate finalDate, LocalDate startDate) {

		return finalDate.isAfter(startDate) || isSameDay(startDate, finalDate);
	}

	/**
	 * Method created to verify if a date ir bigger or equals the beginning date
	 * 
	 * @param date
	 * @param startDate
	 * @return boolean
	 */
	public static boolean afterOrEqual(Date finalDate, Date startDate) {

		return finalDate.after(startDate) || DateUtils.isSameDay(finalDate, startDate);
	}

	/**
	 * Method created to verify if a date ir lower or equals the final date
	 * 
	 * @param date
	 * @param endDate
	 * @return boolean
	 */
	public static boolean beforeOrEqual(Date date, Date endDate) {

		return date.before(endDate) || DateUtils.isSameDay(date, endDate);
	}

	/**
	 * Method created to subtract days from a date
	 * 
	 * @param calendar
	 * @param daysToSubtract
	 * @return Date
	 */
	static public Date subtractDays(Date calendar, int daysToSubtract) {
		LocalDate date = DateUtil.toLocalDate(calendar).minusDays(daysToSubtract);
		Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	/**
	 * Method crated to add days in a date
	 * 
	 * @param calendar
	 * @param daysToAdd
	 * @return Date
	 */
	static public Date addDays(Date calendar, int daysToAdd) {
		LocalDate date = DateUtil.toLocalDate(calendar).plusDays(daysToAdd);
		Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	/**
	 * Method created to return the number of days between two dates
	 * 
	 * @param dateTime
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(LocalTime dateTime) {
		if (dateTime == null) {
			return null;
		} else {
			DatatypeFactory factory;
			try {
				factory = DatatypeFactory.newInstance();
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}

			return factory.newXMLGregorianCalendarTime(dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(),
					DatatypeConstants.FIELD_UNDEFINED);
		}
	}
	
	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Method created to convert from a XMLGregorianCalendar to a Date
	 * 
	 * @param calendar
	 * @return Date
	 */
	public static Date toDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}

	/**
	 * Method created to convert from a Date to a XMLGregorianCalendar
	 * 
	 * @param calendar
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date calendar) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(calendar);

		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			xmlCalendar.setYear(gCalendar.get(GregorianCalendar.YEAR));
			xmlCalendar.setMonth(gCalendar.get(GregorianCalendar.MONTH) + 1);
			xmlCalendar.setDay(gCalendar.get(GregorianCalendar.DAY_OF_MONTH));
			xmlCalendar.toXMLFormat();
		} catch (Exception e) {
			throw new ApplicationRuntimeException("Falha ao criar uma nova instância da data", e);
		}
		return xmlCalendar;
	}

	/**
	 * Method created to convert from a LocalDateTime to a Date
	 * 
	 * @param calendar
	 * @return Date
	 */
	public static Date toDate(LocalDateTime calendar) {
		ZonedDateTime zdt = ZonedDateTime.of(calendar, ZoneId.systemDefault());
		GregorianCalendar cal = GregorianCalendar.from(zdt);
		return cal.getTime();
	}

	/**
	 * Method created to convert from a date to a LocalDateTime
	 * 
	 * @param calendar
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Date calendar) {
		return Instant.ofEpochMilli(calendar.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * Method created to convert from a Date to a LocalDateTime
	 * 
	 * @param calendar
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(Date calendar) {
		return Instant.ofEpochMilli(calendar.getTime()).atZone(ZoneOffset.systemDefault()).toLocalDate();
	}
	
	/**
	 * Method created to convert from a Date to a LocalDateTime
	 * 
	 * @param calendar
	 * @return LocalDate
	 */
	public static String toLocalDate(String date) {
		return Instant.ofEpochMilli(DateUtil.toDate(date).getTime()).atZone(ZoneOffset.systemDefault()).toLocalDate().toString();
	}

	/**
	 * Method created to convert from a XMLGregorianCalendar to a LocalTime
	 * 
	 * @param calendar
	 * @return LocalTime
	 */
	public static LocalTime toLocalTime(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		} else {
			if (isDefined(calendar.getHour()) && isDefined(calendar.getMinute())) {
				return LocalTime.of(calendar.getHour(), calendar.getMinute());
			}
			return null;
		}

	}

	/**
	 * Method created to verify if hours are defined
	 * 
	 * @param hour
	 * @return boolean
	 */
	private static boolean isDefined(int hour) {
		return hour != DatatypeConstants.FIELD_UNDEFINED;
	}

	/**
	 * Method created to convert from a String to a Date
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String toDateFormat(Date calendar, String dateFormat) {
		String result = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			result = format.format(calendar);
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para Date [value={}]", e);
			return null;
		}
		return result;
	}
	
	/**
	 * Method created to convert from a String to a Date
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String toDateFormatBr(Date calendar, String dateFormat) {
		String result = null;
		try {
			DateFormat format = new SimpleDateFormat(dateFormat, new Locale("pt", "BR"));
			result = format.format(calendar);
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para Date [value={}]", e);
			return null;
		}
		return result;
	}
	
	/**
	 * Method created to convert from a String to a Date
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String toDateFormat(Date calendar) {
		String result = null;
		try {
			result = toLocalDate(calendar).format(DateTimeFormatter.ofPattern(DATE_FMT_BR));
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para Date [value={}]", e);
			return null;
		}
		return result;
	}

	/**
	 * Method created to convert from a String to a Date
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String toDateFormat(LocalDate date) {
		String result = null;
		try {
			result = date.format(DateTimeFormatter.ofPattern(DATE_FMT_BR));
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para Date [value={}]", e);
			return null;
		}
		return result;
	}
	
	/**
	 * Method created to convert from a String to a Date
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String toDateFormatBr(LocalDateTime date, String dateFormat) {
		String result = null;
		try {
			result = date.format(DateTimeFormatter.ofPattern(dateFormat));
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para Date [value={}]", e);
			return null;
		}
		return result;
	}
	
	/**
	 * Method created to convert from a String to a Date
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String toDateFormatBrWithZone(Date date, String dateFormat) {
		var localDateTime = toLocalDateTime(date).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
				.toLocalDateTime();
		return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));
	}
	/**
	 * Method created to convert from a string to a Date
	 * 
	 * @param calendar
	 * @param format
	 * @return toDate
	 */
	public static Date toDate(String calendar, String format) {
		Date result = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			result = formatter.parse(calendar);
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para Date [value={}]", e);
			return null;
		}
		return result;
	}

	/**
	 * Method created to convert from a string to a date
	 * 
	 * @param calendar
	 * @return date
	 */
	public static Date toDate(String calendar) {
		Date result = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FMT_BR);
			result = formatter.parse(calendar);
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para Date [value={}]", e);
			return null;
		}
		return result;
	}
	
	/**
	 * To year month.
	 *
	 * @param yearMonth the year month
	 * @return the year month
	 */
	public static Date convertYearMonthToDateWithFirstDayOfMonth(String date) {
		Date result = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FMT_BR_YEAR_MONTH);
			YearMonth yearMonth = YearMonth.parse(date, formatter);
			result = asDate(LocalDate.from(yearMonth.atDay(1)));
			
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	
	/**
	 * Parses the year month.
	 *
	 * @param month the month
	 * @param year the year
	 * @return the string
	 */
	public static String parseYearMonth(Integer month, Integer year) {
		YearMonth yearMonth = YearMonth.of(year, month);
		return yearMonth.format(DateTimeFormatter.ofPattern(FMT_BR_YEAR_MONTH));
	}
	
	public static LocalDateTime removeUTC(LocalDateTime localDateTime) {
		final ZoneId zone = ZoneId.of("America/Sao_Paulo");
		ZoneOffset zoneOffSet = zone.getRules().getOffset(localDateTime);
		OffsetDateTime offsetDateTime = localDateTime.atOffset(zoneOffSet);
		return offsetDateTime.toLocalDateTime();
	}

	/**
	 * Method created to convert from a string to a LocalDateTime
	 * 
	 * @param calendar
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(String calendar) {
		LocalDateTime result = null;
		try {
			result = LocalDateTime.parse(calendar, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para LocalDateTime [value={}]", e);
			return null;
		}
		return result;
	}
	
	/**
	 * Method created to convert from a string to a LocalDateTime
	 * 
	 * @param calendar
	 * @return LocalDateTime
	 */
	public static LocalDate parseToLocalDate(String value) {
		LocalDate result = null;
		try {
			if (StringUtils.isNotBlank(value)) {
				result = LocalDate.parse(value);
			}
		} catch (Exception e) {
			LOG.debug("Erro ao converter String para LocalDateTime [value={}]", e);
			throw e;
		}
		return result;
	}
	
	/**
	 * Method created to convert to a date
	 * 
	 * @param date
	 * @param localTime
	 */
	public static Date addHourToDate(Date date, LocalTime localTime) {

		try {
			return Timestamp.valueOf(localTime.atDate(toLocalDate(date)));

		} catch (Exception e) {
			LOG.debug("Erro ao adicionar hora a data", e);
			return null;
		}
	}

	/**
	 * Method created to convert to a date
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return Date
	 */
	public static Date toDate(Integer year, Integer month, Integer day) {
		Date result = null;
		try {
			LocalDate localDate = LocalDate.of(year, month, day);
			result = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		} catch (Exception e) {
			LOG.debug("Erro ao converter para Date [value={}]", e);
			return null;
		}
		return result;
	}

	/**
	 * Method created to add hours and minutes to a date
	 * 
	 * @param date
	 * @param hour
	 * @param minute
	 * @return Date
	 */
	public static Date addHHMMToDate(Date date, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * Gets the hours from date.
	 *
	 * @param date the date
	 * @return the hours from date
	 */
	public static String getHoursFromDate(Date date) {

		String result = null;
		try {
			result = toLocalDateTime(date).toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm", new Locale("pt", "BR")));

		} catch (Exception e) {
			LOG.debug("Erro ao converter para horas [value={}]", e);
			return StringUtils.EMPTY;
		}
		return result;
	}
}

