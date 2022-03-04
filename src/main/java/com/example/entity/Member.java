package com.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
// 데이터가 들어갈 곳
@Document(collection = "member3")

public class Member {

    // 변수명이 컬럼명이 된다 => 데이터베이스에서
    // 변수명과 insert.jsp name 이름이 일치해야함.
    @Id
    private String id = null;

    private String pw = null;

    private String pw1 = null;

    private String name = null;

    private int age = 0;

    // 일시적 (컬럼으로 만들어지지 않음, DB에 연동되지 않음.)
    // 개발에서 필요한 정보를 보관하기 위한 용도
    @Transient
    private String newPw = null;
}
