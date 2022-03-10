package com.example.repository;

import java.util.Collection;
import java.util.List;

import com.example.entity.Book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 검색한 개수 + 페이지네이션
    @Query(value = "{title: {$regex : ?0}}")
    List<Book> getBookList(String title, Pageable pageable);

    // 검색에 해당하는 전체 개수
    @Query(value = "{title: {$regex : ?0}}", count = true)
    long getBookCount(String title);

    // 코드가 일치하는 것 내용 조회
    @Query(value = "{code: : ?0}")
    long getBookOne(long code);

    // 코드가 일치하는 것 개수 조회
    @Query(value = "{code: : ?0}", count = true)
    long countBook(long code);

    // 코드가 일치하는 것 삭제
    @Query(value = "{code: : ?0}", delete = true)
    long deleteBookCode(long code);

    // 코드가 일치하는 것 존재유무 확인
    @Query(value = "{code: : ?0}", exists = true)
    boolean isBookCode(long code);

    // 코드가 여러개인 것 조회
    @Query(value = "{code : {$in : ?0}")
    List<Book> getBookCodeList(Collection<Long> code);

    // 제목과 가격이 일치하는 것 조회(마이 바티스에선 허용하지 않음)
    @Query(value = "{title : ?0, price :?1")
    List<Book> getBookTitleAndPriceList(String title, long price);

    // 전체 목록 가져오기 code 기준 내림차순
    @Query(value = "{}", sort = "{code : -1}")
    List<Book> getBookListSortCode();

    // 전체 목록에서 필요한 것 가져오기 code 기준 내림차순
    @Query(value = "{}", sort = "{code : -1}", fields = "{title : 1, price : 1")
    List<Book> getBookListSordCodeProjection();

    // 제목과 가격이 일치하는 것 조회 (OR)
    @Query(value = "{$or : [{title : ?0}, {price : ?1}]}")
    List<Book> getBookTitleOrPriceList(String title, long price);

    // 가격이 ?0 이상인 것 ($gte, $gt, $lte, $lt)
    @Query(value = "{price : {$gte : ?0}}")
    List<Book> getBookPriceGte(long pirce);

    // mybatis ver. (각각 매칭시켜줘야함)
    @Query(value = "{title : :#{#title}, price : :#{#price}}")
    List<Book> getBookTitleAndPriceList1(
            @Param("price") long p, @Param("title") String t);

    // mybatis ver.
    @Query(value = "{title : #{#book.title}, price : :#{#book.price}}")
    List<Book> getBookTitleAndPriceList2(@Param("book") Book book);

}
