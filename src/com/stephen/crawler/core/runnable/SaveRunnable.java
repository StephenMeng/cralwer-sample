package com.stephen.crawler.core.runnable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.stephen.crawler.core.Crawler;
import com.stephen.crawler.core.FileType;
import com.stephen.crawler.core.Result;

public class SaveRunnable implements Runnable {
	private BlockingQueue<Result> bq;
	private Crawler crawler;
	FileType fileType = null;
	List<FileType> fileTypes = null;
	private CountDownLatch latch;

	public SaveRunnable(BlockingQueue<Result> blockingQueue, Crawler c, CountDownLatch l) {
		this.bq = blockingQueue;
		this.crawler = c;
		this.latch = l;
	}

	@Override
	public void run() {
		System.out.println("*************存储线程:" + Thread.currentThread().getName() + "开始工作*************");
		while (save()) {}
	}

	private boolean save() {
		try {
			if (checkIfOver()) {
				System.out.println("*************存储线程:" + Thread.currentThread().getName() + " 爬取完成**************");
				latch.countDown();
				return false;
			}
			if (bq.size() > 0) {
				takeFromQuene();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void takeFromQuene() throws InterruptedException, IOException {
		Result result = null;
		synchronized (bq) {
			if (bq.size() > 0) {
				result = bq.poll(3, TimeUnit.SECONDS);
				fileType = result.getFile();
			}
		}
		if (result != null) {
			fileType.save(result);
		}
	}

	private boolean checkIfOver() {
		return crawler.isOver() && bq.size() == 0;
	}

}
