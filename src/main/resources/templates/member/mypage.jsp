<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">

    <head th:replace="~{/member/header :: headerFragment}"></head> 

<body>
<div style="padding:50px;">
    <h4>마이페이지</h4>
    <hr />
    <a th:href="@{/member/mypage?menu=1}">정보수정</a>
    <a th:href="@{/member/mypage?menu=2}">암호변경</a>
    <a th:href="@{/member/mypage?menu=3}">탈퇴하기</a>
    <hr />
   
    <div th:if="${param.menu.toString().equals('1')}">
        정보수정
    </div>
    <div th:if="${param.menu.toString().equals('2')}">
        
        <form th:action="@{/member/mypage}" method="post">
            비밀번호 <input type="text" th:value="${member.pw}" name="pw" /><br />
            변경할 비밀번호 <input type="text" th:value="${member.pw2}" name="pw2" /><br />
            <input type="submit" value="수정" />
        </form>
    
    </div>
    <div th:if="${param.menu.toString().equals('3')}">
        회원탈퇴
        <a th:href="@{/member/delete(id=${tmp.id})}">삭제</a>
    </div>

    <div th:replace="~{/member/footer :: footerFragment}"></div>
</div>
</body>

</html>
