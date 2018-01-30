package com.github.lightvelocity.j00.io;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
	
	private String method;
	private String url;
	private String version;
	
	private List<String> headers;
	
	private String content;

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

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	
	public void addHeader(String header) {
		if (headers == null) headers = new ArrayList<String>();
		headers.add(header);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
