package com.example.service;

import com.example.entity.Sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

// 컨트롤러에서 사용하는 용도가 X
// 다른 service에서 시퀀스가 필요할 때 사용하는 용도
@Service
public class SequenceService {

    @Autowired
    private MongoTemplate mongoDB;

    public long generatorSequence(String seqName) {

        // 조건 : _id가 seqName으로 전달 되는 것을 찾음
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(seqName));

        // seq를 1증가시킴
        Update update = new Update();
        update.inc("seq", 1);

        // 옵션 반환과 1증가를 동시에 수행
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        options.upsert(true);

        Sequence counter = mongoDB.findAndModify(query, update, options, Sequence.class);

        if (counter != null) {
            return counter.getSeq();
        }
        return 1;
    }
}
