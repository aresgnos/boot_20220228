<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상세내용</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:70px;">
        <h4>상세내용</h4>
        <a th:href="@{/book/selectlist}" class="btn btn-primary" style="position:absolute;left:70%;top:100px;">목록으로</a>

        <form th:action="@{/book/content}" method="get">
            <table class="table table-borderless">
                <tr>
                    <th>도서코드</th>
                    <th>도서명</th>
                    <th>저자</th>
                    <th>가격</th>
                    <th>분류</th>
                    <th>등록일</th>
                </tr>

                <tr>
                    <td th:value="${book.code}"></td>
                    <td th:value="${book.title}"></td>
                    <td th:value="${book.writer}"></td>
                    <td th:value="${book.price}"></td>
                    <td th:value="${book.category}"></td>
                    <td th:value="${book.regdate}"></td>
                </tr>
            </table>
          </form>

    
    </div>
</body>
</html>