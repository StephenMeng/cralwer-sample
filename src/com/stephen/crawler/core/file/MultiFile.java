package com.stephen.crawler.core.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.base.BaseFile;

public class MultiFile extends BaseFile {
	OutputStream output;

	public MultiFile(Result result) throws IOException {
		super(result);
		output = new FileOutputStream(file);
	}

	@Override
	public void close() {
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean save(Result re) {
		try {
			IOUtils.write(IOUtils.toByteArray(re.getIn()), output);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
