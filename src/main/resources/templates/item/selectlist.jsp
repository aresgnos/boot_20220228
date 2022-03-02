<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>물품목록</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:80px;">
        <h4>물품목록</h4>
        <hr />

        <table class="table table-borderless">
            <tr>
                <th>번호</th>
                <th>물품코드</th>
                <th>물품명</th>
                <th>가격</th>
                <th>수량</th>
                <th>등록일</th>
                <th>이미지</th>
                <th>수정/삭제</th>
            </tr>

            <tr th:each="tmp, idx : ${list}" >
                <td th:text="${idx.count}"></td>
                <td th:text="${tmp.code}"></td>
                <td th:text="${tmp.name}"></td>
                <td th:text="${tmp.price}"></td>
                <td th:text="${tmp.quantity}"></td>
                <td th:text="${tmp.regdate}"></td>
                <td>
                    <img th:src="@{/item/image(code=${tmp.code})}" style="width:50px;height:50px;" />
                </td>
                <td>
                    <a th:href="@{/item/update(id=${tmp.code})}">수정</a>
                    <a th:href="@{/item/delete(id=${tmp.code})}">삭제</a>

                    <form th:action="@{/item/delete}" method="get">
                    <input type="hidden" name="code" th:value="${tmp.code}" />
                    <input type="submit" value="삭제1" />
                    </form>
                </td>
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
                  <a class="page-link" th:href="@{/item/selectlist(page=${i})}" th:text="${i}"></a></li>
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
</html>