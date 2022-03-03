<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>정보수정</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:30px;">
    <h4>HOME</h4>

    <!-- 과거에 로그인한 기록이 있느냐 -->
    <div th:unless="${session.USERID}">
        <a th:href="@{/member/login}" class="btn btn-primary" style="position:absolute;left:90%;top:30px;">로그인</a>
    </div>

    <div th:if="${session.USERID}">
        <a th:href="@{/member/logout}" class="btn btn-primary" style="position:absolute;left:90%;top:30px;">로그아웃</a>
        <a th:href="@{/member/mypage}">마이페이지</a>
    </div>    
   
        <div style="position: absolute;right:10%;top:100px;">
        <img src="https://images.unsplash.com/photo-1553034608-0d7cc3807216?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1333&q=80">
        </div>
    </div>
</body>
</html>