<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Management Home</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Blog Management</h2>
    <div class="d-flex justify-content-between my-3">
        <form action="${pageContext.request.contextPath}/employee/search-blog" method="get" class="form-inline">
            <input class="form-control mr-sm-2" type="search" placeholder="Search by title" aria-label="Search" name="title">
            <input class="form-control mr-sm-2" type="date" name="date">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
        <a href="${pageContext.request.contextPath}/employee/blog" class="btn btn-outline-primary">Show All</a>
    </div>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <div class="table-responsive mt-4">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Content</th>
                    <th>Posting Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="blog" items="${blogs}">
                    <tr>
                        <td>${blog.blogId}</td>
                        <td>${blog.blogTitle}</td>
                        <td>${blog.content}</td>
                        <td>${blog.postingDate}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/employee/update-blog?blogId=${blog.blogId}" class="btn btn-primary btn-sm">Edit</a>
                            <form action="${pageContext.request.contextPath}/employee/delete-blog" method="post" style="display:inline;">
                                <input type="hidden" name="blogId" value="${blog.blogId}">
                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="d-flex justify-content-between mt-4">
        <a href="${pageContext.request.contextPath}/employee/add-blog" class="btn btn-success">Add Blog</a>
    </div>
    <div class="text-right mt-4">
        <a href="${pageContext.request.contextPath}/employee/logout" class="btn btn-secondary">Logout</a>
    </div>
</div>
</body>
</html>
