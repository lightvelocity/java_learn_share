package com.github.lightvelocity.j01.nio.nonblock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.github.lightvelocity.common.HttpRequest;

public class NonBlockSocketChannelClient implements Runnable {
	
	private SocketChannel client;
	
	private int port = 80;
	
	@Override
	public void run() {
		try {
			client = SocketChannel.open();
			client.configureBlocking(false);
			client.connect(new InetSocketAddress(port));
			
			while(!client.finishConnect()) { Thread.sleep(1000); } // 非阻塞
			
			int count = 5;
			while (client.isConnected() && count-- > 0) {
				ByteBuffer requestBuffer = ByteBuffer.wrap(HttpRequest.generateHttpRequest("/time").getBytes("UTF-8"));
				client.write(requestBuffer);
				
				ByteBuffer responseBuffer = ByteBuffer.allocate(128);
				client.read(responseBuffer);
				System.out.println(new String(responseBuffer.array(), "UTF-8"));
				System.out.println("\n\n");
				
				Thread.sleep(3000);
			}
			client.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new Thread(new NonBlockSocketChannelClient()).start();
	}

}
