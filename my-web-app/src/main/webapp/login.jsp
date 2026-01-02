<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập | Cửa hàng Nội thất Luxury</title>
    <style>
        /* CSS tạo phong cách nội thất hiện đại */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: url('https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background: rgba(255, 255, 255, 0.95);
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            width: 350px;
            text-align: center;
        }
        h2 {
            color: #4a3f35; /* Màu nâu gỗ trầm */
            margin-bottom: 30px;
            text-transform: uppercase;
            letter-spacing: 2px;
        }
        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }
        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box; /* Quan trọng để không bị tràn */
        }
        button {
            width: 100%;
            padding: 12px;
            background-color: #4a3f35;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background 0.3s;
        }
        button:hover {
            background-color: #635447;
        }
        .error-msg { color: #d9534f; margin-bottom: 15px; }
        .success-msg { color: #5cb85c; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Luxury Home</h2>
        
        <%-- Hiển thị thông báo từ Controller --%>
        <div class="error-msg">${error}</div>
        <div class="success-msg">${message}</div>

        <form action="login" method="post">
            <div class="form-group">
                <input type="text" name="username" placeholder="Tên đăng nhập" required>
            </div>
            <div class="form-group">
                <input type="password" name="password" placeholder="Mật khẩu" required>
            </div>
            <button type="submit">ĐĂNG NHẬP</button>
        </form>
    </div>
</body>
</html>