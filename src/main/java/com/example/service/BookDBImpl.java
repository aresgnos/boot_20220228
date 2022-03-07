package com.example.service;

import java.util.Collection;
import java.util.List;

import com.example.entity.Book;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    // 일괄 삭제
    @Override
    public long deleteBatchBook(List<Long> code) {
        try {

            // long[] => List<long>
            // code => 코드가 배열로 온다 => [2,5,3] => collection<Long>
            // .is는 하나만 삭제, .in은 n개 삭제 가능함
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").in(code));

            DeleteResult result = mongoDB.remove(query, Book.class);
            if (result.getDeletedCount() == (long) code.size()) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Book> selectListWhereIn(List<Long> code) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").in(code));

            // DESC=내림차순, ASC=오름차순
            Sort sort = Sort.by(Direction.DESC, "_id");
            query.with(sort);

            return mongoDB.find(query, Book.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 일괄 수정
    @Override
    public long updatebatchBook(List<Book> list) {
        try {
            long updateCount = 0;
            for (Book tmp : list) {
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").in(tmp.getCode()));

                Update update = new Update();
                update.set("title", tmp.getTitle());
                update.set("price", tmp.getPrice());
                update.set("writer", tmp.getWriter());
                update.set("category", tmp.getCategory());

                UpdateResult result = mongoDB.updateFirst(query, update, Book.class);
                updateCount += result.getMatchedCount();
            }
            if (updateCount == list.size()) {
                return 1;
            }

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}