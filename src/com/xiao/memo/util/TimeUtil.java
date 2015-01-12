package com.xiao.memo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	public static Date parseToDate(String strTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm");
		Date resultTime = null;
		try {
			resultTime = sdf.parse(strTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultTime;
	}
	
	
	//获取时间倒数
	public static String parseToString(Date date){
		String Cursortime = null;
		Date currentTime = new Date();
		long interval = (date.getTime() - currentTime.getTime()) / 1000;// 秒
		long dayTemp = interval / (24 * 3600);// 天
		long hourTemp = interval % (24 * 3600) / 3600;// 小时
		long minuteTemp = interval % 3600 / 60;// 分钟
		Cursortime = dayTemp + " 天 " + hourTemp + " 时 "
				+ (minuteTemp + 1)+" 分";
		return Cursortime;
	}
	
	//获取天数
	public static long getDays(Date date){
		long days = 0;
		Date currentTime = new Date();
		long interval = (date.getTime() - currentTime.getTime()) / 1000;// 秒
		days = interval / (24 * 3600);// 天
		return days;
	}

}
