package com.stephen.crawler.core.base;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.stephen.crawler.core.FileType;
import com.stephen.crawler.core.Result;

public abstract class BaseFile implements FileType {
	static String strClassName = BaseFile.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	protected File file;
	protected String suffix;
	protected Result result;
	protected int type;

	public BaseFile() {
	}

	public BaseFile(Result c) throws IOException {
		this.result = c;
		File dir = null;
		String filename = result.getFilename();

		String savepath = result.getSaveDir();
		System.out.println(savepath);
		if (filename.contains("/") || filename.contains("\\")) {
			dir = new File(savepath + "/" + filename.substring(0, filename.lastIndexOf("/")));
		} else {
			dir = new File(savepath);
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		file = new File(savepath + "/" + filename);
	}

	@Override
	public String getFilename() {
		return result.getFilename();
	}

	@Override
	public File getFile() {
		return file;
	}

	public String getSavepath() {
		return result.getSaveDir();
	}

	public void setFileName(String string) {
		result.setFilename(string);
	}
}
