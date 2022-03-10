<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서목록</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:70px;">
        <h3>도서목록</h3>
        <a th:href="@{/book/insert}" class="btn btn-primary" style="position:absolute;left:70%;top:100px;">도서등록</a>

        <form th:action="@{/book/selectlist}" method="get">
          <input type="hidden" name="page" value="1" />
          <input type="text" name="text" placeholder="검색어" />
          <input type="submit" value="검색" />
        </form>


            <table class="table table-borderless">
                <tr>
                    <th>도서코드</th>
                    <th>도서명</th>
                    <th>저자</th>
                    <th>가격</th>
                    <th>분류</th>
                    <th>등록일</th>
                </tr>

                <tr th:each="tmp : ${list}" >
                    <td th:text="${tmp.code}"></td>
                    <td th:text="${tmp.title}"></td>
                    <td th:text="${tmp.writer}"></td>
                    <td th:text="${tmp.price}"></td>
                    <td th:text="${tmp.category}"></td>
                    <td th:text="${tmp.regdate}"></td>
                </tr>
            </table>

        <div style="justify-content: center; display: flex; margin-top: 30px;">
        <nav aria-label="Page navigation example" >
            <ul class="pagination" >
              <li class="page-item" >
                <a class="page-link" href="#" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <li class="page-item" th:block th:each="i : ${#numbers.sequence(1, pages)}">
                  <a class="page-link" th:href="@{/book/selectlist(page=${i}, text=${param.text})}" th:text="${i}"></a></li>
                <a class="page-link" href="#" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
          </div>

        <!-- <th:block th:each="i : ${#numbers.sequence(1, pages)}">
            <a th:href="@{/item/selectlist(page=${i})}" th:text="${i}"></a>
        </th:block> -->

    </div>
</body>
<style>
h4 {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
</html>