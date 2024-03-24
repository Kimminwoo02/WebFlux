package com.example.webflux.future.repository;

import com.example.webflux.future.domain.ImageEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class ImageFutureRepository {
    private final Map<String, ImageEntity> imageEntityMap;

    public ImageFutureRepository(){
        imageEntityMap = Map.of(
                "",new ImageEntity("image","profileImage","https://ssss.com")
        );
    }

    @SneakyThrows
    public CompletableFuture<Optional<ImageEntity>> findById(String id){
        return CompletableFuture.supplyAsync(()->{
            log.info("ImageRepository.findById {}",id);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            return Optional.ofNullable(imageEntityMap.get(id));
        });



    }

}
