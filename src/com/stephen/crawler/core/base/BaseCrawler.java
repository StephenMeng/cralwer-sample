package com.stephen.crawler.core.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

import com.stephen.crawler.core.Crawler;
import com.stephen.crawler.core.GlobalConfig;
import com.stephen.crawler.core.Parser;
import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.file.FileFactory;
import com.stephen.crawler.core.file.TxtFile;
import com.stephen.crawler.core.runnable.CrawlerRunnable;
import com.stephen.crawler.core.runnable.SaveRunnable;
import com.stephen.crawler.sample.DefaultParser;

public abstract class BaseCrawler implements Crawler {

	static String strClassName = BaseCrawler.class.getName();
	static Logger logger = Logger.getLogger(strClassName);

	public static final int STANDART_CRAW_THREAD_NUM = 100;
	public static final int STANDART_SAVE_THREAD_NUM = 1;
	public static final String E_F_NAME = "error.txt";

	private boolean ifOver = false;
	private int crawThreadNum = 0;
	private int saveThreadNum = 1;
	private CountDownLatch savelatch;
	private ExecutorService executor;
	private ExecutorService saveExecutor;
	private BlockingQueue<Result> blockingQueue = new LinkedBlockingQueue<>();

	private TxtFile errorFile;
	private DefaultParser defaultParser;

	protected GlobalConfig crawlerConfig;

	public BaseCrawler() throws IOException {
		this(STANDART_CRAW_THREAD_NUM, STANDART_SAVE_THREAD_NUM, null);
	}

	public BaseCrawler(int ct, int st) throws IOException {
		this(ct, st, null);
	}

	public BaseCrawler(String path) {
		this(STANDART_CRAW_THREAD_NUM, STANDART_SAVE_THREAD_NUM, path);
	}

	public BaseCrawler(int threadnum, int sThreadNum, String path) {
		this.crawlerConfig = new GlobalConfig(this);
		this.crawlerConfig.saveDir = path;
		this.saveThreadNum = sThreadNum;
		this.savelatch = new CountDownLatch(saveThreadNum);
		init(crawlerConfig);
		crawThreadNum = threadnum;
		executor = Executors.newFixedThreadPool(crawThreadNum);
		saveExecutor = Executors.newFixedThreadPool(saveThreadNum);

		errorFile = new TxtFile(E_F_NAME);

		System.out.println(crawlerConfig.getSaveDir());
		System.out.println("存储线程开始工作...文件存储位置：" + crawlerConfig.saveDir);

		for (int i = 0; i < saveThreadNum; i++) {
			saveExecutor.execute(new SaveRunnable(blockingQueue, this, savelatch));
		}
	}

	public Future<?> startCrawl(String url, String filename, Parser parser) {
		return startCrawl(url, null, filename, parser);
	}

	public Future<?> startCrawl(String url, int dataBase, DefaultParser parser) {
		return startCrawl(url, dataBase, null, null, parser);
	}

	public Future<?> startCrawl(String url, String entity, String filename, Parser parser) {
		UrlConfig config = new UrlConfig(crawlerConfig, url, entity, filename, parser);
		return startCrawl(config);
	}

	public Future<?> startCrawl(String url, int type, String entity, String filename, Parser parser) {
		UrlConfig config = new UrlConfig(crawlerConfig, url, entity, filename, parser);
		if (type == FileFactory.DATA_BASE) {
			config.setFileType(type);
			config.setFilename(config.getOrmCls().getName());
		}
		return startCrawl(config);
	}

	public Future<?> startCrawl(UrlConfig config) {
		System.out.println(config.getUrl());
		CrawlerRunnable cr = new CrawlerRunnable(config, blockingQueue);
		Future<?> future = executor.submit(cr);
		return future;
	}

	public Future<?> startCrawl(String html) {
		return null;

	}

	public Future<?> startCrawl(InputStream in) {
		return null;

	}

	public boolean isOver() {
		return ifOver && ((ThreadPoolExecutor) executor).getActiveCount() == 0;
	}

	@Override

	public void waitToshutdown() {
		ifOver = true;
		try {
			savelatch.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		System.out.println("craw executor shutdown...");
		executor.shutdown();
		System.out.println("save executor shutdown...");
		saveExecutor.shutdown();
		System.out.println("file close...");
		crawlerConfig.close();
		errorFile.close();
		System.out.println("所有线程爬取完毕~");
	}

	public void saveErrorInfo(Object object) {
		// errorFile.save((InputStream) object);
	}

	public void crawError() {
		crawError(null);
	}

	public void crawError(String path) {
		File error = null;
		if (path != null) {
			error = new File(path);
		} else {
			error = errorFile.getFile();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(error)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);

				startCrawl(line, "error", defaultParser);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public GlobalConfig getGlobalConfig() {
		// TODO Auto-generated method stub
		return crawlerConfig;
	}

	public void setSaveThreadNum(int saveThreadNum) {
		this.saveThreadNum = saveThreadNum;
	}

	public void setCrawThreadNum(int crawThreadNum) {
		this.crawThreadNum = crawThreadNum;
	}

	public abstract void init(GlobalConfig config);
}
