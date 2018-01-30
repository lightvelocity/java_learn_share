package com.github.lightvelocity.j01.nio.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
	
	public static void main(String[] args) throws IOException {
		RandomAccessFile srcFile = new RandomAccessFile("D://Temp//NioTest.txt", "r");
		RandomAccessFile destFile = new RandomAccessFile("D://Temp//NioTest1.txt", "rw");
		
		FileChannel srcChannel = srcFile.getChannel();
		FileChannel destChannel = destFile.getChannel();
		
		ByteBuffer buffer = ByteBuffer.allocate(256);
		
		int read = srcChannel.read(buffer);
		while (read != -1) {
			buffer.flip(); // 切换为读模式
			
			destChannel.write(buffer);
			
			buffer.compact(); // 切换为写模式
			
			read = srcChannel.read(buffer);
		}
		
		srcFile.close();
		destFile.close();
		System.out.println("复制完毕");
	}

}
