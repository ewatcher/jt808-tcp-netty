package com.tuocheng.jt808.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateUtil {

	/**
	 * 校验时间格式有效性
	 * 
	 * @param str
	 * @return
	 */
	public static boolean valideDateString(String str, String format) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {// 设置lenient为false.
				// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			sdf.setLenient(false);
			sdf.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 判断字符串有效性
	 */
	public static boolean isValid(String src) {
		if (null==src  || "".equals(src.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断集合的有效性
	 */
	public static boolean isValid(Collection col) {
		if (null==col  || col.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串数组的有效性
	 * 
	 * @param arrays
	 * @return
	 */
	public static boolean isValid(Object[] arrays) {
		if (null==arrays  || arrays.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串数组的有效性
	 * 
	 * @param arrays
	 * @return
	 */
	public static boolean isValidListObject(List obj) {
		if (null!=obj  && obj.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断整型对象有效性
	 * 
	 * @param arrays
	 * @return
	 */
	public static boolean isValid(Integer entry) {
		if (null==entry  || entry < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断浮点型对象有效性
	 * 
	 * @param arrays
	 * @return
	 */
	public static boolean isValid(Double entry) {
		if (null==entry  || entry <= 0) {
			return false;
		}
		return true;
	}

	public static boolean isValid(Long entry) {
		if (null==entry  || entry <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断浮点型对象有效性
	 * 
	 * @param arrays
	 * @return
	 */
	public static boolean isValid(Date entry) {
		if (null==entry ) {
			return false;
		}
		return true;
	}

	/**
	 * 判断浮点型对象有效性
	 * 
	 * @param arrays
	 * @return
	 */
	public static boolean isValid(Map entry) {
		if (null==entry  || entry.size() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 根据身份证的号码算出当前身份证持有者的性别和出生日期,年龄 18位身份证
	 * 
	 * @param CardCode
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getCarInfo(String CardCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String year = CardCode.substring(6).substring(0, 4);// 得到年份
		String yue = CardCode.substring(10).substring(0, 2);// 得到月份
		String day = CardCode.substring(12).substring(0, 2);// 得到日
		Integer sex;
		if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
			sex = 0;
		} else {
			sex = 1;
		}
		Date date = new Date();// 得到当前的系统时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fyear = format.format(date).substring(0, 4);// 当前年份
		String fyue = format.format(date).substring(5, 7);// 月份
		// String fday=format.format(date).substring(8,10);
		int age = 0;
		if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
			age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
		} else {// 当前用户还没过生
			age = Integer.parseInt(fyear) - Integer.parseInt(year);
		}
		map.put("sex", sex);
		map.put("age", age);
		map.put("birthday", year + "-" + yue + "-" + day);
		return map;
	}

	public static String getOneYearLater(String datestring, String formatString) {
		if (!isValid(datestring) || !isValid(formatString)) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		Date todayStart;
		try {
			todayStart = formatter.parse(datestring);
			long afterTime = (todayStart.getTime() / 1000) + 60 * 60 * 24 * 364;
			todayStart.setTime(afterTime * 1000);
			return formatter.format(todayStart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * 将给定日期增加或者减少num天，返回给定日期字符串时间加1个小时，保险一下整点生效
	 * 
	 * @param num
	 * @param newDate
	 * @param formatter
	 * @return
	 */
	public static String plusDay(int num, String newDate, String formatter) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatter);
			SimpleDateFormat sdfRet = new SimpleDateFormat("yyyy-MM-dd HH");
			Date currentDate = sdf.parse(newDate);
			Calendar ca = Calendar.getInstance();
			ca.setTime(currentDate);
			ca.add(5, num);
			ca.add(Calendar.HOUR_OF_DAY, 1);
			ca.add(Calendar.MINUTE, 10);
			return sdfRet.format(ca.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将给定日期增加或者减少num天，返回给定日期字符串时间加1个小时，保险一下整点生效
	 * 
	 * @param num
	 * @param newDate
	 * @param formatter
	 * @return
	 */
	public static String pushDay(int num, String newDate, String formatter) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatter);
			SimpleDateFormat sdfRet = new SimpleDateFormat("yyyy-MM-dd HH");
			Date currentDate = sdf.parse(newDate);
			Calendar ca = Calendar.getInstance();
			ca.setTime(currentDate);
			ca.add(5, num);
			ca.add(Calendar.HOUR_OF_DAY, 0);
			ca.add(Calendar.MINUTE, 3);
			return sdfRet.format(ca.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 根据指定格式化方式，校验输入时间日期字符串与格式化方式一致
	 * 
	 * @param str
	 * @param formatString
	 * @return
	 */
	public static boolean isValidDate(String str, String formatString) {
		// 定义返回对象
		boolean convertSuccess = true;
		// 时间格式化方式
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str.trim());
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/**
	 * 判断给定的日期是否大于当前时间，在于当前时间返回true,否则返回false<br/>
	 * @param date
	 * @param formatType
	 * @return
	 */
	public static boolean getValidGrateThenNow(String date,String formatType){
		if(isValidDate(date, formatType)){
			try {
				long temp1=MyDateUtils.stringToLong(date, formatType);
				long temp2=System.currentTimeMillis();
				if(temp1>temp2){
					return true;
				}
			} catch (ParseException e) {
			}
		}
		return false;
	}
	
}
