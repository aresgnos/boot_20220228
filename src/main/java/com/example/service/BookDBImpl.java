package com.example.service;

import java.util.Collection;
import java.util.List;

import com.example.entity.Book;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.internal.bulk.DeleteRequest;

import org.eclipse.jdt.internal.compiler.parser.NLSTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

// 1. 서비스
@Service
public class BookDBImpl implements BookDB { // 2. 설계 인터페이스 구현

    // 3. DB 연결 객체 생성
    @Autowired
    MongoTemplate mongoDB;

    // 일괄 등록
    @Override
    public int insertBatchBook(List<Book> list) {
        try {

            // 4. 실제 수행
            // insert=>Collection 타입으로 넣으면 여러 타입을 넣을 수 있지만
            // arraylist로 넣으면 여러 타입을 넣을 수 없다.
            Collection<Book> retList = mongoDB.insert(list, Book.class);
            if (retList.size() == list.size()) {
                return 1;
            }
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    // 목록
    @Override
    public List<Book> selectListPageSearchBook(int page, String text) {
        try {

            Query query = new Query();

            // 검색 패턴 (.*a.* => a가 포함된 것 해당), 정규식
            Criteria criteria = Criteria.where("title").regex(".*" + text + ".*");
            query.addCriteria(criteria);

            // 페이지네이션 (0부터 시작)
            Pageable pageable = PageRequest.of(page - 1, 10);
            query.with(pageable);

            // 정렬 (_id기준 내림차순)
            Sort sort = Sort.by(Direction.DESC, "_id");
            query.with(sort);

            return mongoDB.find(query, Book.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 전체 개수
    @Override
    public long countSearchBook(String text) {
        try {
            Query query = new Query();

            // 검색 패턴 (.*a.* => a가 포함된 것 해당), 정규식
            Criteria criteria = Criteria.where("title").regex(".*" + text + ".*");
            query.addCriteria(criteria);

            return mongoDB.count(query, Book.class);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteBatchBook(String btn, long[] code) {
        try {

            Query query = new Query();
            Criteria criteria = Criteria.where("btn").is(code);
            query.addCriteria(criteria);

            DeleteResult result = mongoDB.remove(query, Book.class);
            if (result.getDeletedCount() > 0L) {
                return 1;
            }
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}