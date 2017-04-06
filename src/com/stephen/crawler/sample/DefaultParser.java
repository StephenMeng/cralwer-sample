package com.stephen.crawler.sample;

import java.util.ArrayList;
import java.util.List;

import com.stephen.crawler.core.ParsedResult;
import com.stephen.crawler.core.Parser;

public class DefaultParser implements Parser {

	@Override
	public ParsedResult parseHtml(String html) throws Exception {
		ParsedResult result = new ParsedResult();
		List<Object> list = new ArrayList<>();
		list.add(html);
		result.setLists(list);
		result.setReturnObj(null);
		return result;
	}

}
