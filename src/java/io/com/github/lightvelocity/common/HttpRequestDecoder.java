package com.github.lightvelocity.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HttpRequestDecoder {
	
	private SocketChannel channel;
	
	private ByteBuffer buffer;
	
	private byte[] line = new byte[128];
	
	public HttpRequestDecoder(SocketChannel channel) {
		this.channel = channel;
		this.buffer = ByteBuffer.allocate(128);
	}
	
	public HttpRequest decode() throws IOException {
		HttpRequest request = new HttpRequest();
		
		// req line
		{
			String reqLine = readLine();
			if (reqLine == null) return null;
			
			String[] reqs = reqLine.split(" ");
			request.setMethod(reqs[0]);
			request.setUrl(reqs[1]);
			request.setVersion(reqs[2]);
		}
		
		// header
		while(true) {
			String headerLine = readLine();
			if (headerLine == null) return null;
			
			if (headerLine.equals("")) break;
			else request.addHeader(headerLine);
		}
		
		// body
		// FIXME
		
		return request;
	}
	
	private String readLine() throws IOException {
		int count = 0; // 一行已读的字节数
		
		while (true) { // 每次循环是buffer读取完毕
			
			if (count >= 8096) { // 一行读取超过极限则报错
				throw new IllegalStateException("Http请求报文一行已超过8096");
			}
			
			buffer.flip(); // 切换为读模式，循环前必须切换为写模式
			
			int remaining = buffer.remaining();
			while(remaining-- > 0) { // 每次读取1字节
				byte b = buffer.get();
				if (b == 10) { // LF，一行结束
					if (line[count-1] == 13) count--; // CR
					String lineString = new String(line, 0, count, "UTF-8");
					buffer.compact(); // 切换为写模式
					return lineString;
					
				} else {
					if (count == line.length) { // 扩容
						byte[] _line = new byte[count + count/2];
						System.arraycopy(line, 0, _line, 0, count);
						line = _line;
					}
					line[count++] = b;
				}
			}
			
			buffer.compact(); // 循环前切换为写模式
			int read = channel.read(buffer);
			if (read == -1) { // 流结束
				if (count > 0) {
					if (line[count-1] == 13) count--; // CR
					String lineString = new String(line, 0, count, "UTF-8");
					return lineString;
				}
				return null;
			}
		}
	}
	
}
