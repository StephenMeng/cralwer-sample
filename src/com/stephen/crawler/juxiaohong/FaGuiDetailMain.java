package com.stephen.crawler.juxiaohong;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.stephen.crawler.core.base.BaseCrawler;
import com.stephen.crawler.sample.DefaultParser;

public class FaGuiDetailMain {
	public static void main(String[] args) throws IOException {

		BaseCrawler crawler = new FaGuiDetailCrawler();

		File file = new File(crawler.getGlobalConfig().getSaveDir() + "/test.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		while ((line = reader.readLine()) != null) {
			String id = line.split("\t")[0];
			String url = "http://www.pkulaw.cn/filedownload_form.aspx?Gid=" + id + "&db=lar&paycode=&jiamizi=";
			String name = line.split("\t")[1];
			String entity = "__VIEWSTATE=/wEPDwUKMTcyMjg0NzA3MA8WAh4TVmFsaWRhdGVSZXF1ZXN0TW9kZQIBFgICAw9kFgICAQ9kFgICEQ8PFgIeB1Zpc2libGVoZBYCAgEPEGRkFgFmZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WBgUDdHh0BQNkb2MFA2h0bQUMY2tfa2VlcF96ZHh4BRBja19rZWVwX2FsbF9saW5rBQxja19rZWVwX3hnemycp7N3WfmQiUDb2RY2kIcHe6+1jUwcnLVx/9Nq/c318A==&__VIEWSTATEGENERATOR=25CF0B33&radio=txt&tb_tbwidth=660&but_ok=%C8%B7+%B6%A8&curLibName=lar";
			crawler.startCrawl(url, entity, "/detail/" + name+".txt", new DefaultParser() );
		}

	}

}
