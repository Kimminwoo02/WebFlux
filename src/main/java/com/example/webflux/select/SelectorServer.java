package com.example.webflux.select;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SelectorServer {
    private static AtomicInteger count = new AtomicInteger(0);

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Start main");
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open();
             Selector selector = Selector.open();
             ) {
                serverSocket.bind(new InetSocketAddress("localhost", 8080));
                serverSocket.configureBlocking(false);
                serverSocket.register(selector, SelectionKey.OP_ACCEPT); // 서버 소켓에서 accpet 준비가 되면 쓰레드에 알려준다.

            while (true) {
                selector.select();
                Iterator<SelectionKey> selecteddKeys = selector.selectedKeys().iterator();

                while(selecteddKeys.hasNext()){
                    SelectionKey key = selecteddKeys.next();
                    selecteddKeys.remove();

                    if(key.isAcceptable()){
                        SocketChannel clientSocket = ((ServerSocketChannel)key.channel()).accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector,SelectionKey.OP_READ);
                    }else if(key.isReadable()){
                        SocketChannel clientSocket = (SocketChannel) key.channel();
                        
                        handleRequest(clientSocket);
                    }
                }


            }


        }
    }


    @SneakyThrows
    private static void handleRequest(SocketChannel clientSocket) {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        clientSocket.read(requestByteBuffer);
        requestByteBuffer.flip();
        String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
        log.info("request : {}", requestBody);

        Thread.sleep(10);

        ByteBuffer wrap = ByteBuffer.wrap("This is server".getBytes());
        clientSocket.write(requestByteBuffer);
        clientSocket.close();

    }

}



