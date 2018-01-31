package com.github.lightvelocity.common;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	
	private static final String CRLF = "\r\n";
	
	private String method;
	private String url;
	private String version;
	
	private Map<String, String> headers;
	private long contentLength;
	
	private String body;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	public void addHeader(String header) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		String[] entry = header.split(":");
		String key = entry[0].trim();
		String value = entry[1].trim();
		headers.put(key, value);
		
		if (key.equalsIgnoreCase("Content-Length")) {
			this.contentLength = Long.parseLong(value);
		}
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getContentLength() {
		return contentLength;
	}
	
	public static String generateHttpRequest(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("GET " + url + " HTTP/1.1" + CRLF);
		sb.append(CRLF);
		return sb.toString();
	}
	
}
