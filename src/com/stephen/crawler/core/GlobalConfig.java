package com.stephen.crawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;

public class GlobalConfig {
	public static int GET = 0;
	public static int POST = 1;
	public static final int DEFAULT_OPEN_FILE_NUM = 10;

	public static String DEFAULT_CHARSET = "UTF-8";

	protected List<FileType> files;
	public boolean isGZIP;
	public String url;
	public Map<String, String> headers = null;
	public HttpEntity postEntity = null;
	public HttpHost proxy = null;
	public RequestConfig requestConfig = null;
	public String charset = null;
	public int requestMethod;
	public String saveDir;
	public Crawler crawler;

	public String mybatisConfPath;
	public Class<?> ormCls;

	public GlobalConfig() {
		this(null);
	}

	public GlobalConfig(Crawler baseCrawler) {
		crawler = baseCrawler;
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).setConnectTimeout(20000)
				.setSocketTimeout(20000).build();
		isGZIP = false;
		headers = new HashMap<>();
		charset = DEFAULT_CHARSET;
		saveDir = "/";
		files = new ArrayList<>();
	}

	public int getGet() {
		return GET;
	}

	public void setGet(int get) {
		GET = get;
	}

	public static int getPost() {
		return POST;
	}

	public static void setPost(int post) {
		POST = post;
	}

	public static String getDefaultCharset() {
		return DEFAULT_CHARSET;
	}

	public boolean isGZIP() {
		return isGZIP;
	}

	public void setGZIP(boolean isGZIP) {
		this.isGZIP = isGZIP;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public HttpEntity getPostEntity() {
		return postEntity;
	}

	public void setPostEntity(HttpEntity postEntity) {
		this.postEntity = postEntity;
	}

	public HttpHost getProxy() {
		return proxy;
	}

	public void setProxy(HttpHost proxy) {
		this.proxy = proxy;
	}

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(int requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	public Crawler getCrawler() {
		return crawler;
	}

	public String getMybatisConfPath() {
		return mybatisConfPath;
	}

	public void setMybatisConfPath(String mybatisConfPath) {
		this.mybatisConfPath = mybatisConfPath;
	}

	public Class<?> getOrmCls() {
		return ormCls;
	}

	public void setOrmCls(Class<?> ormCls) {
		this.ormCls = ormCls;
	}

	public List<FileType> getFiles() {
		return files;
	}

	public void addFile(FileType file) {
		files.add(file);
	}

	public boolean removeFile(int index) {
		try {
			files.get(index).close();
			files.remove(index);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void close() {
		// TODO Auto-generated method stub
		for (FileType fType : files) {
			try {
				fType.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		files.clear();
	}

}
