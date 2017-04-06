package com.stephen.crawler.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.stephen.crawler.core.GlobalConfig;
import com.stephen.crawler.core.base.BaseCrawler;
import com.stephen.crawler.entity.Info;
import com.stephen.crawler.sample.DefaultParser;

public class CrawlerTest {
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		BaseCrawler crawler = new TestC();

		String url = "http://blog.csdn.net/chenchaofuck1/article/details/51606613";
		for (int i = 0; i < 10; i++) {
			Future<?> future = crawler.startCrawl(url, "test.xls", new DefaultParser());
			// try {
			// pr = (ParsedResult) future.get();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (ExecutionException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// System.out.println(pr.getReturnObj());
			// crawler.startCrawl(url, FileFactory.DATA_BASE, new
			// DefaultParser());

		}
		crawler.waitToshutdown();
		long end = System.currentTimeMillis();
		System.out.println("×ÜºÄÊ±£º" + (end - start) / 1000.0 + "s");
	}
}

class TestC extends BaseCrawler {

	public TestC() throws IOException {
		super(10, 1);
	}

	@Override
	public void init(GlobalConfig config) {
		Map<String, String> headers = new HashMap<>();
		config.setMybatisConfPath("conf.xml");
		config.setOrmCls(Info.class);
		config.setSaveDir("c:/users/stephen/desktop/crawler");
	}
}