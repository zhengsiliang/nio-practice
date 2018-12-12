package com.zsl.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * @author siliang.zheng
 * Date : 2018/12/12
 * Describle
 */
public class SocketChannelHandler implements Runnable {
    private SocketChannel socketChannel;

    public SocketChannelHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            Selector selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            if (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        System.out.println("a connection was accepted by a ServerSocketChannel.");
                        return;
                    }
                    if (selectionKey.isConnectable()) {
                        System.out.println("a connection was established with a remote server.");
                        return;
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                        int byteRead = 0;
                        do {
                            byteRead = socketChannel.read(byteBuffer);
                        } while (byteRead > 0);
                        byteBuffer.flip();
                        while (byteBuffer.hasRemaining()) {
                            System.out.print((char) byteBuffer.get());
                        }
                        byteBuffer.clear();
                        byteBuffer.put("response".getBytes());
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        return;
                    }
                    if (selectionKey.isWritable()) {
                        System.out.println("a connection was accepted by a ServerSocketChannel.");
                        return;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
