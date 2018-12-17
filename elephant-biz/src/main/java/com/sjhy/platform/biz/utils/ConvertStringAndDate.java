package com.sjhy.platform.biz.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertStringAndDate
{
	public static Date convertStringToDate(String dateString, String format) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateString);
	}
	public static String convertDateToString(Date date, String format)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	public static int convertDateToInt(Date date, String format)
	{
		return Integer.parseInt(convertDateToString(date,format));
	}
}
