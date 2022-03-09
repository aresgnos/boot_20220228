package com.example.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "book4")
public class Book {

    @Id // 기본키
    private long code = 0L;

    private String title = null;

    // 문자열 "hello"
    private String writer = null;

    private long price = 0L;

    // 'A', 'B', 'C'
    private String category = null;

    private Date regdate = null;
}
