package com.stephen.crawler.utils;

public class StringUtils {
	public static String getSuffix(String uri) {
		return uri.substring(uri.lastIndexOf(".")+1, uri.length());
	}
}
