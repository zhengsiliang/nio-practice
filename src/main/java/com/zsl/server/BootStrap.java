package com.zsl.server;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author siliang.zheng
 * Date : 2018/12/12
 * Describle
 */
public class BootStrap {
    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(10);
        ConcurrentLinkedQueue<SocketChannel> socketChannelQueue = new ConcurrentLinkedQueue();
        Server server = new Server(socketChannelQueue);
        executor.execute(server);

        while (true) {
            SocketChannel socketChannel = socketChannelQueue.poll();
            if (socketChannel != null) {
                SocketChannelHandler socketChannelHandler = new SocketChannelHandler(socketChannel);
                executor.execute(socketChannelHandler);
            }
        }


    }
}
