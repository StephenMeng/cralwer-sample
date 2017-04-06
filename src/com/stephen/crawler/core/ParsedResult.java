package com.stephen.crawler.core;

import java.util.List;

public class ParsedResult {
	private Object returnObj;
	private List<Object> lists;

	public List<Object> getLists() {
		return lists;
	}

	public Object getReturnObj() {
		return returnObj;
	}

	public void setLists(List<Object> lists) {
		this.lists = lists;
	}

	public void setReturnObj(Object returnObj) {
		this.returnObj = returnObj;
	}
}
