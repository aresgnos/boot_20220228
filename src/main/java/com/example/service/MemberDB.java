package com.example.service;

import java.util.List;

import com.example.entity.Member;

import org.springframework.stereotype.Service;

// DB와 연동하는 부분 구현하는 설계부분
// 설계도면
@Service
public interface MemberDB {

    // 추가할 내용을 member에 주면 추가한 후에 실제 추가된 내용을 반환
    public Member insertMember(Member member);

    // 회원 전체목록(page, search X)
    public List<Member> selectListMember();

    // 회원 1명 삭제, 탈퇴 (회원 아이디가 오면 삭제 후 -1, 0 또는 1로)
    public int deleteMember(String id);

    // 비밀번호로 회원탈퇴
    public int removeMember(String pw);

    // 회원 1명 조회하기 (아이디를 전달하면 회원 1명의 정보가 옴)
    public Member selectOneMember(String id);

    // 회원 정보 변경하기
    public int updateMember(Member member);

    // 로그인 (아이디, 암호가 전달되면 일치하는 회원정보를 반환)
    public Member selectLogin(Member member);

    // 암호 변경
    public long updatMemberPassword(Member member);

    // 삭제, 추가, 수정의 return은 int

}
