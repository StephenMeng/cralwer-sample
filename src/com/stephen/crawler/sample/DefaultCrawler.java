package com.stephen.crawler.sample;

import java.io.IOException;

import com.stephen.crawler.core.GlobalConfig;
import com.stephen.crawler.core.base.BaseCrawler;

public class DefaultCrawler extends BaseCrawler {

	public DefaultCrawler(String path) throws IOException {
		super(path);
	}

	public DefaultCrawler(int i, int j, String string) {
		// TODO Auto-generated constructor stub
		super(i, j, string);
	}

	@Override
	public void init(GlobalConfig config) {
		config.setCharset("UTF8");
	}

}
