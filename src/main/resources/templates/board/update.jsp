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
        <h3>글수정</h3>
        <hr />
        <form th:action="@{/board/update}" method="post">
            <input type="hidden" name="no" th:value="${board.no}" />
            제목 <input type="text" name="title" th:value="${board.title}" /><br />
            작성자 <input type="text" name="writer" th:value="${board.writer}" /><br />
            내용 <input type="text" name="content" th:value="${board.content}" /><br />
            
            <input type="submit" class="btn btn-primary" value="글수정" />
            <a th:href="@{board/selectlist}">글목록</a>
        </form>
    </div>
</body>
</html>