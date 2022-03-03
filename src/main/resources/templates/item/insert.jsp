<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>물품등록</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:50px;">
        <h4>물품등록</h4>
        물품코드, 등록일은 자동 생성 <br />
        <hr />
        <!-- 이미지가 포함되므로 get(X), post(O) -->
        <form th:action="@{/item/insert}" method="post"
            enctype="multipart/form-data">
            
            <label class="form-label">물품명</label><input type="text" name="name" /><br />
            <label class="form-label">가격</label><input type="text" name="price" /><br />
            <label class="form-label">수량</label><input type="text" name="quantity" /><br />
            <label class="form-label">이미지</label><input type="file" name="image" /><br />
            <input type="submit" class="btn btn-primary" value="등록" style="margin-top: 5px;position:absolute;left: 20%;"  />
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