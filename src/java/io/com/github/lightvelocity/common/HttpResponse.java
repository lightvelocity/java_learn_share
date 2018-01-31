package com.github.lightvelocity.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class HttpResponse {
	
	private String version;
	private Integer statusCode;
	private String statusMsg;
	
	private List<String> headers;
	
	private String content;

	public HttpResponse setVersion(String version) {
		this.version = version;
		return this;
	}

	public HttpResponse setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		switch (statusCode) {
			case 200: statusMsg = "OK"; break;
			case 403: statusMsg = "Forbidden"; break;
			case 404: statusMsg = "Not Found"; break;
		}
		return this;
	}
	public HttpResponse setForbidden() {
		return setStatusCode(403);
	}
	public HttpResponse setNotFound() {
		return setStatusCode(404);
	}

	public HttpResponse addHeader(String header) {
		if (headers == null) headers = new ArrayList<String>();
		headers.add(header);
		return this;
	}

	public HttpResponse setContent(String content) {
		this.content = content;
		return this;
	}
	
	@Override
	public String toString() {
		if (StringUtils.isBlank(version)) setVersion("HTTP/1.1");
		
		if (statusCode == null) setStatusCode(200);
		
		if (CollectionUtils.isEmpty(headers)) {
			headers = new ArrayList<String>();
			headers.add("Date: " + new Date().toString());
			headers.add("Content-Type: text/html;charset=UTF-8");
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(version + " " + statusCode + " " + statusMsg + "\r\n");
		for (String header : headers) {
			sb.append(header + "\r\n");
		}
		sb.append("\r\n");
		if (StringUtils.isNotBlank(content)) sb.append(content);
		return sb.toString();
	}
	
}
