package com.example.webflux.select;

import lombok.RequiredArgsConstructor;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@RequiredArgsConstructor
public class TcpEventHandler implements  EventHandler{
    private final Selector selector;
    private final SocketChannel socketChannel;

}

