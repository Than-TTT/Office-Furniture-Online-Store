<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Phản hồi Khách hàng</title>
    <!-- Thêm Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

</head>
<body class="d-flex justify-content-center align-items-center bg-light">
<style>
    body, html {
        height: 100%;
    }
    .chat-container {
        height: 100%;
        display: flex;
        flex-direction: column;
    }
    .chat-box {
        flex: 1;
        overflow-y: auto;
        padding: 20px;
        border-bottom: 1px solid #ddd;
    }
    .input-area {
        display: flex;
        padding: 10px;
        border-top: 1px solid #ddd;
        background-color: #f9f9f9;
    }
    .input-area input {
        width: 80%;
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ddd;
    }
    .input-area button {
        width: 15%;
        padding: 10px;
        margin-left: 5px;
        border-radius: 5px;
        border: none;
        background-color: #4CAF50;
        color: white;
        cursor: pointer;
    }
    .input-area button:hover {
        background-color: #45a049;
    }
</style>
<div class="chat-container col-md-6 col-lg-4">
    <div class="chat-box" id="chat-box">
        <!-- Sử dụng JSTL để hiển thị danh sách tin nhắn -->
        <c:forEach var="message" items="${messages}">
            <div class="message mb-2">
                <strong>${message.sender.name}:</strong> ${message.content}
                <br>
                <small>${fn:substring(message.timestamp, 0, 16)}</small>
                <span class="message-data" data-customer-id="${message.sender.userId}"></span>
            </div>
        </c:forEach>
    </div>
    <div class="input-area">
        <input type="text" id="message-input" placeholder="Nhập tin nhắn..." name="content">
        <button type="button" onclick="sendMessage()">Gửi</button>
    </div>
</div>

<!-- Thêm Bootstrap JS và jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    function sendMessage() {
        const inputField = document.getElementById('message-input');
        const message = inputField.value;
        const firstMessage = document.querySelector('.message');
        const customerId = firstMessage ? firstMessage.querySelector('.message-data').getAttribute('data-customer-id') : '';

        // Kiểm tra nếu tin nhắn không rỗng
        if (message.trim() !== '') {
            // Tạo form ẩn để gửi dữ liệu qua POST
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/employee/chat';  // Địa chỉ servlet của bạn

            // Tạo input ẩn để chứa nội dung tin nhắn
            const contentInput = document.createElement('input');
            contentInput.type = 'hidden';
            contentInput.name = 'content';
            contentInput.value = message;

            const customerIdInput = document.createElement('input');
            customerIdInput.type = 'hidden';
            customerIdInput.name = 'customerId';
            customerIdInput.value = customerId;

            form.appendChild(contentInput);
            form.appendChild(customerIdInput);
            // Thêm form vào body và submit
            document.body.appendChild(form);
            form.submit();
            inputField.value = '';
        }
    }
</script>
</body>
</html>
