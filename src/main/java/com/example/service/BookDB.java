package com.example.service;

import java.util.List;

import com.example.entity.Book;

import org.springframework.stereotype.Service;

@Service
public interface BookDB {
    // 일괄 등록
    public int insertBatchBook(List<Book> list);

    // 일괄 삭제
    public long deleteBatchBook(List<Long> code);

    // 도서 목록
    public List<Book> selectListPageSearchBook(int page, String text);

    // 페이지네이션용(검색어)
    public long countSearchBook(String text);

    // 코드에 해당하는 목록 가져오기
    public List<Book> selectListWhereIn(List<Long> code);

    // 일괄 수정
    public long updatebatchBook(List<Book> list);
}
