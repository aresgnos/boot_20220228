package com.example.service;

import java.util.Date;

import com.example.entity.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class ItemImpl implements ItemDB {

    @Autowired
    private MongoTemplate mongodb;

    // 내가 직접 만든 service를 이용해서
    // 필요한 곳에서 시퀀스의 값을 가져올 수 있게 구현함.
    @Autowired
    private SequenceService sequenceService;

    @Override
    public Item inserItem(Item item) {
        try {
            // 현재 시간
            item.setRegdate(new Date());
            // 코드(시퀀스에서 받아옴)
            item.setCode(sequenceService.generatorSequence("SEQ_ITEM3_NO"));

            return mongodb.insert(item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
