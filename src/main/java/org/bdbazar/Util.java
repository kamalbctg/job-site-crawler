package org.bdbazar;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Util {
	private static Map<String,String> weekDaysInbangla = new HashMap<String,String>();
	private static Map<String,String> monthInbangla = new HashMap<String,String>();
	private static Map<String,String> numberInBangla = new HashMap<String,String>();
	private static SimpleDateFormat fmt = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
	
	static{
		weekDaysInbangla.put(new String("সোমবার".getBytes(),Charset.forName("UTF-8")),"Monday");		
		weekDaysInbangla.put(new String("মঙ্গলবার".getBytes(),Charset.forName("UTF-8")),"Tuesday");
		weekDaysInbangla.put(new String("বুধবার".getBytes(),Charset.forName("UTF-8")),"Wednesday");
		weekDaysInbangla.put(new String("বহস্পতিবার".getBytes(),Charset.forName("UTF-8")),"Thursday");
		weekDaysInbangla.put(new String("শুক্রবার".getBytes(),Charset.forName("UTF-8")),"Monday");
		weekDaysInbangla.put(new String("শনিবার".getBytes(),Charset.forName("UTF-8")),"Saturday");
		weekDaysInbangla.put(new String("রবিবার".getBytes(),Charset.forName("UTF-8")),"Sunday");
		
		monthInbangla.put(new String("জানুয়ারী".getBytes(),Charset.forName("UTF-8")),"January");
		monthInbangla.put(new String("ফেব্রুয়ারি".getBytes(),Charset.forName("UTF-8")),"February");
		monthInbangla.put(new String("মার্চ".getBytes(),Charset.forName("UTF-8")),"March");
		monthInbangla.put(new String("এপ্রিল".getBytes(),Charset.forName("UTF-8")),"April");
		monthInbangla.put(new String("মে".getBytes(),Charset.forName("UTF-8")),"May");
		monthInbangla.put(new String("জুন".getBytes(),Charset.forName("UTF-8")),"June");
		monthInbangla.put(new String("জুলাই".getBytes(),Charset.forName("UTF-8")),"July");
		monthInbangla.put(new String("আগস্ট".getBytes(),Charset.forName("UTF-8")),"August");
		monthInbangla.put(new String("সেপ্টেম্বর".getBytes(),Charset.forName("UTF-8")),"September");
		monthInbangla.put(new String("অক্টোবর".getBytes(),Charset.forName("UTF-8")),"October");
		monthInbangla.put(new String("নভেম্বর".getBytes(),Charset.forName("UTF-8")),"November");
		monthInbangla.put(new String("ডিসেম্বর".getBytes(),Charset.forName("UTF-8")),"December");
		
		numberInBangla.put(new String("০".getBytes(),Charset.forName("UTF-8")), "0");
		numberInBangla.put(new String("১".getBytes(),Charset.forName("UTF-8")), "1");		
		numberInBangla.put(new String("২".getBytes(),Charset.forName("UTF-8")), "2");
		numberInBangla.put(new String("৩".getBytes(),Charset.forName("UTF-8")), "3");
		numberInBangla.put(new String("৪".getBytes(),Charset.forName("UTF-8")), "4");
		numberInBangla.put(new String("৫".getBytes(),Charset.forName("UTF-8")), "5");
		numberInBangla.put(new String("৬".getBytes(),Charset.forName("UTF-8")), "6");
		numberInBangla.put(new String("৭".getBytes(),Charset.forName("UTF-8")), "7");
		numberInBangla.put(new String("৮".getBytes(),Charset.forName("UTF-8")), "8");
		numberInBangla.put(new String("৯".getBytes(),Charset.forName("UTF-8")), "9");
		
	}
	
	public static String convertBDdateToDate(String str) throws ParseException {
		
		for(String key : weekDaysInbangla.keySet()){
			str = str.replaceAll(key, weekDaysInbangla.get(key));
		}
		
		for(String key : monthInbangla.keySet()){
			str = str.replaceAll(key, monthInbangla.get(key));
		}
		
		for(String key : numberInBangla.keySet()){
			str = str.replaceAll(key, numberInBangla.get(key));
		}	
		
		return str;
	}
	
	public static java.sql.Date convertDate(Date date){
		if(date == null){
			return null;
		}
		return new java.sql.Date(date.getTime());
	}
}
