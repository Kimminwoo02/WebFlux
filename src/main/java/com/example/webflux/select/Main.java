package com.example.webflux.select;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Reactor reactor = new Reactor(8080);
        reactor.run();

    }
}
