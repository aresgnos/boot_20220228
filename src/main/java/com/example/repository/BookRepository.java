package com.example.repository;

import java.util.List;

import com.example.entity.Book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, Long> {
    // <Entity class, 기본키 타입>

    // 서비스 사용X
    // 저장소를 사용하여 기본적인 CRUD(읽기, 쓰기, 수정, 삭제)
    // 1. 책 등록
    // 2. 책 목록(전체)
    // 3. 책번호를 누르면 책 상세내용 표시
    // 4. 페이지네이션과 정렬

    // 전체목록 + 페이지네이션
    @Query(value = "{}")
    List<Book> getBookList(String title, Pageable pageable);

    // 페이지네이션 번호 생성
    @Query(value = "{title: {$regex : ?0}}", count = true)
    long getBookCount(String title);
}
