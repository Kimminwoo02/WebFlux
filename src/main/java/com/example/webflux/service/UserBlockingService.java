package com.example.webflux.service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
@RequiredArgsConstructor
public class UserBlockingService {
    private final UserRepository userRepository;
    public Optional<User> getUserById(String id){
        return userRepository.findById(id)
                .map(user -> {
                    var image = imageRepository.findById(user.getProfileImageId())
                            .map(imageEntity -> {
                                return new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
                            });

                });

    }
}
