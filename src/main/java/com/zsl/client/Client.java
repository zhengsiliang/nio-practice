package com.zsl.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author siliang.zheng
 * Date : 2018/12/12
 * Describle
 */
public class Client {
    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 5000; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    SocketChannel socketChannel = SocketChannel.open();
                    socketChannel.configureBlocking(false);
                    socketChannel.connect(new InetSocketAddress("localhost", 8080));
                    while (!socketChannel.finishConnect()) {
                        Thread.sleep(1000);
                    }
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                    byteBuffer.put("123".getBytes());
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                    while (socketChannel.read(byteBuffer) == 0) {
                        Thread.sleep(1000);
                    }
                    byteBuffer.flip();
                    System.out.println(index);
                    while (byteBuffer.hasRemaining()) {
                        System.out.print((char) byteBuffer.get());
                    }
                    System.out.println();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
