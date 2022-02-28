<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:20px;">
        <h3>물품등록</h3>
        <hr />
        <!-- th = thymeleaf -->
        <form th:action="@{/item/insert}" method="post">
            물품명 <input type="text" name="name" /><br />
            가격 <input type="text" name="price" /><br />
            수량 <input type="text" name="quantity" /><br />
            날짜 <name="regdate" /><br />
            <input type="submit" class="btn btn-primary" value="물품등록" />
        </form>
    </div>
</body>
</html>