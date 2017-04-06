package com.stephen.crawler.core;

import java.util.concurrent.Future;

import com.stephen.crawler.core.base.UrlConfig;

public interface Crawler {

	public GlobalConfig getGlobalConfig();

	public Future<?> startCrawl(UrlConfig config);

	public boolean isOver();

	public void waitToshutdown();

	public void saveErrorInfo(Object object);

	public void crawError();
}
