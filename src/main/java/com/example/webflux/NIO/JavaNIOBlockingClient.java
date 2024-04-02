package com.example.webflux.NIO;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class JavaNIOBlockingClient {
    @SneakyThrows
    public static void main(String[] args) {
        try( Socket socket = new Socket()){
            socket.connect(new InetSocketAddress("localhost",8080));

            OutputStream out = socket.getOutputStream();
            String requestBody = "this is client";
            out.write(requestBody.getBytes());
            out.flush();

            InputStream in = socket.getInputStream();
            byte[] responseBytes = new  byte[1024];
            log.info("result : {}",new String(responseBytes).trim());

        }catch (IOException e){
            throw new RuntimeException(e);

        }
    }
}
