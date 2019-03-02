package com.sjhy.platform.biz.deploy.utils;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * java.sql.Timestamp & java.util.Date Utility Function
 * 
 * @author 
 */
public class UtilDate {
	private static FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH|mm|ss"); // 指定年月日,多线程安全
	public static final int DAY_SECONDS = 24 * 60 * 60;
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	public final static String ORDER_DATE_PATTERN = "yy-MM-dd";
	public final static String TIME_PATTERN = "HH:mm:ss";
	public final static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public final static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.fff";
	public final static String SHOW_DATETIME_PATTERN = "yyyy-MM-dd HH:mm";
	
	public final static String ZH_DATETIME_PATTERN = "yyyy年MM月dd日 HH:mm";
	public final static String EN_DATETIME_PATTERN = "MMM d'th', yyyy HH:mm";
	
	public static final Timestamp NOTAVAIABLE = UtilDate.str2Date("2037-12-31",DATE_PATTERN);
	
	public final static String DATE_PATTERN_INT = "yyyyMMdd";

	// /////////////////////////////////////////////////////////
	// java.sql.Timestamp Functions
	// /////////////////////////////////////////////////////////
	/**
	 * 得到两个时间的差值,以秒记

	 * 
	 * @param bigDate
	 * @param smallDate
	 * @return
	 */
	public static long secondDistance(Date bigDate, Date smallDate) {
		if (bigDate == null || smallDate == null)
			return 0;

		return (bigDate.getTime() - smallDate.getTime()) / 1000;
	}

	public static long secondDistance(Date smallDate) {
		return secondDistance(new Date(),smallDate);
	}
	
	
	/**
	 * 将天数转换成秒
	 * @return
	 */
	public static long formatTwoDays(int days){
		return days*24*3600;
	}
	
	/**
	 * 以当前时间为基准, 向前(后)推移若干秒

	 * 
	 * @param second
	 * @return
	 */
	public static Date moveSecond(long second) {
		return moveSecond(nowTimestamp(), second);
	}

	/**
	 * 以给定时间为基准, 向前(后)推移若干秒

	 * 
	 * @param baseDttm
	 * @param second
	 * @return
	 */
	public static Date moveSecond(Date baseDttm, long second) {
		if (baseDttm == null)
			baseDttm = nowDateTime();
		long time = baseDttm.getTime();
		time += second * 1000;

		return new Date(time);
	}

	/**
	 * Return a Timestamp for right now
	 * 
	 * @return Timestamp for right now
	 */
	public static Timestamp nowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date nowDateTime(){
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Timestamp to Str, 只要时间部分HH:mm:ss
	 *
	 * @param dttm
	 * @return
	 */
	public static String getTime(Timestamp dttm) {
		if (dttm == null)
			return null;
		return getDatetime(dttm).substring(11);
	}

	/**
	 * Timestamp to Str, 只要日期部分yyyy-MM-dd
	 *
	 * @param dttm
	 * @return
	 */
	public static String getDate(Date dttm) {
		if (dttm == null)
			return null;

		SimpleDateFormat lFormatTimestamp = new SimpleDateFormat(DATE_PATTERN);
		return lFormatTimestamp.format(dttm);
	}

	public static int getDateInt(Date dttm) {
		if (dttm == null)
			return 0;

		SimpleDateFormat lFormatTimestamp = new SimpleDateFormat(DATE_PATTERN_INT);
		String now = lFormatTimestamp.format(dttm);
		int rtn = 0;
		if(StringUtils.isNumeric(now))
			rtn = Integer.valueOf(now);
		return rtn;
	}

	/**
	 * 当前时间是否在某个时间段内

	 *
	 *
	 * @param startTime
	 *            HH:MM:SS
	 * @param endTime
	 *            HH:MM:SS
	 * @return
	 */
	public static boolean betweenTime(String startTime, String endTime) {
		String sTime = getDate(nowTimestamp()) + " " + startTime;
		String eTime = getDate(nowTimestamp()) + " " + endTime;

		Timestamp tsStart = str2Date(sTime, DATETIME_PATTERN);
		Timestamp tsEnd = str2Date(eTime, DATETIME_PATTERN);
		Timestamp now = nowTimestamp();

		return (now.compareTo(tsStart) >= 0 && now.compareTo(tsEnd) <= 0);
	}

	public static boolean betweenTime(Timestamp startTime, Timestamp endTime)
	{
		long nowTime = System.currentTimeMillis();
		return (nowTime>=startTime.getTime() && nowTime<=endTime.getTime());
	}
	/**
	 * Timestamp to Str, yyyy-MM-dd HH:mm
	 *
	 * @param dttm
	 * @return
	 */
	public static String getDatetime(Timestamp dttm) {
		if (dttm == null)
			return null;

		SimpleDateFormat lFormatTimestamp = new SimpleDateFormat(
				DATETIME_PATTERN);
		return lFormatTimestamp.format(dttm);
	}

	/**
	 * string to timstamp, with "yyyy-MM-dd HH:mm:ss.fffffffff"
	 *
	 * @param asDate
	 * @return
	 */
	public static Timestamp str2Date(String asDate) {
		return str2Date(asDate, DATETIME_PATTERN);
	}

	/**
	 * string to timestamp, with given pattern
	 *
	 * @param asDate
	 * @param asPattern
	 * @return
	 */
	public static Timestamp str2Date(String asDate, String asPattern) {
		Timestamp lStamp = null;

		if (asDate == null || asDate.trim().length() == 0)
			return null;

		if (asPattern == null || asPattern.trim().length() == 0) {
			try {
				lStamp = Timestamp.valueOf(asDate);
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				SimpleDateFormat lFormat = new SimpleDateFormat(asPattern);
				lStamp = new Timestamp(lFormat.parse(asDate).getTime());
			} catch (Exception e) {
				return null;
			}
		}

		return lStamp;
	}

	// /////////////////////////////////////////////////////////
	// java.util.Date Functions
	// /////////////////////////////////////////////////////////
	public static String date2Text(Date date) {
		return date2Text(date, DATE_PATTERN);
	}

	public static String datetime2Text(Date datetime) {
		return date2Text(datetime, DATETIME_PATTERN);
	}

	public static String datetime2ShowText(Date datetime) {
		return date2Text(datetime, DATE_PATTERN);
	}

	public static String date2Text(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String text = "";
		try {
			text = formatter.format(date);
		} catch (Exception e) {
		}
		return text;
	}

	public static Date text2Date(String text) {
		return text2Date(text, DATETIME_PATTERN);
	}

	public static Date datetime2Text(String text) {
		return text2Date(text, DATETIME_PATTERN);
	}

	public static Date text2Date(String text, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = formatter.parse(text);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 将600 秒 转为10:00
	 *
	 * @param time
	 * @return
	 */
	public static String formatIntTime(int time) {
		if (time < 60)
			return "00:00:" + StringUtils.leftPad("" + time, 2, '0');
		else if (time >= 60 && time < 3600) {
			String mm = "" + time / 60;
			String ss = "" + time % 60;
			return "00:" + StringUtils.leftPad(mm, 2, '0') + ":"
					+ StringUtils.leftPad(ss, 2, '0');
		} else {
			String hh = "" + time / 3600;
			int ms = time % 3600;
			String mm = "" + ms / 60;
			String ss = "" + ms % 60;
			return StringUtils.leftPad(hh, 2, '0') + ":"
					+ StringUtils.leftPad(mm, 2, '0') + ":"
					+ StringUtils.leftPad(ss, 2, '0');
		}
	}

	public static String formatlongTimeToMinutes(long time) {
		if (time < 60)
			return "00:" + StringUtils.leftPad("" + time, 2, '0');
		else {
			String mm = "" + time / 60;
			String ss = "" + time % 60;
			return StringUtils.leftPad(mm, 2, '0') + ":"
					+ StringUtils.leftPad(ss, 2, '0');
		}
	}

	/**
	 * 当前时间是否在每天指定的时间后

	 *
	 * @param time
	 *            HH:mm:ss
	 */
	public static boolean dailyAfterTime(String time) {
		Timestamp now = UtilDate.nowTimestamp();
		String timeStr = UtilDate.getDate(now) + " " + time + ".000";
		Timestamp pointTime = UtilDate.str2Date(timeStr);
		if (UtilDate.secondDistance(pointTime, now) > 0)
			return true;
		return false;
	}


	/**
	 * 当前时间是否在每天指定的时间前

	 *
	 * @param time
	 *            HH:mm:ss
	 */
	public static boolean dailyBeforeTime(String time) {
		Timestamp now = UtilDate.nowTimestamp();
		String timeStr = UtilDate.getDate(now) + " " + time + ".000";
		Timestamp pointTime = UtilDate.str2Date(timeStr);
		if (UtilDate.secondDistance(pointTime, now) < 0)
			return true;
		return false;
	}

	/**
	 * 当前时间距离每天指定的时间差值
	 *
	 * @param time HH:mm
	 *
	 * 单位为秒
	 */
	public static long getDailyBetweenTime(String time) {
		Date now = Calendar.getInstance().getTime();
		String timeStr = UtilDate.getDate(now) + " " + time + ":00";

		Timestamp pointTime = UtilDate.str2Date(timeStr);

		return UtilDate.secondDistance(pointTime, now);
	}

	/**
	 * 取得设定时间
	 * @param time HH:mm
	 *
	 */
	public static Date getInitDate(Calendar cal, String time){
		String s = getDate(cal.getTime()) + " " + time + ":00";

		return text2Date(s);
	}

	/**
	 * 获取当前时间一个月前的日期点
	 * @param
	 * @return
	 */
	public static String getLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);

		if(calendar.get(Calendar.MONTH) == 0){
			calendar.add(Calendar.YEAR, -1);
		}

		int year  = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int date  = calendar.get(Calendar.DATE);

		String dateStr = String.valueOf(year)+String.valueOf(month)+String.valueOf(date)+"00:00:00";

		return dateStr;
	}


	public static int betweenTwoDate(Date lastDate, Date nowDate){
		String lastDateStr = UtilDate.getDate(lastDate);
		String nowDataStr = UtilDate.getDate(nowDate);
		lastDateStr = lastDateStr + " 00:00:00";
		nowDataStr = nowDataStr + " 00:00:00";
		Date newLastDate = UtilDate.text2Date(lastDateStr, DATETIME_PATTERN);
		Date newNowDate = UtilDate.text2Date(nowDataStr, DATETIME_PATTERN);
		if(newNowDate.getTime()-newLastDate.getTime()>(24*3600*1000)){
			return 2;
		}else if(newNowDate.getTime()-newLastDate.getTime() == (24*3600*1000)){
			return 1;
		}else{
			return 0;
		}

	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int dayOfBetweenTwoDate(Date beginDate, Date endDate){
		Long minusDay = endDate.getTime() - beginDate.getTime();

		return (int)(minusDay/(24*3600*1000));
	}

	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//		df.setTimeZone(TimeZone.getTimeZone("UTC"));//Wed Feb 28 16:18:42 CST 2018
//		System.out.println(df.parse("2018-02-28T08:18:42Z").toString());
		Date dt = new Date(15216336191334L);
		System.out.println(date2Text(dt,DATETIME_PATTERN));
	}
	
	public static String getDateAsString(Date d) {
		return fastDateFormat.format(d);
	}
	
	public static int getWeek(Date d){
		Calendar c = Calendar.getInstance();
		
		c.setTime(d);
		
		int week = c.get(Calendar.DAY_OF_WEEK);
		week = week-1;
		if (week == 0){
			week = 7;
		}
		
		return week;
	}
	
	/**
	 * 计算出星期几的开始时间
	 * @param week
	 * @param time
	 * @param mod  1 表示向后推时间,0 则最近时间
	 * @return
	 */
	public static long getTimeMillis(int week, String time, int mod){
		String[] timeArr = time.split(":");
		int hour   = Integer.parseInt(timeArr[0]);
		int minute = Integer.parseInt(timeArr[1]);
		
		int currWeek = getWeek(new Date());
		
		Calendar timeCalendar = Calendar.getInstance();
		timeCalendar.setTime(new Date());
		
		int dayMinus = week - currWeek;
		
		// 如果星期差是负数，那么加上一周往前推
		if(mod == 1){
			timeCalendar.add(Calendar.DATE, dayMinus+7);
		}else{
			timeCalendar.add(Calendar.DATE, dayMinus);
		}
		
		timeCalendar.set(Calendar.HOUR_OF_DAY, hour);
		timeCalendar.set(Calendar.MINUTE, minute);
		timeCalendar.set(Calendar.SECOND, 0);
		
		return timeCalendar.getTime().getTime();
	}
	
	/**
	 * 时间往前或者往后推算N天
	 * @param 
	 * @return 
	 */
	public static Date getDay(Date d, int n){
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(d);
		
		calendar.add(Calendar.DATE, n);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar.getTime();
	}
	
	/**
	 * 转换日期格式 xx年xx月xx日 xx:xx
	 * @param d
	 * @return
	 */
	public static String getDateDetail(Date d, String language){
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(d);
		
		String s = "";
		if(language.equals("EN")){
			SimpleDateFormat format = new SimpleDateFormat("d");
		    String date = format.format(calendar.getTime());


		    if(date.endsWith("1") && !date.endsWith("11"))
		        format = new SimpleDateFormat("MMM d'st', yyyy HH:MM");
		    else if(date.endsWith("2") && !date.endsWith("12"))
		        format = new SimpleDateFormat("MMM d'nd', yyyy HH:MM");
		    else if(date.endsWith("3") && !date.endsWith("13"))
		        format = new SimpleDateFormat("MMM d'rd', yyyy HH:MM");
		    else 
		        format = new SimpleDateFormat("MMM d'th', yyyy HH:MM");
		    
		    s = format.format(calendar.getTime());
		}else{
			s = date2Text(calendar.getTime(), ZH_DATETIME_PATTERN);
		}
//		String year = calendar.get(Calendar.YEAR)+"";
//		String month = calendar.get(Calendar.MONTH)+1+"";
//		String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
//		String hour = calendar.get(Calendar.HOUR_OF_DAY)+"";
//		String minute = calendar.get(Calendar.MINUTE)+"";
//		//String second = calendar.get(Calendar.SECOND)+"";
//		
//		String s = year +"年";
//		if(month.length() > 1){
//			s = s + month +"月";
//		}else{
//			s = s + "0" + month +"月";
//		}
//		
//		if(day.length() > 1){
//			s = s + day + "日 ";
//		}else{
//			s = s + "0" + day +"日 ";
//		}
//		
//		if(hour.length() > 1){
//			s = s + hour + ":";
//		}else{
//			s = s + "0" +hour + ":";
//		}
//		if(minute.length() > 1){
//			s = s + minute;
//		}else{
//			s = s + "0" + minute;
//		}
		
		return s;
	}
	
}
