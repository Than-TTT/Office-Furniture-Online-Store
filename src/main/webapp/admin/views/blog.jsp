<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <title>Waiting Blog List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .short-content {
            max-width: 300px; /* Giới hạn chiều rộng của cột để tránh hiển thị quá dài */
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mt-4">Waiting Blog List</h2>
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <th>Blog ID</th>
            <th>Blog Title</th>
            <th>Content</th>
            <th>Posting Date</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="blog" items="${listBlog}">
            <tr>
                <td>${blog.blogId}</td>
                <td>${blog.blogTitle}</td>
                <td>
                    <div class="short-content">${fn:substring(blog.content, 0, 100)}...</div>
                    <button type="button" class="btn btn-link" data-toggle="collapse" data-target="#content-${blog.blogId}">Xem thêm</button>
                    <div id="content-${blog.blogId}" class="collapse">${blog.content}</div>
                </td>
                <td>${blog.postingDate}</td>
                <td>
                    <form method="post" action="blog/approve">
                        <input type="hidden" name="blogId" value="${blog.blogId}">
                        <input type="hidden" name="currentPage" value="${currentPage}">
                        <button type="submit" class="btn btn-success">Approve</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty listBlog}">
            <tr>
                <td colspan="5" class="text-center">Không có blog nào chưa duyệt</td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <!-- Pagination Links -->
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:if test='${currentPage > 1}'>
                <li class="active">
                    <a class="page-link" href="?page=${currentPage - 1}">Previous</a>
                </li>
            </c:if>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item <c:if test='${currentPage == i}'>active</c:if>'">
                    <a class="page-link" href="?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <c:if test='${currentPage < totalPages}'>
                <li class="active">
                    <a class="page-link" href="?page=${currentPage + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
    <a href="home" class="btn btn-primary">Back to Home</a>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
