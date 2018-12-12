package com.zsl.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author siliang.zheng
 * Date : 2018/12/12
 * Describle
 */
public class Server implements Runnable {
    private ServerSocketChannel serverSocketChannel;
    private ConcurrentLinkedQueue<SocketChannel> socketChannelQueue = null;

    public Server(ConcurrentLinkedQueue<SocketChannel> socketChannelQueue) {
        this.socketChannelQueue = socketChannelQueue;
    }

    @Override
    public void run() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(8080));
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    socketChannelQueue.add(socketChannel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
