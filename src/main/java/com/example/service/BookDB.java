package com.example.service;

import java.util.List;

import com.example.entity.Book;

import org.springframework.stereotype.Service;

@Service
public interface BookDB {
    // 일괄 등록
    public int insertBatchBook(List<Book> list);

    // 일괄 삭제
    public int deleteBatchBook(String btn, long[] code);

    // 도서 목록
    public List<Book> selectListPageSearchBook(int page, String text);

    // 전체 개수
    public long countSearchBook(String text);
}
