package com.stephen.crawler.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.base.BaseFile;
import com.stephen.crawler.utils.FileUtils;

public class ExcelFile extends BaseFile {
	static String strClassName = ExcelFile.class.getName();
	private Workbook wb;
	private Sheet sheet;
	private OutputStream out;
	private AtomicInteger rownum;
	static Logger logger = Logger.getLogger(strClassName);

	public ExcelFile(Result r) throws IOException {
		super(r);
		if (file.exists()) {
			loadExcelFile();
		} else {
			createExcelFile(r.getFilename());
		}
		out = new FileOutputStream(new File(result.getConfig().getFileAbsolutPath()));
	}

	private void loadExcelFile() throws IOException {
		logger.info("file:" + file.getName() + " exist");
		InputStream in = new FileInputStream(file);
		try {
			wb = WorkbookFactory.create(in);
			sheet = wb.getSheetAt(0);
			System.out.println(sheet.getLastRowNum());
			rownum = new AtomicInteger(sheet.getLastRowNum());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createExcelFile(String filename) {
		if (filename.endsWith(".xls")) {
			wb = new HSSFWorkbook();
		} else if (filename.endsWith(".xlsx")) {
			wb = new XSSFWorkbook();
		}
		sheet = wb.createSheet("sheet1	");
		rownum = new AtomicInteger(0);
	}

	@Override
	public boolean save(Result result) {
		List<?> list = result.getResultList();
		if (list != null) {
			if (rownum.get() == 0) {
				Row row = sheet.createRow(0);
				FileUtils.saveExcelRowHead(row, list.get(0));
			}
			for (Object object : list) {
				Row row = sheet.createRow(rownum.incrementAndGet());
				FileUtils.saveExcelRow(row, object);
			}
			return true;
		}
		return false;
	}

	@Override
	public void close() throws IOException {
		wb.write(out);
		out.close();
	}
}
