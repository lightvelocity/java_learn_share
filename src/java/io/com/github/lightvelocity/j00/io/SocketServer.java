package com.github.lightvelocity.j00.io;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

/**
 * 阻塞式web服务器
 * @author hzlimaozhi
 *
 */
public class SocketServer implements Runnable {
	
	private ServerSocket server;
	
	private int port = 80;
	
	public SocketServer() {}
	public SocketServer(int port) { this.port = port; }
	
	@Override
	public void run() {
		boolean stop = false;
		try {
			server = new ServerSocket(port);
			System.out.println("服务器启动");
			
			while (!stop) {
				Socket socket = server.accept();
				new Thread(new SocketHandler(socket)).start();
			}
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(server);
			server = null;
			System.out.println("服务器关闭");
		}
	}
	
	public static void main(String[] args) {
		new Thread(new SocketServer()).start();
	}
		
}
