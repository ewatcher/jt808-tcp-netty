package com.tuocheng.jt808.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author 农峰
 * @time Apr 25, 2018 -12:28:05 AM
 * @version v1.0
 * @Description
 *
 */
public class MyDateUtils {



	/**
	 * 获得该月第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime());
		return firstDayOfMonth;
	}

	/**
	 * 获得该月最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

	/**
	 * long毫秒格式化为日期时间数字型表示 如：1525842605000 --》201805091310
	 * 
	 * @param seconds
	 * @param format
	 * @return
	 */
	public static long longTimeSecondsYMDHMS(long seconds, String format) {
		String str = longToString(seconds, format);
		return Long.parseLong(str);
	}

	/**
	 * 时间加法 用法：当前时间推后1小时15分20秒
	 * 
	 * @param dateStart
	 *            开始时间
	 * @param hours
	 *            小时
	 * @param minutes
	 *            分
	 * @param seconds
	 *            秒
	 * @return
	 */
	public static long addTime(Long dateStart, Integer hours, Integer minutes, Integer seconds) {
		long add = 0;
		if (ValidateUtil.isValid(hours)) {
			add = hours * 60 * 60 * 1000;
		}
		if (ValidateUtil.isValid(minutes)) {
			add += minutes * 60 * 1000;
		}
		if (ValidateUtil.isValid(seconds)) {
			add += seconds * 1000;
		}
		if (ValidateUtil.isValid(dateStart)) {
			return new Date(dateStart + add).getTime();
		}
		return 0;
	}


	/**
	 * 从时间long值中获取时分秒
	 * @param times
	 * @param format
	 * @return
	 */
	public static String getHHmmssFromLong(long times, String format) {
		String str = longToString(times, format);
		return str.substring(str.length() - 8, str.length());
	}

	/**
	 * 将时间总秒数转换为HH:mm:ss表示
	 * 
	 * @param dateString
	 * @return
	 */
	public static String secondsToHHmmss(String dateString) {
		int seconds = Integer.parseInt(dateString);
		int temp = 0;
		StringBuffer sb = new StringBuffer();
		temp = seconds / 3600;// 算出多少小时
		sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");// 格式化时间
		temp = seconds % 3600 / 60;
		sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
		temp = seconds % 3600 % 60;
		sb.append((temp < 10) ? "0" + temp : "" + temp);
		return sb.toString();
	}

	/**
	 * 日期毫秒数转换为Date
	 * 
	 * @param msecond
	 * @return
	 */
	public static Date longToDate(long msecond) {
		return new Date(msecond);
	}

	/**
	 * 日期毫秒数转换为Date字符串表示
	 * 
	 * @param msecond
	 * @param format
	 * @return
	 */
	public static String longToString(long msecond, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(msecond);
		return sdf.format(date);
	}

	/**
	 * 
	 * @param strTime
	 * @param formatType
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String strTime, String formatType) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		try {
			return formatter.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 秒转化为天小时分秒字符串
	 * 
	 * @param seconds
	 * @return
	 */
	public static String formatSeconds(long seconds) {
		String timeStr = seconds + "秒";
		if (seconds > 60) {
			long second = seconds % 60;
			long min = seconds / 60;
			timeStr = min + "分" + second + "秒";
			if (min > 60) {
				min = (seconds / 60) % 60;
				long hour = (seconds / 60) / 60;
				timeStr = hour + "小时" + min + "分" + second + "秒";
				if (hour > 24) {
					hour = ((seconds / 60) / 60) % 24;
					long day = (((seconds / 60) / 60) / 24);
					timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
				}
			}
		}
		return timeStr;
	}

	// string类型转换为long类型
	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType) throws ParseException {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (null == date) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// date类型转换为long类型
	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	public static String fixTimeLong(String date) {
		if (date.length() <= 5) {
			return "0" + date;
		}
		return date;
	}

	/**
	 * 字符串日期相加减(按天加减)
	 * @param dateTime
	 * @param n 正数表示增加日期，负数表示减
	 * @param format 格式化显示，
	 * @return
	 */
	public static String addAndSubtractDaysByCalendar(String dateTime, int n, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date currentDate = sdf.parse(dateTime);
			Calendar calstart = Calendar.getInstance();
			calstart.setTime(currentDate);
			calstart.add(Calendar.DAY_OF_WEEK, n);
			return sdf.format(calstart.getTime());
		} catch (Exception e) {
		}
		return null;
	}
}
