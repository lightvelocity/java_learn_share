package com.github.lightvelocity.j01.nio.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

import com.github.lightvelocity.common.HttpRequest;
import com.github.lightvelocity.common.HttpRequestDecoder;
import com.github.lightvelocity.common.HttpResponse;

public class BlockSocketChannelServer implements Runnable {

	private ServerSocketChannel server;
	
	private int port = 80;
	
	@Override
	public void run() {
		try {
			server = ServerSocketChannel.open();
			server.bind(new InetSocketAddress(port));
			System.out.println("服务器启动");
			
			while (true) {
				SocketChannel socket = server.accept();
				
				HttpRequestDecoder decoder = new HttpRequestDecoder(socket);
				
				while (true) {
					HttpRequest request = decoder.decode();
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
					
					ByteBuffer responseBuffer = ByteBuffer.wrap(response.toString().getBytes("UTF-8"));
					socket.write(responseBuffer);
					//break; // 只读取一个请求
				}
				
				socket.close(); // 断开连接
				System.out.println("连接断开");
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new Thread(new BlockSocketChannelServer()).start();
	}
}
