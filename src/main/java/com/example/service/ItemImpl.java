package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.entity.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ItemImpl implements ItemDB {

    @Autowired
    private MongoTemplate mongodb;

    // 내가 직접 만든 service
    // 필요한 곳에서 시퀀스의 값을 가져올 수 있게 구현함.
    @Autowired
    private SequenceService sequenceService;

    // 물품 등록
    @Override
    public int inserItem(Item item) {
        try {
            // 코드 (시퀀스에서 받아옴)
            long seq = sequenceService.generatorSequence("SEQ_ITEM4_CODE");
            item.setCode(seq);

            // import java.util.Date
            // 현재 시간
            Date date = new Date(); // 현재
            item.setRegdate(date);

            Item item1 = mongodb.insert(item);
            if (item1.getCode() == seq) {
                return 1;
            }
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 물품 목록

    @Override
    public Item selectOneImage(long code) {
        try {
            Query query = new Query();
            Criteria criteria = Criteria.where("_id").is(code);
            query.addCriteria(criteria);
            query.fields().include("filedata", "filetype", "filesize");

            return mongodb.findOne(query, Item.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Item selectOneItem(long code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Item> selectListItem(Pageable pageable) {
        try {
            Query query = new Query();
            query.with(pageable);
            query.fields().exclude("filedata", "filetype", "filesize", "filename"); // projection

            Sort sort = Sort.by(Direction.DESC, "_id");
            query.with(sort);

            return mongodb.find(query, Item.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
