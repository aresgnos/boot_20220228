<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서목록</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:70px;">
        <h4>도서목록</h4>
        <a th:href="@{/admin/insertbatch}" class="btn btn-primary" style="position:absolute;left:90%;top:100px;">등록</a>

        <!-- a태그로 만들면 입력한 값이 들어가지 않는다. 검색어로 주소가 형성되어야 하므로 -->
        <form th:action="@{/admin/selectlist}" method="get">
            <input type="hidden" name="page" value="1" />
            <input type="text" name="text" placeholder="검색어" />
            <input type="submit" value="검색" />
        </form>
        <br />

        <form th:action="@{/admin/action}" method="post">
            <a class="btn btn-primary" name="btn" style="position:absolute;left:84%;top:100px;">일괄삭제</a>
            <a class="btn btn-primary" name="btn" style="position:absolute;left:78%;top:100px;">일괄수정</a>
            
            <table class="table table-borderless">
                <tr class="table-primary">
                    <th>선택</th>
                    <th>도서코드</th>
                    <th>도서명</th>
                    <th>저자</th>
                    <th>가격</th>
                    <th>분류</th>
                    <th>등록일</th>
                    <!-- <th>수정/삭제</th> -->
                </tr>

                <tr th:each="tmp : ${list}" >
                    <!-- 체크박스 value 값을 주지 않으면 true, false로 값이 넘어감 그래서 체크박스에 고유한 값 넣어주기 -->
                    <td><input type="checkbox" name="chk" th:value="${tmp.code}" /></td>
                    <td th:text="${tmp.code}"></td>
                    <td th:text="${tmp.title}"></td>
                    <td th:text="${tmp.writer}"></td>
                    <td th:text="${tmp.price}"></td>
                    <td th:text="${tmp.category}"></td>
                    <td th:text="${tmp.regdate}"></td>
                    <!-- <td>
                        <a th:href="@{/item/update(code=${tmp.code})}">수정</a>
                        <a th:href="@{/item/delete(code=${tmp.code})}">삭제</a>

                    </td> -->
                </tr>
            </table>
        </form>

        <div style="justify-content: center; display: flex; margin-top: 30px;">
        <nav aria-label="Page navigation example" >
            <ul class="pagination" >
              <li class="page-item" >
                <a class="page-link" href="#" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              <li class="page-item" th:block th:each="i : ${#numbers.sequence(1, pages)}">
                  <a class="page-link" th:href="@{/admin/selectlist(page=${i}, text=${param.text})}" th:text="${i}"></a></li>
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