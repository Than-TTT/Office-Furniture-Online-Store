<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý hỗ trợ</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/styles.css"/>
</head>
<body>
<div class="sidebar">
</div>
<div class="main-content">
    <div class="container mt-4">
        <h2 class="text-center">Danh sách câu hỏi chưa trả lời</h2>
        <div class="table-responsive text-nowrap">
            <table  class="table table-bordered table-hover mt-3">
                <thead  class="table-dark">
                <tr>
                    <th>Mã KH</th>
                    <th>Tên KH</th>
                    <th>Câu hỏi</th>
                    <th>Ngày</th>
                    <th>Xử lý</th>
                </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                <c:forEach var="question" items="${pendingQuestions}">
                    <tr>
                        <td><strong>${question.customer.userId}</strong></td>
                        <td>${question.customer.name}</td>
                        <td>${question.content}</td>
                        <td>${question.timestamp}</td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow"
                                        data-bs-toggle="dropdown">
                                    <i class="bx bx-dots-vertical-rounded"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item " href="/employee/chat?questionId=${question.questionId}"><i class="bx bx-edit-alt me-1"></i> Phản hồi</a>
                                    <a class="dropdown-item" href="deleteQuestion?questionId=${question.questionId}"><i
                                            class="bx bx-trash me-1"></i> Delete</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
