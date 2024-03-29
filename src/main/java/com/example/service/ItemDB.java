package com.example.service;

import java.util.List;

import com.example.entity.Item;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ItemDB {

    // 물품등록 (추가할 물품 정보가 처리 후에 int(-1, 0, 1)으로 반환)
    public int inserItem(Item item);

    // 물품목록 (페이지 정보 1,2,3)
    public List<Item> selectListItem(Pageable pageable);

    // 이미지 정보 1개 조회
    public Item selectOneImage(long code);

    // 물품 1개 조회 (이미지 제외)
    public Item selectOneItem(long code);

    // 물품 전체 개수 구하기 (페이지네이션의 페이지)
    public long selectItemCount();

    // 물품 1개 삭제
    public int deleteItemOne(long code);

    // 물품 수정
    public int updateItemOne(Item item);
}
