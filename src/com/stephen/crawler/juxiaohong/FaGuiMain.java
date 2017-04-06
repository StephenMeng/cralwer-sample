package com.stephen.crawler.juxiaohong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.stephen.crawler.core.ParsedResult;
import com.stephen.crawler.core.Parser;
import com.stephen.crawler.core.base.BaseCrawler;

public class FaGuiMain {
	public static void main(String[] args) throws IOException {

		BaseCrawler crawler = new FaGuiCrawler();
		String url = crawler.getGlobalConfig().getUrl();
		Parser p = new FGParser();
		for (int i = 0; i < 5; i++) {

			String entity = "keyword=%E5%A4%A7%E6%95%B0%E6%8D%AE&range=name&Search_Mode=accurate&check_hide_xljb=1&Db=lar&check_gaojijs=1&orderby=%E5%8F%91%E5%B8%83%E6%97%A5%E6%9C%9F&name=%E5%A4%A7%E6%95%B0%E6%8D%AE&fdep_id=&pdep_id=&bfdate=2010-01-01&efdate=2017-03-30&shixiao_id=&xiaoli_id=&sort_id=&hidtrsWhere=4F7EBFEC83D8F5A9A431E5B80A34EA66326CF95A73EB39E0C491D1373F6E8103B88573EA6DD0F1A4160673090C48A885B637A3D2F2F04256F2422628467B4E863742EB4B7BA766F362827C44DFF3CF435D6259801FB4A63988EE3EB7A005996B&&nomap=&clusterwhere=((%25e6%25b3%2595%25e8%25a7%2584%25e6%25a0%2587%25e9%25a2%2598%253d%2525%25e5%25a4%25a7%25e6%2595%25b0%25e6%258d%25ae%2525)%2520and%2520%25e5%258f%2591%25e5%25b8%2583%25e6%2597%25a5%25e6%259c%259f%253e%253d20100101%2520and%2520%25e5%258f%2591%25e5%25b8%2583%25e6%2597%25a5%25e6%259c%259f%253c%253d20170330)%2520and%2520%25e6%2595%2588%25e5%258a%259b%25e7%25ba%25a7%25e5%2588%25ab%253dXP08&aim_page="
					+ i + "&page_count=5&clust_db=lar&menu_item=law&EncodingName=&time=0.006507949775619526";

			crawler.startCrawl(url, entity, "test.txt", p);
		}

	}

}

class FGParser implements Parser {
	@Override
	public ParsedResult parseHtml(String html) throws Exception {
		ParsedResult pr = new ParsedResult();
		List<Object> s = new ArrayList<>();
		Document doc = Jsoup.parse(html);
		Elements elements = null;
		try {
			// main = doc.select("td[class=main-top4]").first();
			elements = doc.select("a[class=main-ljwenzi]");
			for (Element e : elements) {
				System.out.println(e.text());
				s.add(e.attr("href") + "\t" + e.text());
			}
		} catch (Exception e) {
			// System.out.println("没有找到数据");
			throw new Exception();
		}
		pr.setLists(s);
		return pr;
	}
}
