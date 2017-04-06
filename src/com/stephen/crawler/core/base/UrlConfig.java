package com.stephen.crawler.core.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.stephen.crawler.core.Crawler;
import com.stephen.crawler.core.FileType;
import com.stephen.crawler.core.GlobalConfig;
import com.stephen.crawler.core.ParsedResult;
import com.stephen.crawler.core.Parser;
import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.file.FileFactory;

public class UrlConfig {

	private int fileType = FileFactory.LOCAL_FILE;

	private String filename = null;
	private String fileAbsolutPath = null;
	private String url = null;
	private String html;
	private List<Object> results;
	private GlobalConfig crawlerConfig = null;
	private HttpEntity postEntity = null;
	private Map<String, String> map = null;
	private Parser parser;

	public UrlConfig(GlobalConfig b, Parser parser) {
		this(b, "defualt.txt", parser);
	}

	public UrlConfig(GlobalConfig b, String filename, Parser parser) {
		this(b, null, filename, parser);

	}

	public UrlConfig(GlobalConfig crawlerConfig, String url, String filename, Parser parser) {
		this(crawlerConfig, url, null, filename, parser);
	}

	public UrlConfig(GlobalConfig crawlerConfig, String u, String entity, String f, Parser parser) {
		this.crawlerConfig = crawlerConfig;
		this.url = u;
		this.parser = parser;
		if (crawlerConfig.getRequestMethod() == GlobalConfig.POST && entity != null) {
			try {
				List<NameValuePair> parameters = parsePostParameters(entity);
				this.postEntity = new UrlEncodedFormEntity(parameters);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		results = new ArrayList<>();
		this.filename = f;
		this.fileAbsolutPath = getSaveDir() + "/" + filename;
	}

	private List<NameValuePair> parsePostParameters(String entity) {
		List<NameValuePair> parameters = new ArrayList<>();
		int index = entity.indexOf("&");
		do {
			if (index != 0 && index != -1) {
				String key = entity.substring(0, entity.indexOf("="));
				String value = entity.substring(entity.indexOf("=") + 1, index);
				parameters.add(new BasicNameValuePair(key, value));
			}
			entity = entity.substring(index + 1);
			index = entity.indexOf("&");
		} while (index != -1);
		return parameters;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public void setcrawlerConfig(GlobalConfig crawlerConfig) {
		this.crawlerConfig = crawlerConfig;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void setStringEntity(StringEntity stringEntity) {
		this.postEntity = stringEntity;
	}

	public GlobalConfig getcrawlerConfig() {
		return crawlerConfig;
	}

	public boolean isGZIP() {
		return crawlerConfig.isGZIP;
	}

	public String getCharset() {
		return crawlerConfig.getCharset();
	}

	public Map<String, String> getHeaders() {
		return crawlerConfig.getHeaders();
	}

	public RequestConfig getRequestConfig() {
		// TODO Auto-generated method stub
		return crawlerConfig.getRequestConfig();
	}

	public HttpHost getProxy() {
		// TODO Auto-generated method stub
		return crawlerConfig.getProxy();
	}

	public int getRequestMethod() {
		// TODO Auto-generated method stub
		return crawlerConfig.getRequestMethod();
	}

	public String getUrl() {
		// TODO Auto-generated method stub
		if (url != null) {
			return url;
		}
		return crawlerConfig.getUrl();
	}

	public HttpEntity getPostEntity() {
		// TODO Auto-generated method stub
		return postEntity;
	}

	public String getSaveDir() {
		return crawlerConfig.getSaveDir();
	}

	public Crawler getCrawler() {
		return crawlerConfig.getCrawler();
	}

	public String getFileAbsolutPath() {
		return fileAbsolutPath;
	}

	public ParsedResult parseHtml(String html) throws Exception {
		return parser.parseHtml(html);
	}

	public Class<?> getOrmCls() {
		return crawlerConfig.getOrmCls();
	}

	public String getMybatisConfPath() {
		return crawlerConfig.getMybatisConfPath();
	}

	public Map<String, String> getMap() {
		return map;
	}

	public GlobalConfig getCrawlerConfig() {
		return crawlerConfig;
	}

	public Parser getParser() {
		return parser;
	}

	public void setCrawlerConfig(GlobalConfig crawlerConfig) {
		this.crawlerConfig = crawlerConfig;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public void setPostEntity(String entity) {
		if (crawlerConfig.getRequestMethod() == GlobalConfig.POST && entity != null) {
			try {
				List<NameValuePair> parameters = parsePostParameters(entity);
				this.postEntity = new UrlEncodedFormEntity(parameters);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPostEntity(HttpEntity entity) {
		this.postEntity = entity;
	}

	public FileType getFile(Result result) {
		for (FileType fileType : crawlerConfig.getFiles()) {
			if (fileType.getFilename().equals(getFilename())) {
				return fileType;
			}
		}
		System.out.println(getFilename());
		FileType fileType = null;
		try {
			fileType = FileFactory.createFile(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (fileType != null) {
			crawlerConfig.addFile(fileType);
		}
		if (crawlerConfig.getFiles().size() > GlobalConfig.DEFAULT_OPEN_FILE_NUM) {
			crawlerConfig.removeFile(0);
		}

		return fileType;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public void setResult(List<Object> results) {
		// TODO Auto-generated method stub
		this.results = results;
	}

	public List<Object> getResults() {
		return results;
	}
}
