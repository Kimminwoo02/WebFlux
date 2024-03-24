package com.example.webflux.future.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleEntity {
    private Long id;
    private String content;
    private String userId;

    public ArticleEntity(String number, String 소식1, String 내용1, String number1) {
    }
}
