<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>물품수정</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:20px;">
        <h3>물품수정</h3>
        <hr />
        <!-- entity에 있는 변수로 th:value 구성 -->
        <form th:action="@{/item/update}" method="post">
            물품명 <input type="text" th:value="${item.name}" name="name" /><br />
            가격 <input type="text" th:value="${item.price}" name="price" /><br />
            수량 <input type="text" th:value="${item.quantity}" name="quantity" /><br />
            <input type="submit" value="수정" />
        </form>
    </div>
</body>
</html>