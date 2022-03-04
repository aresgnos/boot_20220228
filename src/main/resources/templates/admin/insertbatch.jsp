<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일괄등록</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:50px;">
        <h4>도서 일괄 등록</h4>
        <hr />
        <form th:action="@{/admin/insertbatch}" method="post">
            <table class="table table-borderless">
                <tr>
                    <th>제목</th>
                    <th>가격</th>
                    <th>저자</th>
                    <th>분류</th>
                </tr>

                <!-- 칸을 더 늘리고 싶으면 sequence(1, n) -->
                <tr th:each="i : ${#numbers.sequence(1,2)}">
                    <td><input type="text" value="1" name="title" /></td>
                    <td><input type="text" value="2" name="price" /></td>
                    <td><input type="text" value="은희경" name="writer" /></td>
                    <td>
                        <select name="category">
                        <option>소설</option>
                        <option>시</option>
                        <option>비문학</option>
                        </select>
                    </td>
                </tr>
            </table>
            <input type="submit" class="btn btn-primary" value="도서일괄등록" />
        </form>
    </div>
</body>

<style type="text/css">

.form-label {
    width:70px;
    margin-top: 8px;
    margin-left:8px;
}
</style>
</html>