package com.example.webflux.reactive;

import lombok.SneakyThrows;

public class SimpleColdPublisherMain {
    @SneakyThrows
    public static void main(String[] args){
        var publisher = new SimpleColdPublisher();
        var subscriber = new SimpleNamedSubscriber<Integer>("subsriber1");
        publisher.subscribe(subscriber);

        Thread.sleep(5000);

        var subscriber2 = new SimpleNamedSubscriber<Integer>("subscriber2");
        publisher.subscribe(subscriber2);
    }


}
