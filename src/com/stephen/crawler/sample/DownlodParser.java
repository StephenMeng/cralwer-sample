package com.stephen.crawler.sample;

import com.stephen.crawler.core.ParsedResult;
import com.stephen.crawler.core.Parser;

public class DownlodParser implements Parser {

	@Override
	public final ParsedResult parseHtml(String html) throws Exception {
		ParsedResult result = new ParsedResult();
		setParsedResult(result, html);
		return result;
	}

	public void setParsedResult(ParsedResult result, String html) {
		result.setLists(null);
		result.setReturnObj(null);
	}
}
