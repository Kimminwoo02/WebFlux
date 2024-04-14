package com.example.webflux.select;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Reactor implements Runnable {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ServerSocketChannel serverSocket;

    private final Selector selector;
    private final EventHandler acceptor;

    @SneakyThrows
    public Reactor(int port){
        acceptor = new Acceptor();
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost",port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector,SelectionKey.OP_ACCEPT).attach(acceptor);
    }

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

                        String requestBody = handleRequest(clientSocket);
                        sendResponse(clientSocket, requestBody);
                    }
                }


            }


        }
    }


    @SneakyThrows
    private static String handleRequest(SocketChannel clientSocket) {
        ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
        clientSocket.read(requestByteBuffer);
        requestByteBuffer.flip();
        String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
        log.info("request : {}", requestBody);

        return requestBody;



    }

    private static void sendResponse(SocketChannel clientSocket ,String requestBody) throws InterruptedException {
        CompletableFuture.runAsync(()->{
            try{
                Thread.sleep(10);

                String content = "received" + requestBody;

                ByteBuffer responseByteBuffer = ByteBuffer.wrap(content.getBytes());
                clientSocket.write(responseByteBuffer);
                clientSocket.close();
            }catch (Exception e){
                System.out.println("dd");
            }
        });

    }

    @Override
    public void run() {
        executorService.submit(()->{
            while(true){
                selector.select();
                Iterator<SelectionKey> selectedKeys =selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()){
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();

                    dispatch(key);
                }
            }
        })
    }

    private void dispatch(SelectionKey selectionKey){
        EventHandler attachment = (EventHandler) selectionKey.attachment();

        if(selectionKey.isReadable() || selectionKey.isAcceptable()){
            attachment.handle();
        }
    }

    static class Acceptor implements EventHandler{
        @SneakyThrows
        @Override
        public void handle() {
            SocketChannel clientSocket = serverSocket.accept();
            log.info("client:{}",clientSocket);
            clientSocket.close();
//            new TcpEventHandler(selector, clientSocket);
        }
    }
}



