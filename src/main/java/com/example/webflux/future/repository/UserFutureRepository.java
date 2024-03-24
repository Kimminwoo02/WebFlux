package com.example.webflux.future.repository;

import com.example.webflux.future.domain.ArticleEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class UserFutureRepository {
    private static List<ArticleEntity> articleEntityList;

    public void ArticleFutureRepository(){
        articleEntityList = List.of(
                new ArticleEntity("1","소식1","내용1","1234"),
                new ArticleEntity("2","소식2","내용2","1234"),
                new ArticleEntity("3","소식3","내용3","1234")
        );
    }

    @SneakyThrows
    public CompletableFuture<List<ArticleEntity>> findAllByUserId(String userId){
        log.info("ArticleRepository.findByUserId {}" , userId);

        return CompletableFuture.supplyAsync(new Supplier<List<ArticleEntity>>() {
            @Override
            public List<ArticleEntity> get() {
                return articleEntityList.stream()
                        .filter(articleEntity -> articleEntity.getUserId().equals(userId))
                        .collect(Collectors.toList());
            }
        });


    }
}
