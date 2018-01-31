package com.github.lightvelocity.j00.io;

import java.io.BufferedReader;
import java.io.IOException;

import com.github.lightvelocity.common.HttpRequest;

public class HttpRequestParser {

	public static HttpRequest parse(BufferedReader reader) {
		try {
			HttpRequest request = new HttpRequest();
			
			{
				String reqLine = reader.readLine();
				if (reqLine == null) return null;
				
				String[] reqs = reqLine.split(" ");
				request.setMethod(reqs[0]);
				request.setUrl(reqs[1]);
				request.setVersion(reqs[2]);
			}
			
			while(true) {
				String headerLine = reader.readLine();
				if (headerLine == null) return null;
				
				if (headerLine.equals("")) break;
				else request.addHeader(headerLine);
			}
			
			if (reader.ready()) {
				request.setBody(reader.readLine());
			}
			
			return request;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
