package com.example.service;

import java.util.List;

import com.example.entity.Member;
import com.mongodb.client.result.DeleteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

// DB연동을 실제로 수행하는 구현부
// 구현부는 프레임워크에 따라서 안 만들수도 있다.
@Service
public class MemberDBImpl implements MemberDB {

    // 환경설정으로 생성된 객체를 가져옴
    @Autowired
    private MongoTemplate mongodb;

    @Override
    public Member insertMember(Member member) {
        try {
            return mongodb.insert(member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Member> selectListMember() {
        try {
            Query query = new Query();
            return mongodb.find(query, Member.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int deleteMember(String id) {
        try {
            Member member = new Member();
            member.setId(id);

            DeleteResult result = mongodb.remove(member);
            if (result.getDeletedCount() == 1L) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

}
