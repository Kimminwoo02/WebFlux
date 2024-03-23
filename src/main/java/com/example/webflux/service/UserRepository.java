package com.example.webflux.service;

import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private final Map<String, User> userMap;

    public UserRepository(){
        var user = new  User();
    }

    @SneakyThrows
    public Optional<User> findById(){
        Thread.sleep(1000);
        var user = userMap.get(userId);
        return Optional.ofNullable(user);
    }
}
