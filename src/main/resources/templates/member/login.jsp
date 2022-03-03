<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">

    <head th:replace="~{/member/header :: headerFragment}"></head> 

<body>
<div style="padding:50px;">
    <h3>로그인</h3>
    <hr />
    <!-- th = thymeleaf -->
    <!-- 암호가 있으니 안 보이게 post로 보냄 -->
    <form th:action="@{/member/login}" method="post">
        아이디 <input type="text" name="id" /><br />
        암호 <input type="text" name="pw" /><br />
        <input type="submit" class="btn btn-primary" value="로그인" />
    </form>
    
    <hr />
    <div th:replace="~{/member/footer :: footerFragment}"></div>
</div>
</body>

</html>
