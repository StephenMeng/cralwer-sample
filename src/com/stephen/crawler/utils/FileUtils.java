package com.stephen.crawler.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class FileUtils {
	public static final int TYPE_XLS = 0;
	public static final int TYPE_XLSX = 1;
	public static final int TYPE_TXT = 2;
	public static final int TYPE_JPG = 3;
	public static final int TYPE_PNG = 4;
	public static final int TYPE_BMP = 5;
	public static final int TYPE_DOC = 6;
	public static final int TYPE_PDF = 7;
	public static final int TYPE_ZIP = 8;
	public static final int TYPE_OTHER = 9;

	public static void findFiles(File f, List<String> uris) {
		if (f.isDirectory()) {
			for (File file : f.listFiles()) {
				findFiles(file, uris);
			}
		} else {
			String path = f.getAbsolutePath();
			uris.add(path);
		}
	}

	public static void saveExcelRow(Row row, Object obj) {
		if (obj instanceof String) {
			row.createCell(0).setCellValue(obj.toString());
			return;
		}

		Cell cell = null;
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			cell = row.createCell(i);
			try {
				cell.setCellValue(fields[i].get(obj).toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveExcelRowHead(Row row, Object obj) {
		// TODO Auto-generated method stub
		Cell cell = null;
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true);
			}
			cell = row.createCell(i);
			try {
				cell.setCellValue(fields[i].getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

}
