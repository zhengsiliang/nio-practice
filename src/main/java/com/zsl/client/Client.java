package com.zsl.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author siliang.zheng
 * Date : 2018/12/12
 * Describle
 */
public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        while (!socketChannel.finishConnect()) {
            Thread.sleep(1000);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("123".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
        while (socketChannel.read(byteBuffer)==0){
            Thread.sleep(1000);
        }
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());
        }
    }
}
