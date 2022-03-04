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
    <a th:href="@{/member/logout}">로그아웃</a>
    <hr />

    <p th:text="|${session.USERNAME}, ${session.USERID}님 로그인|"></p>
    <div th:if="${param.menu.toString().equals('1')}">
        <form th:action="@{/member/mypage(menu=1)}" method="post">
            <!-- entity 변수명과 동일하게 -->
            아이디 <input type="text" th:value="${mem.id}" name="id" readonly /><br />
            이름 <input type="text" th:value="${mem.name}" name="name" /><br />
            나이 <input type="text" th:value="${mem.age}" name="age" /><br />
            <input type="submit" value="정보수정" />
        </form>
    </div>

    
    <div th:if="${param.menu.toString().equals('2')}">
       <form th:action="@{/member/mypage(menu=2)}" method="post">
            현재 비밀번호 <input type="password" name="pw" /><br />
            변경할 비밀번호<input type="password" name="newPw" /><br />
            변경할 비밀번호 확인<input type="password"/><br />
            <input type="submit" value="암호변경" />
        </form>
    </div>

    <div th:if="${param.menu.toString().equals('3')}">
        <form th:action="@{/member/mypage(menu=3)}" method="post">
            비밀번호 <input type="password" name="pw" /><br />
            <input type="submit" value="회원탈퇴" />
            </form>
    </div>

    <div th:replace="~{/member/footer :: footerFragment}"></div>
</div>
</body>

</html>
