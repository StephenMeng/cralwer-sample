package com.stephen.crawler.core;

public interface Parser {
	public ParsedResult parseHtml(String html) throws Exception;
}
