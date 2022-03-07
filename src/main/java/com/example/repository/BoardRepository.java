package com.example.repository;

import com.example.entity.Board;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// 저기 저 오른쪽<entity, 타입은 뭐냐>
@Repository
public interface BoardRepository extends MongoRepository<Board, Long> {

}
