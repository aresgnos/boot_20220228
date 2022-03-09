package com.example.repository;

import java.util.Collection;
import java.util.List;

import com.example.entity.Board;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

// 저장소
@Repository
public interface BoardRepository extends MongoRepository<Board, Long> {
    // <Entity class, 기본키 타입>

    // 정확하게 일치 findBy변수명
    // 작성자가 'a'인 사람의 목록 조회
    List<Board> findByTitle(String title);

    List<Board> findByWriter(String writer);

    // 조회수가 100인것 목록으로 조회
    List<Board> findByHit(long hit);

    // 크다 작다
    // 조회수가 200미만 인것 목록으로 조회
    List<Board> findByHitLessThan(long hit);

    // 조회수가 300이상 인것 목록으로 조회
    List<Board> findByHitGreaterThanEqual(long hit);

    // 여러개 포함,
    // 글번호가 컬렉션에 담긴 1, 5, 7만 조회
    List<Board> findByNoIn(Collection<Long> nos);

    List<Board> findByNoNotIn(Collection<Long> nos);

    // 직접 구현
    // ** 이 방식 많이 써야 한다 **
    // 메뉴얼을 모를 때 내가 문법을 만들 수 있기 때문에 쓰기 용이하다. (get 어쩌고 저쩌고 하는거)
    // 컨트롤러에서 bRepository.getBoardTitle("제목")
    // "{title:?0}" => mongodb query 문법
    // @Query(fields = "{title:1, content:1}") => 이걸 붙여서 projection으로 쓸 수 있다.

    @Query(value = "{title: ?0}")
    List<Board> getBoardTitle(String title);

    @Query(value = "{writer: ?0}")
    List<Board> getBoardWriter(String writer);

    @Query(value = "{hit: ?0}")
    List<Board> getBoardHit(long hit);

    @Query(value = "{title : {$regex : ?0}}")
    List<Board> getBoardTitleLike(String title);
    // ex) 컨트롤러에서 bRepository.getBoardTitle("가")
    // => 제목에 "가" 가 포함된 것 가져옴.

    // 조회수가 n보다 작다. $lt, $lte, $gt, $gte
    @Query(value = "{hit : {$lt : ?0}}")
    List<Board> getBoardHitLt(long hit);

    // 제목과 작성자가 일치 (AND=,)
    @Query(value = "{title : ?0, writer:?1}")
    // = @Query(value = "{$and : [{title : ?0}, {writer:?1}]}")
    List<Board> getBoardTitleAndWriter(String title, String writer);

    // 제목과 작성자 둘 중 하나 (OR)
    @Query(value = "{$or : [{title : ?0, writer:?1}]}")
    List<Board> getBoardTitleOrWriter(String title, String writer);

    // 전체 제목에 포함된 것중에서의 개수
    @Query(value = "{title : {$regex : ?0}}", count = true)
    long getBoardTitleLikeCount(String title);

    // 작성자가 정확하게 일치하는 목록을 글번호 기준으로 내림차순 정렬
    @Query(value = "{writer: ?0}", sort = "{_id:-1}")
    List<Board> getBoardWriterSort(String writer);

    // query 문법이로다. value=query
    // { status : { $in :?0 }}
    // { no : { $in ["1", "2"]}} => no에 1, 2를 포함하는걸 가져와

}
