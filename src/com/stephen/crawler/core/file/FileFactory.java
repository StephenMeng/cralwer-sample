package com.stephen.crawler.core.file;

import java.io.IOException;

import com.stephen.crawler.core.FileType;
import com.stephen.crawler.core.Result;
import com.stephen.crawler.utils.StringUtils;

public class FileFactory {
	public static final int DATA_BASE = 0;
	public static final int LOCAL_FILE = 1;

	public static FileType createFile(Result result)
			throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException {
		if (result.getConfig().getFileType() == DATA_BASE) {
			return new DatabaseFile(result);
		}
		String filename = result.getFilename();
		String suffix = StringUtils.getSuffix(filename);
		if (suffix == null) {
			throw new IOException("文件缺少后缀名");
		}
		switch (suffix) {
		case "html":
			return new HtmlFile(result);
		case "txt":
			return new TxtFile(result);
		case "xlsx":
		case "xls":
			return new ExcelFile(result);
		default:
			return new MultiFile(result);
		}
	}
}
