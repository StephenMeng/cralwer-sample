package com.stephen.crawler.core.file;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.base.BaseFile;

public class HtmlFile extends BaseFile {
	private FileWriter fw;

	public HtmlFile(Result result) throws IOException {
		super(result);
		fw = new FileWriter(file, true);
	}

	@Override
	public boolean save(Result re) {
		System.out.println("save html");
		try {
			String html = IOUtils.toString(re.getIn(), re.getConfig().getCharset());
			fw.write(html + "\r\n");
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
}
