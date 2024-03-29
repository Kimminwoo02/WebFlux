package com.example.webflux.reactive;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleHotPublisherMain {
    @SneakyThrows
    public static void main(String[] args){
        var publisher = new SimpleHotPublisher();
        Thread.sleep(1000);
        publisher.shutdown();
    }
}
