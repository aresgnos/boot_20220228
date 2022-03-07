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
        <h4>도서 일괄 수정</h4>
        <hr />

        <form th:action="@{/admin/updatebatch}" method="post">
            <input type="submit" value="일괄수정" />
            <table class="table table-sm">
                <tr>
                    <th>코드</th>
                    <th>책제목</th>
                    <th>가격</th>
                    <th>저자</th>
                    <th>분류</th>
                    <th>등록일</th>
                </tr>
                <tr th:each="tmp : ${list}">
                    <td><input type="text" th:value="${tmp.code}" name="code" readonly /></td>
                    <td><input type="text" th:value="${tmp.title}" name="title" /></td>
                    <td><input type="text" th:value="${tmp.price}" name="price" /></td>
                    <td><input type="text" th:value="${tmp.writer}" name="writer" /></td>
                    <td>
                        <p th:text="${tmp.category}" name="category"></p>
                        <select name="category">
                            <option th:selected="${#strings.equals(tmp.category, '소설')}">소설</option>
                            <option th:selected="${#strings.equals(tmp.category, '시')}">시</option>
                            <option th:selected="${#strings.equals(tmp.category, '비문학')}">비문학</option>
                        </select>
                    </td>
                    <td th:text="${tmp.regdate}" name="regdate"></td>
                </tr>
            </table>

        </form>
    </div>
</body>


</style>
</html>