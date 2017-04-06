package com.stephen.crawler.core.runnable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.stephen.crawler.core.ParsedResult;
import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.base.UrlConfig;
import com.stephen.crawler.utils.CrawlerUtils;

public class CrawlerRunnable implements Callable<Result> {
	private UrlConfig config;
	private BlockingQueue<Result> bq;
	private int count = 0;
	private int sleepTime;

	public CrawlerRunnable(UrlConfig config, BlockingQueue<Result> blockingQueue) {
		this(config, blockingQueue, 5000);
	}

	public CrawlerRunnable(UrlConfig config, BlockingQueue<Result> blockingQueue, int time) {
		this.config = config;
		this.bq = blockingQueue;
		this.sleepTime = time;
	}

	@Override
	public Result call() throws Exception {
		// TODO Auto-generated method stub
		count++;
		ParsedResult pr = null;
		Result result = null;
		try {
			result = CrawlerUtils.getResponseResult(this.config);
			if (result.getIn() == null) {
				result = null;
				return repeatCall();
			}
			pr = config.parseHtml(result.getHtml());
			result.setParsedResult(pr);
			bq.put(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Result repeatCall() throws Exception {
		System.out.println("尝试重新第" + count + "次访问");
		if (count > 5) {
			System.out.println("超过5次访问失败...");
			config.getCrawler().saveErrorInfo(config.getUrl());
			return null;
		}
		try {
			Thread.sleep(sleepTime * count * count);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.call();
	}

}
