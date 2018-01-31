package com.github.lightvelocity.j00.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.github.lightvelocity.common.HttpRequest;
import com.github.lightvelocity.common.HttpResponse;

/**
 * 请求处理器
 * @author hzlimaozhi
 *
 */
public class SocketClientHandler implements Runnable {
	
	private Socket socket;
	
	public SocketClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			out = new PrintWriter(socket.getOutputStream());
			
			while (true) { // 处理单个请求
				
				HttpRequest request = HttpRequestParser.parse(in);
				if (request == null) break;
				
				HttpResponse response = new HttpResponse();
				
				String url = request.getUrl();
				if (url.startsWith("/hello")) {
					response.setContent("Hello, World");
					
				} else if (url.startsWith("/time")) {
					response.setContent(new Date().toString());
					
				} else {
					response.setNotFound();
				}
				
				out.write(response.toString());
				out.flush();
				
				break; // 中断连接
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(socket);
			socket = null;
		}
	}
	
}
