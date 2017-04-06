package com.stephen.crawler.core.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.base.BaseFile;

public class TxtFile extends BaseFile {
	private FileWriter fw;

	public TxtFile(String string) {
		file = new File(string);
		try {
			fw = new FileWriter(file, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TxtFile(Result result) throws IOException {
		super(result);
		fw = new FileWriter(file, true);
	}

	public boolean saveError(String line) {
		try {
			fw.write(line + "\r\n");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean save(Result re) {
		try {
			List<?> list = result.getResultList();
			for (Object object : list) {
				fw.write(object.toString() + "\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void close() {
		try {
			if (fw != null) {
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean save(String results) {
		try {
			fw.write(results + "\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
