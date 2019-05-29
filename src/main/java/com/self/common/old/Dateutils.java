package com.self.common.old;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtils {
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

	public static final String DATE_FORMAT_MM_dd = "MM_dd";

	public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";

	public static final String DATE_FORMAT_HH_MM = "HH:mm";

	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	public static final String DATE_FORMAT_YYYYMMDDHH = "yyyyMMddHH";
	
	public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";

	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
	
	public static final String DATE_FORMAT_HHMMSSSSS = "HHmmssSSS";

	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String DATE_FORMAT_YYYYMMDD_HHMM = "yyyyMMdd:HHmm";

	public static final String DATE_FORMAT_HHMM = "HHmm";

	private final static ThreadLocal<DateFormat> formaterDate = new ThreadLocal<DateFormat>();

	// 一毫秒
	public static final long TIME_ONE_MILL_SECOND = 1;
	// 一秒
	public static final long TIME_ONE_SECOND = 1000 * TIME_ONE_MILL_SECOND;
	// 一分钟
	public static final long TIME_ONE_MINUTE = TIME_ONE_SECOND * 60;
	// 一小时
	public static final long TIME_ONE_HOUR = TIME_ONE_MINUTE * 60;
	// 一天
	public static final long TIME_ONE_DAY = TIME_ONE_HOUR * 24;

	/**
	 * 检查2个时间段是否有交集，无所谓前后
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param newBeginDate
	 * @param newEndDate
	 * @return true 有交叉 false 无交叉
	 */
	public static boolean checkAcross(Date beginDate, Date endDate, Date newBeginDate, Date newEndDate) {
		long beginTime = beginDate.getTime();
		long endTime = endDate.getTime();
		long newBeginTime = newBeginDate.getTime();
		long newEndTime = newEndDate.getTime();

		if ((beginTime < newBeginTime && endTime < newBeginTime) && (beginTime < newEndTime && endTime < newEndTime)) {
			return false;
		} else if ((beginTime > newEndTime && endTime > newEndTime)
				&& (beginTime > newBeginTime && endTime > newBeginTime)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 昨天的日期字符串
	 * 
	 * @Title: yesterday
	 * @param @return
	 * @return String
	 */
	public static String yesterdayStr() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(cal.getTime());
	}

	/**
	 * 最近n天的日期 比如:day=1,返回昨天的日期字符串
	 * 
	 * @param day
	 * @return
	 */
	public static String lastOtherDayStr(int day) {
		return lastOtherCustomDayStr(new Date(), day);
	}

	/**
	 * 以date为准,最近n天的日期 比如:day=1,返回昨天的日期字符串
	 * 
	 * @param day
	 * @return
	 */
	public static String lastOtherCustomDayStr(Date date, int day) {
		Date d = lastOtherCustomDay(date, day);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(d);
	}

	/**
	 * 以date为准,最近n天的日期 比如:day=1,返回昨天的日期
	 * 
	 * @param day
	 * @return
	 */
	public static Date lastOtherCustomDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -day);
		return cal.getTime();
	}

	/**
	 * 最近n月的年月 例如month=1，返回上个月的年月 yyyy-MM
	 * 
	 * @param month
	 * @return
	 */
	public static String lastOtherMonthStr(int month) {
		return lastOtherCustomMonthStr(new Date(), month);
	}

	/**
	 * 从指定时间算起,最近n月的年月 例如month=1，返回上个月的年月 yyyy-MM
	 * 
	 * @param month
	 * @return
	 */
	public static String lastOtherCustomMonthStr(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -month);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(cal.getTime());
	}

	/**
	 * 获取当前周 yyyy-WW
	 * 
	 * @return
	 */
	public static String NowWeekStr() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return year + "-" + week;
	}

	/**
	 * 最近n周的年周 例如week=1 就是上周 yyyy-WW
	 * 
	 * @param week
	 * @return
	 */
	public static String lastOtherWeekStr(int week) {
		return lastOtherCustomWeekStr(new Date(), week);
	}

	/**
	 * 最近n周的年周 例如week=1 就是上周 yyyy-WW
	 * 
	 * @param week
	 * @return
	 */
	public static String lastOtherCustomWeekStr(Date date, int week) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, -week);
		int year = cal.get(Calendar.YEAR);
		int w = cal.get(Calendar.WEEK_OF_YEAR);
		return year + "-" + w;
	}

	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		try {
			if (date == null) {
				return StringUtils.EMPTY;
			}
			return getFormat(pattern).format(date);
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 获取 DateFormat
	 * 
	 * @param pattern
	 * @return
	 */
	public static DateFormat getFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 最近n周的起止日期
	 * 
	 * @param week
	 * @return
	 */
	public static String[] lastOtherWeekTimeStr(int week) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -week);
		int year = cal.get(Calendar.YEAR);
		int w = cal.get(Calendar.WEEK_OF_YEAR);
		return getDayOfWeek(year, w);
	}

	/**
	 * 获取某年某周的起始日期和结束日期
	 * 
	 * @param year
	 * @param weekindex
	 * @return
	 */
	public static String[] getDayOfWeek(int year, int weekindex) {
		Calendar c = Calendar.getInstance();
		c.setWeekDate(year, weekindex, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2;
		c.add(Calendar.DATE, -dayOfWeek); // 得到本周的第一天
		String begin = format.format(c.getTime());
		c.add(Calendar.DATE, 6); // 得到本周的最后一天
		String end = format.format(c.getTime());
		String[] range = new String[2];
		range[0] = begin;
		range[1] = end;
		return range;
	}
	
	/**
	 * 获得本周一0点时间（英式周）
	 */
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}
 
	/**
	 * 获得本周日24点时间（英式周）
	 * */
	public static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}
	
	
	/**
	 * 获得本周一0点时间（中式周）
	 */
	public static Date getTimesWeekmorning1() {
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK);
		week = week - 1;
		if(week == 0) {
			week = 7;
		}
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.add(Calendar.DATE, -week + 1);
		return cal.getTime();
	}
 
	public static void main(String[] args) {
		long l23 = DateUtils.getNingnTime("23:59");
		System.err.println(l23);
		long l00 = DateUtils.getNingnTime("00:00");
		l00 = l00 + (24 * 60 * 60 * 1000);
		System.err.println(l00);
	}
	
	/**
	 * 获得本周日24点时间（中式周）
	 * */
	public static Date getTimesWeeknight1() {
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK);
		week = week - 1;
		if(week == 0) {
			week = 7;
		}
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		cal.add(Calendar.DATE, -week + 7);
		return cal.getTime();
	}
	

	/**
	 * 最近n天的日期
	 * 
	 * @Title: yesterday
	 * @param @return
	 * @return String
	 */
	public static String lastOtherCustomDayStr(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -day);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(cal.getTime());
	}

	/**
	 * 最近n天的日期
	 * 
	 * @Title: yesterday
	 * @param @return
	 * @return String
	 */
	public static Date lastOtherCustomDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -day);
		return cal.getTime();
	}
	
	/**
	 * 当前时间加减N分钟（正为加，负为减）
	 * @Title: addMinute
	 * @param minute 分钟数
	 * @return Date
	 */
	public static Date addMinute(int minute) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	
	/**
	 * 指定时间加减N分钟（正为加，负为减）
	 * @Title: addMinute
	 * @param minute 分钟数
	 * @return Date
	 */
	public static Date addMinute(Date time, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 昨天日期
	 * 
	 * @Title: yesterdayDate
	 * @param @return
	 * @return Date
	 */
	public static Date yesterdayDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return StrToDate(yesterdayStr(), format);
	}
	
	
	
	/**
	 * date格式化date
	 * 
	 * @param
	 * @return date
	 */
	public static Date dateToDate(Date date, String oldsdf,String newsdf) {
		String dateToStr = DateToStr(date, oldsdf);
		return StrToDate(dateToStr, newsdf);
	}
	
	
	/**
	 * 字符串格式化字符串
	 * 
	 * @param str
	 * @return date
	 */
	public static String strToStr(String str, String oldsdf,String newsdf) {
		Date date = StrToDate(str, oldsdf);
		return DateToStr(date, newsdf);
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date, String strSdf) {
		SimpleDateFormat sdf = new SimpleDateFormat(strSdf);
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, String strSdf) {
		SimpleDateFormat sdf = new SimpleDateFormat(strSdf);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, SimpleDateFormat sdf) {
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	


	/**
	 * String转毫秒
	 * 
	 * @param inVal
	 * @return
	 */
	public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
		Date date = null; // 定义时间类型
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = format.parse(inVal); // 将字符型转换成日期型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date.getTime(); // 返回毫秒数
	}

	/**
	 * 两个时间相减
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long dateMin(String start, String end) {
		long startT = fromDateStringToLong(start); //
		long endT = fromDateStringToLong(end); //
		long ss = (endT - startT) / (1000); // 共计秒数
		return ss;
	}

	/**
	 * 两个时间相减
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long dateMin(Date start, Date end) {
		long startT = start.getTime(); //
		long endT = end.getTime(); //
		long ss = (endT - startT) / (1000); // 共计秒数
		return ss;
	}
	
	
	public static boolean compare(String obuLastLoginTimeStr, String last7DayMax) {
		long dateMin = dateMin(obuLastLoginTimeStr, last7DayMax);
		if(dateMin>=0){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateString, String pattern) {
		try {
			return getFormat(pattern).parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 取得某时间前n天,格式为yyyy-mm-dd
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static String getBeforeDays(Date date, int n) {
		Date d = getAfterDays(date, Calendar.DAY_OF_MONTH, -n);
		return getFormaterDate().format(d);
	}


	/**
	 * 取得某时间前n天,格式为 dateFormat
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static String getBeforeDays(Date date, int n,String dateFormat) {
		Date d = getAfterDays(date, Calendar.DAY_OF_MONTH, -n);
		return getFormat(dateFormat).format(d);
	}
	
	public static DateFormat getFormaterDate() {
		DateFormat formater = formaterDate.get();
		if (formater == null) {
			formater = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
			formaterDate.set(formater);
		}
		return formater;
	}

	public static Date getAfterDays(Date date, int type, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(type, amount);
		return c.getTime();
	}

	/**
	 * 当天开始日期
	 * 
	 * @return
	 */
	public static Date getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/**
	 * 当天结束日期
	 */
	public static Date getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}

	/**
	 * 获得指定格式的当前日期开始是日期
	 * 
	 * @param startDate
	 * @return
	 */
	public static String formatCurrentDate(Date startDate) {
		return getFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(startDate);
	}

	/**
	 * 获得指定格式的当前日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatCurrentDate(String pattern) {
		return getFormat(pattern).format(new Date());
	}

	/**
	 * 获取两个时间相差的秒
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getDistDatesBySecond(Date startTime, Date endTime) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(startTime);
		long time0 = aCalendar.getTimeInMillis();
		aCalendar.setTime(endTime);
		long time1 = aCalendar.getTimeInMillis();
		return (time1 - time0) / (TIME_ONE_SECOND);
	}

	/**
	 * 获取开始和结束日期之间的所有日期
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 是否为当天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH) + 1;
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH) + 1;
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		if (year1 == year2 && month1 == month2 && day1 == day2) {
			return true;
		}
		return false;
	}

	/**
	 * 计算两段日期之间的天数
	 * 
	 * @param beginDate
	 * 
	 */
	public static long getDaysByDate(Date beginDate, Date endDate, int amount) {
		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("argu is ellegal,beginDate or endDate is null");
		}
		long times = endDate.getTime() - beginDate.getTime();
		long result = 0;
		switch (amount) {
		case Calendar.MILLISECOND:
			result = times;
			break;
		case Calendar.SECOND:
			result = times / 1000;
			break;
		case Calendar.MINUTE:
			result = times / TIME_ONE_MINUTE;
			break;
		case Calendar.HOUR:
			result = times / TIME_ONE_HOUR;
			break;
		case Calendar.DATE:
			result = times / TIME_ONE_DAY;
			break;
		default:
			throw new IllegalArgumentException("argu is ellegal,amount is error.");
		}
		return result;
	}

	/**
	 * 计算两段日期之间的天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getDaysByDate(Date beginDate, Date endDate) {
		long days = 0;
		if (beginDate == null || endDate == null)
			return days;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(beginDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);

		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();
		// 计算天数
		days = (l2 - l1) / TIME_ONE_DAY;
		return days;
	}

	/**
	 * 获得指定格式的前n天日期
	 */
	public static String formatCustomDate(int day, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -day);
		return getFormat(pattern).format(cal.getTime());
	}
	
	public static Date longToDate(Long time){
		  if(time==null){
			  return null;
		  }
		 try {
			  SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		      String d = format.format(time);  
		      Date date=format.parse(d); 
		      return date;
		} catch (Exception e) {
			logger.error("",e);
		}
		 return null;
	}
	
	/**
	 * 获得指定格式的前n小时日期
	 */
	public static String formatCustomHour(int hour, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -hour);
		return getFormat(pattern).format(cal.getTime());
	}

	/**
	 * 获得指定格式的前n天日期
	 * @param day
	 * @return
	 */
	public static Date formatCustomDate(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -day);
		return cal.getTime();
	}

	/**
	 * 获取date每间隔interval分钟的所有时间点
	 * 
	 * @param date
	 * @param interval
	 * @return
	 */
	public static List<Date> get2MinuteOneDay(Date date, int interval) {
		Date start = dayStartDate(date);// 转换为天的起始date
		Date nextDayDate = nextDay(start);// 下一天的date

		List<Date> result = new ArrayList<Date>();
		while (start.compareTo(nextDayDate) < 0) {
			result.add(start);
			// 日期加2分钟
			start = addFiveMin(start, 2);
		}

		return result;
	}

	private static Date addFiveMin(Date start, int offset) {
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.MINUTE, offset);
		return c.getTime();
	}

	private static Date dayStartDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	private static Date nextDay(Date start) {
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	 * 获取指定时间的下一天
	 * 
	 * @param time
	 * @return
	 */
	public static Date getNextDay(Date time) {
		return getAfterDayTime(time, 1);
	}

	/**
	 * 获取某时间后n天的时间
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getAfterDayTime(Date date, int n) {
		return getAfterDays(date, Calendar.DAY_OF_MONTH, n);
	}

	/**
	 * 当前时间在一个时间范围
	 * 
	 * @param nowDateStr
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static boolean isInDateScope(String nowDateStr, String startDateStr, String endDateStr) {
		boolean in = false;
		int beginRegionTime = 0;
		int endRegionTime = 0;
		int currentTime = Integer.parseInt(nowDateStr);
		if (StringUtils.isNotBlank(startDateStr)) {
			beginRegionTime = Integer.parseInt(startDateStr);
		}
		if (StringUtils.isNotBlank(endDateStr)) {
			endRegionTime = Integer.parseInt(endDateStr);
		}
		if (currentTime >= beginRegionTime && currentTime <= endRegionTime) {
			in = true;
		}
		return in;
	}

	/**
	 * 获取指定时间的上一天
	 * 
	 * @param time
	 * @return
	 */
	public static Date returnPreviousDay(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		calendar.get(Calendar.DAY_OF_MONTH);
		return calendar.getTime();
	}

	/**
	 * 获得最近三天的日期
	 * 
	 * @param
	 * @return
	 */
	public static String[] last3DaysDates() {
		String last1Day = formatCustomDate(1, DATE_FORMAT_YYYY_MM_DD);
		String last2Day = formatCustomDate(2, DATE_FORMAT_YYYY_MM_DD);
		String last3Day = formatCustomDate(3, DATE_FORMAT_YYYY_MM_DD);
		
		// 测试加上今天
		//String today = formatCustomDate(0, DATE_FORMAT_YYYY_MM_DD);
		
		String[] dates = { last1Day, last2Day, last3Day};
		return dates;
	}

	/**
	 * 格式转化
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(String date, String pattern) {
		try {
			if (StringUtils.isEmpty(date)) {
				return null;
			}
			return getFormat(pattern).format(parseDate(date, DATE_FORMAT_YYYY_MM_DD));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取是星期几（1-7）
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int res = calendar.get(Calendar.DAY_OF_WEEK)-1;
		return res==0?7:res;
	}

	/**
	 * 分钟
	 * @param date
	 * @return
	 */
	public static int getMinutesOfDay(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int hour= calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		return hour*60+minutes;
	}
	
	/**
	 * 获取 宁宁格式 时间毫秒数
	 * <p>宁宁时间格式：在固定日期（2017-05-26），加上时分的时间
	 * @param hourMinute 字符串时分，如：12:40
	 * @return -1 = 格式输入错误，其他为正确
	 */
	public static long getNingnTime(String hourMinute) {
		try {
			String[] split = hourMinute.split(":");
			if(split.length == 2) {
				int hour = Integer.parseInt(split[0]);
				int minute = Integer.parseInt(split[1]);
				if(hour < 0 || hour > 23) {
					return -1;
				}
				if(minute < 0 || minute > 59) {
					return -1;
				}
				//加上固定日期：2017-05-26
				Calendar cal = Calendar.getInstance();
				cal.set(2017, 05 -1, 26, hour, minute, 0);
				return cal.getTime().getTime();
			} else {
				return -1;
			}
		} catch (Exception e) {
			logger.error("宁宁格式 时间转换异常.");
			return -1;
		}
	}
	
	/**
	 * 获取 宁宁格式 时间毫秒数
	 * <p>宁宁时间格式：在固定日期（2017-05-26），加上时分的时间
	 * @param hourMinute 指定时间，取其时和分
	 * @return -1 = 格式输入错误，其他为正确
	 */
	public static long getNingnTime(Date time) {
		if(time == null) {
			return -1;
		}
		try {
			Calendar ctime = Calendar.getInstance();
			ctime.setTime(time);
			//加上固定日期：2017-05-26
			Calendar cal = Calendar.getInstance();
			int hour = ctime.get(Calendar.HOUR_OF_DAY);
			int minute = ctime.get(Calendar.MINUTE);
			cal.set(2017, 05 -1, 26, hour, minute, 0);
			return cal.getTime().getTime();
		} catch (Exception e) {
			logger.error("宁宁格式 时间转换异常.");
			return -1;
		}
	}


}
