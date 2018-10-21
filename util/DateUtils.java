package com.sinosoft.platform.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * 日期处理工具类，获取和转换日期等
 * 
 * @author Darker.Wang
 * @date 2016-8-11 下午5:30:59
 * @company SINOSOFT
 */
public class DateUtils {
	private static final Logger logger = Logger.getLogger(DateUtils.class);

	/**
	 * 得到当前系统日期,日期格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return String
	 * @author Darker.Wang
	 */
	public static String DateToString(Date date) {
		if (date == null) {
			return null;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	public static String DateToString(Date date,String dateFormat) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(date);
	}
	/**
	 * 获取当前系统时间戳
	 * 
	 * @return Date
	 * @author likai
	 */
	public static Date getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当前系统时间戳
	 * 
	 * @return String
	 * @author likai
	 */
	public static String getCurrentTimestampString() {
		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		return "to_timestamp('" + timestamp + "','yyyy-mm-dd hh24:mi:ss.ff')";
	}

	/**
	 * 获取系统TimeMillis转换成时间
	 * 
	 * @param l
	 * @return
	 */
	public static String getTimestampString(long l) {
		String timestamp = new Timestamp(l).toString();
		return timestamp;
	}

	/**
	 * 得到当前系统日期,日期格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrentDateTime() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date());
	}

	/**
	 * 获取到当前系统日期，精确到时间
	 * 
	 * @return
	 */
	public static Date getDateTime() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = df.parse(getCurrentDateTime());
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
	}

	/**
	 * 功能:得到某时间单位,在某个时间段内往后或往前推某个时间段的日期 日期格式：yyyy--MM--dd unit:Y,M,D
	 * 
	 * @param baseDate
	 *            起始日期
	 * @param interval
	 *            时间间隔
	 * @param unit
	 *            时间间隔单位，可以为 Y（年）、M（月）、D（天）
	 * @return Date
	 */
	public static Date calDate(Date baseDate, int interval, String unit) {
		Date returnDate = null;

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(baseDate);

		if (unit.equals("Y")) {
			gregorianCalendar.add(Calendar.YEAR, interval);
		}
		if (unit.equals("M")) {
			gregorianCalendar.add(Calendar.MONTH, interval);
		}
		if (unit.equals("D")) {
			gregorianCalendar.add(Calendar.DATE, interval);

		}
		if (unit.equals("H")) {
			gregorianCalendar.add(Calendar.HOUR, interval);

		}
		if (unit.equals("m")) {
			gregorianCalendar.add(Calendar.MINUTE, interval);

		}
		if (unit.equals("s")) {
			gregorianCalendar.add(Calendar.SECOND, interval);

		}
		returnDate = gregorianCalendar.getTime();

		gregorianCalendar = null;

		return returnDate;
	}

	/**
	 * 功能:得到某时间单位,在某个时间段内往后或往前推某个时间段的日期 日期格式：yyyy--MM--dd unit:Y,M,D
	 * 
	 * @param baseDate
	 *            起始日期
	 * @param interval
	 *            时间间隔
	 * @param unit
	 *            时间间隔单位，可以为 Y（年）、M（月）、D（天）
	 * @return String
	 * @throws ParseException
	 */
	public static String calDate(String baseDate, int interval, String unit) {
		//北京意外险时间特殊处理
		if (unit.equals("m")) {
			Calendar calendar = Calendar.getInstance();
			if(12 > baseDate.length()) {
				baseDate = baseDate + " 00:00:00";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(baseDate);
			} catch (ParseException e) {
				// TODO 日期格式错误
				e.printStackTrace();
				return null;
			}
			calendar.setTime(date);// 设置日历时间
			// 在日历的日期按照给定的日期单位增加一个间隔;
			calendar.add(Calendar.MINUTE, interval);
			return sdf.format(calendar.getTime());
		} else {
			// 获得一个日历的实例
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(baseDate);
			} catch (ParseException e) {
				// TODO 日期格式错误
				e.printStackTrace();
				return null;
			}
			calendar.setTime(date);// 设置日历时间
			// 在日历的日期按照给定的日期单位增加一个间隔;
			if (unit.equals("Y"))
				calendar.add(Calendar.YEAR, interval);
			else if (unit.equals("M"))
				calendar.add(Calendar.MONTH, interval);
			else if (unit.equals("D"))
				calendar.add(Calendar.DATE, interval);
			else
				calendar.add(Calendar.DATE, interval);// 如果传错单位,默认日期单位是天
			return sdf.format(calendar.getTime());
		}

	}

	/**
	 * 比较两个日期的大小, startDate>endDate,return fasle else return true
	 * 
	 * @param if
	 * 
	 * @param compareDate
	 * @return boolean
	 * @throws ParseException
	 */
	public static boolean isDateBefore(String startDateString,
			String endDateString) throws ParseException {
		Date stateDate = getDate(startDateString);
		Date endDate = getDate(endDateString);

		if (stateDate.after(endDate)) {
			logger.debug("开始日期晚于结束日期！！！！");
			return false;
		} else {
			logger.debug("开始日期早于结束日期！！！");
			return true;
		}
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔
	 * 
	 * @param startDate
	 *            起始日期，Date变量
	 * @param endDate
	 *            终止日期，Date变量
	 * @param unit
	 *            时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return int 时间间隔
	 */
	public static int calInterval(Date startDate, Date endDate, String unit) {
		int interval = 0;

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(startDate);
		int years = gregorianCalendar.get(Calendar.YEAR);
		int months = gregorianCalendar.get(Calendar.MONTH);
		int days = gregorianCalendar.get(Calendar.DAY_OF_MONTH);

		GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
		gregorianCalendar2.setTime(endDate);
		int years2 = gregorianCalendar2.get(Calendar.YEAR);
		int months2 = gregorianCalendar2.get(Calendar.MONTH);
		int days2 = gregorianCalendar2.get(Calendar.DAY_OF_MONTH);

		if (unit.equals("Y")) {
			interval = years2 - years;
			if (months2 < months) {
				interval--;
			} else {
				if (months2 == months && days2 < days) {
					interval--;
					if (months2 == 1) { // 如果同是2月，校验润年问题
						if ((years % 4) == 0 && (years2 % 4) != 0) { // 如果起始年是润年，终止年不是润年
							if (days2 == 28) { // 如果终止年不是润年，且2月的最后一天28日，那么补一
								interval++;
							}
						}
					}
				}
			}
		}
		if (unit.equals("M")) {
			interval = years2 - years;
			interval *= 12;

			interval += months2 - months;
			if (days2 < days) {
				interval--;
				// eDays如果是月末，则认为是满一个月
				int maxDate = gregorianCalendar2
						.getActualMaximum(Calendar.DATE);
				if (days2 == maxDate) {
					interval++;
				}
			}
		}
		if (unit.equals("D")) {
			gregorianCalendar.set(years, months, days);
			gregorianCalendar2.set(years2, months2, days2);
			long lInterval = (gregorianCalendar2.getTime().getTime() - gregorianCalendar
					.getTime().getTime()) / 86400000;
			interval = (int) lInterval;
		}
		return interval;
	}

	/**
	 * 输入符合格式要求的日期字符串，返回日期类型变量
	 * 
	 * @param dateString
	 * @return Date yyyy-MM-dd
	 * @throws ParseException
	 */
	public static Date getDate(String dateString) {
		Date date = null;
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			date = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			try {
				date = simpleDateFormat.parse(dateString);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return date;
	}

	/**
	 * 输入符合格式要求的日期字符串，返回日期类型变量
	 * 
	 * @param dateString
	 * @return Date yyyy-MM-dd HH:mm:ss
	 * @throws ParseException
	 */
	public static Date getDateTime(String dateString) throws ParseException {
		Date date = null;
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			date = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			return getDate(dateString);
		}
		return date;
	}

	/**
	 * 输入日期类型变量，返回日期字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateString(Date date) {
		String dateString = null;
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			dateString = simpleDateFormat.format(date);
		}
		return dateString;
	}

	/**
	 * 输入日期类型变量，返回日期字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateTimeString(Date date) {
		String dateString = null;
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateString = simpleDateFormat.format(date);
		}
		return dateString;
	}

	/**
	 * 输入日期类型变量，返回日期字符串
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateTimeString(String date) {
		String dateString = null;
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateString = simpleDateFormat.format(date);
		}
		return dateString;
	}

	/**
	 * 输入日期类型变量，返回日期字符串
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateTimeString() {
		String dateString = null;
		Date date = getCurrentTimestamp();
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			dateString = simpleDateFormat.format(date);
		}
		logger.debug("dateString : " + dateString);

		return dateString;
	}

	/**
	 * 输入日期类型变量，返回日期字符串
	 * 
	 * @param date
	 * @return String
	 */
	public static String getDateChnString(String dateString) {
		Date date = null;
		String dateChnString = "";
		if (dateString != null && !"".equals(dateString)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			try {
				date = simpleDateFormat.parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy年MM月dd日");
			dateChnString = simpleDateFormat.format(date);
			logger.debug("date:" + date.toString());
		}
		logger.debug("dateString : " + dateString);
		return dateChnString;
	}

	/**
	 * 输入日期类型变量，返回日期int
	 * 
	 * @param date
	 * @return String
	 */
	public static Integer getDateInteger(Date date) {
		String dateString = getDateString(date);
		Integer dateNum = Integer.parseInt(dateString.replace("-", ""));
		return dateNum;
	}

	/**
	 * @param dateFormat
	 *            日期格式YYYY-MM-DD 等
	 * @return
	 */
	public static String getTimeStamp(String dateFormat) {
		String dateString = null;
		Date date = new Date();
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
			dateString = simpleDateFormat.format(date);
		}
		logger.debug("dateString : " + dateString);
		return dateString;
	}

	/**
	 * 获取日期，无"-"区分年月日
	 * 
	 * @return
	 */
	public static String getCurrentDateSerial() {
		String rtvS = getCurrentDate();
		rtvS = rtvS.replaceAll("-", "");
		return rtvS;
	}

	/**
	 * 获取时间，无":"区分时分秒
	 * 
	 * @return
	 */
	public static String getCurrentTimeSerial() {
		String rtvS = getCurrentTime();
		rtvS = rtvS.replaceAll(":", "");
		return rtvS;
	}

	/**
	 * 得到当前系统日期 author: Darker.Wang
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: Darker.Wang modify by dongming 2009-01-09
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 获取时间，精确到毫秒，yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getCurrentTimeToHao() {
		String rtvStr = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new java.util.Date());
		return rtvStr;
	}

	/**
	 * 获取时间，精确到毫秒，自定义时间格式<br>
	 * 
	 * @eg yyyyMMddHHmmssSSS OR yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime(String type) {
		String rtvStr = new SimpleDateFormat(type).format(new java.util.Date());
		return rtvStr;
	}

	/**
	 * 获取日期时间，yyyyMMddhhmmss
	 * 
	 * @return
	 */
	public static String getCurrentDateAndTime() {
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 获取日期时间，yyyyMMddhhmm
	 * 
	 * @return
	 */
	public static String getCurrentDateAndTimeToMin() {
		String rtvS = getCurrentTime();
		rtvS = rtvS.substring(0, rtvS.length() - 2);
		rtvS.replaceAll(":", "");
		return getCurrentDate() + rtvS;
	}

	/**
	 * 转化为默认格式 yyyy-mm-dd
	 * 
	 * @param oDate
	 *            Tue Dec 08 12:00:25 CST 2015
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String convertLocalDate(String oDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String rtv = format.format(new java.util.Date(oDate));
		return rtv;
	}
	

	/**
	 * 转化为指定格式
	 * 
	 * @param type
	 *            指定格式：eg yyyy-mm-dd hh:MM:ss
	 * @param oDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String convertLocalDate(String type, String oDate) {
		SimpleDateFormat format = new SimpleDateFormat(type);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String rtv = oDate;
		try {
			rtv = format.parse(oDate).toLocaleString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtv;
	}

	/**
	 * 格林时间转化为本地日期格式
	 * 
	 * @param gmts
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static String convert2Date(String gmts) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy",
				Locale.US);
		Date date = sdf.parse(gmts);
		return date.toLocaleString();
	}

	/**
	 * 格林时间转化为本地日期格式
	 * 
	 * @param gmts
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parase2Date(String gmts) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.US);
		Date date = sdf.parse(gmts);
		return date;
	}

	/**
	 * <b>两个日期比较</b>
	 * <p>
	 * aDate 大于 bDate 返回 1， aDate 小于 bDate 返回 -1 ， aDate 等于 bDate 返回 0
	 * </p>
	 * 
	 * @param aDate
	 *            比较日期
	 * @param bDate
	 *            参照日期
	 * @return
	 * @throws ParseException
	 */
	public static int compareDate(String aDate, String bDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date tAdate = sdf.parse(aDate);
		Date tBdate = sdf.parse(bDate);
		int i = tAdate.compareTo(tBdate);
		return i;
	}

	/**
	 * aDate 早于 bDate 返回true 否则返回false
	 * 
	 * @param aDate
	 * @param bDate
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDate1(String aDate, String bDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date tAdate = sdf.parse(aDate);
		Date tBdate = sdf.parse(bDate);
		return tAdate.before(tBdate);
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static String getCurrentYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.CHINA);
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 获取当前月
	 * 
	 * @return
	 */
	public static String getCurrentMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.CHINA);
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 获取当前日
	 * 
	 * @return
	 */
	public static String getCurrentDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.CHINA);
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 测试主方法
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtils
				.getCalendar(DateUtils.getCurrentDateTime()).getTime());
	}

	/**
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getCalendar(String string) throws ParseException {
		if (string == null || string.equals("")) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dataformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		Date date = null;
		try {
			date = dataformat.parse(string);
		} catch (ParseException e) {
			throw e;
		}
		calendar.setTime(date);
		// System.out.println(calendar);
		// System.out.println(calendar.getTime());
		return calendar;
	}
}