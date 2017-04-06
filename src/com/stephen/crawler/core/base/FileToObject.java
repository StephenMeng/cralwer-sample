package com.stephen.crawler.core.base;

public class FileToObject {
	private String filename;
	private Object object;

	public FileToObject() {
		// TODO Auto-generated constructor stub
	}

	public FileToObject(String filename, Object object) {
		super();
		this.filename = filename;
		this.object = object;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
