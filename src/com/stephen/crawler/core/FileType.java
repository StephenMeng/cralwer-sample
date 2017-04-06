package com.stephen.crawler.core;

import java.io.File;
import java.io.IOException;

public interface FileType {

	boolean save(Result result) throws IOException;

	void close() throws IOException;

	String getFilename();

	File getFile();
}
