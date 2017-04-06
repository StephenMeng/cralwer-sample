package com.stephen.crawler.juxiaohong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.stephen.crawler.core.Crawler;
import com.stephen.crawler.core.FileType;
import com.stephen.crawler.core.GlobalConfig;
import com.stephen.crawler.core.base.BaseCrawler;
import com.stephen.crawler.core.base.UrlConfig;

public class FaGuiDetailCrawler extends BaseCrawler implements Crawler {
	public FaGuiDetailCrawler() throws IOException {
		super();
	}

	@Override
	public void init(GlobalConfig config) {
		// TODO Auto-generated method stub
		config.setRequestMethod(GlobalConfig.POST);
		// config.setPostMethod(BaseUrlConfig.STRING_ENTITY);

		config.setUrl("http://www.pkulaw.cn/doSearch.ashx");
		config.setCharset("GBK");
		Map<String, String> headers = new HashMap<>();
		headers.put("Cookie",
				"FWinCookie=1; ASP.NET_SessionId=ovmub3l3ogsroloq05ry5mgi; CheckIPAuto=0; CheckIPDate=2017-03-31 09:37:29; fiqwx5jfn3cld5ihwodh0nrhisIPlogin=1; Hm_lvt_58c470ff9657d300e66c7f33590e53a8=1490923982,1490924232; Hm_lpvt_58c470ff9657d300e66c7f33590e53a8=1490924258; Hm_lvt_8266968662c086f34b2a3e2ae9014bf8=1490923982,1490924232; Hm_lpvt_8266968662c086f34b2a3e2ae9014bf8=1490924258; CookieId=fiqwx5jfn3cld5ihwodh0nrh; User_User=%c4%cf%be%a9%b4%f3%d1%a7; Catalog_Search=((%b7%a8%b9%e6%b1%ea%cc%e2%3d%25%b4%f3%ca%fd%be%dd%25)+and+%b7%a2%b2%bc%c8%d5%c6%da%3e%3d20100101+and+%b7%a2%b2%bc%c8%d5%c6%da%3c%3d20170330)+and+%d0%a7%c1%a6%bc%b6%b1%f0%3dXP08");
		config.setHeaders(headers);
		config.setSaveDir("c:/users/stephen/desktop/ju");

	}
}
