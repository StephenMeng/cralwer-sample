package com.stephen.crawler.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.protocol.HttpContext;

import com.stephen.crawler.core.base.UrlConfig;

public class Result {
	private InputStream in;
	private HttpContext httpContext;
	private UrlConfig config;
	private ParsedResult pr;
	private String html;
	
	public void setHttpContext(HttpContext httpContext) {
		this.httpContext = httpContext;
	}

	public HttpContext getHttpContext() {
		return httpContext;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public InputStream getIn() {
		return in;
	}

	public void setConfig(UrlConfig config) {
		this.config = config;
	}

	public UrlConfig getConfig() {
		return config;
	}

	@SuppressWarnings("deprecation")
	public String getHtml() throws IOException {
		html = IOUtils.toString(in);
		return html;
	}

	public ParsedResult getParsedResult() {
		return pr;
	}

	public void setParsedResult(ParsedResult pr) {
		this.pr = pr;
	}

	public String getSaveDir() {
		// TODO Auto-generated method stub
		return config.getSaveDir();
	}

	public void setFilename(String string) {
		// TODO Auto-generated method stub
		config.setFilename(string);
	}

	public String getFilename() {
		// TODO Auto-generated method stub
		return config.getFilename();
	}

	public List<?> getResultList() {
		// TODO Auto-generated method stub
		return pr.getLists();
	}

	public FileType getFile() {
		return getConfig().getFile(this);
	}
}
