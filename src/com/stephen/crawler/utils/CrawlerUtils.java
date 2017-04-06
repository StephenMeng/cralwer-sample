package com.stephen.crawler.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.stephen.crawler.core.GlobalConfig;
import com.stephen.crawler.core.Result;
import com.stephen.crawler.core.base.UrlConfig;

public class CrawlerUtils {

	public static String getResponse(UrlConfig urlConfig) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = initRequest(urlConfig, client);
		try {
			String html = getResponseBody(urlConfig, client, request);
			return html;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private static String getResponseBody(UrlConfig urlConfig, CloseableHttpClient client,
			HttpRequestBase request) throws IOException, ClientProtocolException, UnsupportedEncodingException {
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		InputStream in = null;
		if (urlConfig.isGZIP()) {
			in = new GZIPInputStream(entity.getContent());
		} else {
			in = entity.getContent();
		}
		String html = null;
		html = IOUtils.toString(in);
		return html;
	}

	private static HttpRequestBase initRequest(UrlConfig urlConfig, CloseableHttpClient client) {
		HttpRequestBase request;
		if (urlConfig.getProxy() != null) {
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, urlConfig.getProxy());
		}
		if (urlConfig.getRequestMethod() == GlobalConfig.GET) {
//			 System.out.println("Get");
			request = new HttpGet(urlConfig.getUrl());
		} else {
			request = new HttpPost(urlConfig.getUrl());
			if (urlConfig.getPostEntity() == null) {
				System.out.println("Alert:Post Entity is null!");
			} else {
				((HttpPost) request).setEntity(urlConfig.getPostEntity());
			}
		}
		if (urlConfig.getRequestConfig() != null) {
			request.setConfig(urlConfig.getRequestConfig());
		}
		if (urlConfig.getHeaders().size() > 0) {
			for (Map.Entry<String, String> map : urlConfig.getHeaders().entrySet()) {
				request.addHeader(new BasicHeader(map.getKey(), map.getValue()));
			}
		}
		return request;
	}

	public static InputStream getResponseStream(UrlConfig config) throws ClientProtocolException, IOException {
		HttpEntity entity = getResponseEntity(config);
		InputStream in = null;
		if (config.isGZIP()) {
			in = new GZIPInputStream(entity.getContent());
		} else {
			in = entity.getContent();
		}
		return in;
	}

	public static HttpEntity getResponseEntity(UrlConfig config) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		HttpContext httpContext = new BasicHttpContext();
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = initRequest(config, client);
		HttpResponse response = client.execute(request, httpContext);
		HttpEntity entity = response.getEntity();
		// for (org.apache.http.Header header : response.getAllHeaders()) {
		// System.out.println(header.getName() + "\t" + header.getValue());
		// }
		// HttpHost currentHost = (HttpHost)
		// httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		// HttpUriRequest req = (HttpUriRequest) httpContext
		// .getAttribute(ExecutionContext.HTTP_REQUEST);
		// System.out.println(req.getURI());
		return entity;
	}

	public static Result getResponseResult(UrlConfig config) throws ClientProtocolException, IOException {
		Result result = new Result();
		HttpContext httpContext = new BasicHttpContext();
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = initRequest(config, client);
		HttpResponse response = client.execute(request, httpContext);
		HttpEntity entity = response.getEntity();

		InputStream in = null;
		if (config.isGZIP()) {
			in = new GZIPInputStream(entity.getContent());
		} else {
			in = entity.getContent();
		}
		result.setHttpContext(httpContext);
		result.setIn(in);
		result.setConfig(config);
		return result;
	}
}
