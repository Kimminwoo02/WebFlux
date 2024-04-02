package com.example.webflux.NIO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class JavaIOServer {
    @SneakyThrows
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket()){
            serverSocket.bind(new InetSocketAddress("localhost",8080));

            while(true){
                Socket clientSocket = serverSocket.accept();
                byte[] requestBytes = new byte[1024];
                InputStream in = clientSocket.getInputStream();
                log.info("request : {}", new String(requestBytes).trim());

                OutputStream out = clientSocket.getOutputStream();
                String response = "This is server";
                out.write(response.getBytes());
                out.flush();
            }





        }
    }
}
