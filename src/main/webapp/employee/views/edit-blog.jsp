<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Blog</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Edit Blog</h2>
    <form action="${pageContext.request.contextPath}/employee/update-blog" method="post">
        <input type="hidden" name="blogId" value="${blog.blogId}">
        <div class="form-group">
            <label for="blogTitle">Blog Title</label>
            <input type="text" class="form-control" id="blogTitle" name="blogTitle" value="${blog.blogTitle}" required>
        </div>
        <div class="form-group">
            <label for="content">Content</label>
            <textarea class="form-control" id="content" name="content" rows="5" required>${blog.content}</textarea>
        </div>
        <button type="submit" class="btn btn-primary btn-block mt-3">Save Changes</button>
        <a href="${pageContext.request.contextPath}/employee/blog" class="btn btn-secondary btn-block mt-3">Cancel</a>
    </form>
</div>
</body>
</html>
