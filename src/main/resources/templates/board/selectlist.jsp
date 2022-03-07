<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판목록</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}" />
    <script type="text/javascript" th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding:70px;">
        <h4>게시판목록</h4>
        <a th:href="@{/board/insert}" class="btn btn-primary" style="position:absolute;left:90%;top:70px;">글쓰기</a>

        <input type="submit" name="btn" value="1개 삭제" />
        <input type="submit" name="btn" value="1개 수정" />
        <table class="table table-borderless">
            <tr class="table-primary">
                <th>radio</th>
                <th>글번호</th>
                <th>제목</th>
                <th>내용</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>등록일</th>
            </tr>

            <tr th:each="tmp, idx : ${list}" >
                <td><input type="radio" name="rad" th:value="${tmp.no}"></td>
                <td th:text="${idx.count}"></td>
                <td th:text="${tmp.no}"></td>
                <td th:text="${tmp.title}"></td>
                <td th:text="${tmp.content}"></td>
                <td th:text="${tmp.writer}"></td>
                <td th:text="${tmp.regdate}"></td>
                
                    <!-- <a th:href="@{/board/update(code=${tmp.code})}">수정</a>
                    <a th:href="@{/board/delete(code=${tmp.code})}">삭제</a> -->            
                </td>
            </tr>
        </table>
        <!-- <div style="justify-content: center; display: flex; margin-top: 30px;">
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
          </div> -->

        <!-- <th:block th:each="i : ${#numbers.sequence(1, pages)}">
            <a th:href="@{/item/selectlist(page=${i})}" th:text="${i}"></a>
        </th:block> -->

    </div>
</body>
</html>