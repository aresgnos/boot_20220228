<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서 등록</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:50px;">
        <h3>도서 등록</h3>
        <input type="submit" class="btn btn-primary" value="등록" style="margin-left: 100px;"  />
        <br />
        <hr />
        <form th:action="@{/book/insert}" method="post">
            <table class="table table-borderless">
            
            <label class="form-label">도서명</label><input type="text" name="title" /><br />
            <label class="form-label">저자</label><input type="text" name="writer" /><br />
            <label class="form-label">가격</label><input type="text" name="price" /><br />
            <label class="form-label">분류</label>
                <select name="category">
                <option>소설</option>
                <option>시</option>
                <option>비문학</option>
                </select>
                
            
        </form>
    </div>
</body>

<style type="text/css">

.form-label {
    width:70px;
    margin-top: 8px;
    margin-left:8px;
}

h3 {
    float: left;
    font-family: 'Noto Sans KR', sans-serif;
}




</style>
</html>