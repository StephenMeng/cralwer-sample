package com.stephen.crawler.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalenderTest {
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());
//		04/02/2017 10:38:35; c_m_expire=2017-04-02 10:38:35
		SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		System.out.println(format.format(calendar.getTime()));
		System.out.println(format2.format(calendar.getTime()));

	}
}
