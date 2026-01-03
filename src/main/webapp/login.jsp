<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Đăng nhập - Furniture Store</title>

    <!-- Bootstrap 5 -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />

    <style>
      body {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      }

      .login-container {
        background: white;
        border-radius: 20px;
        box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        overflow: hidden;
        max-width: 900px;
        width: 100%;
      }

      .login-image {
        background: url("https://images.unsplash.com/photo-1497366216548-37526070297c?w=800")
          center/cover;
        min-height: 500px;
      }

      .login-form {
        padding: 60px 50px;
      }

      .login-title {
        color: #667eea;
        font-weight: 700;
        margin-bottom: 10px;
      }

      .login-subtitle {
        color: #6c757d;
        margin-bottom: 40px;
      }

      .form-control {
        border-radius: 10px;
        padding: 12px 20px;
        border: 2px solid #e0e0e0;
        transition: all 0.3s;
      }

      .form-control:focus {
        border-color: #667eea;
        box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
      }

      .btn-login {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border: none;
        border-radius: 10px;
        padding: 12px;
        font-weight: 600;
        color: white;
        width: 100%;
        transition: transform 0.2s;
      }

      .btn-login:hover {
        transform: translateY(-2px);
        box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
      }

      .test-accounts {
        background: #f8f9fa;
        border-radius: 10px;
        padding: 20px;
        margin-top: 30px;
        font-size: 0.9rem;
      }

      .test-accounts h6 {
        color: #667eea;
        font-weight: 600;
        margin-bottom: 15px;
      }

      .account-item {
        background: white;
        padding: 10px 15px;
        border-radius: 8px;
        margin-bottom: 10px;
        border-left: 4px solid #667eea;
      }

      .account-item:last-child {
        margin-bottom: 0;
      }

      .account-item strong {
        color: #495057;
      }

      .account-item code {
        background: #e9ecef;
        padding: 2px 8px;
        border-radius: 4px;
        color: #764ba2;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="login-container row g-0">
        <!-- Left Image -->
        <div class="col-md-6 d-none d-md-block">
          <div class="login-image"></div>
        </div>

        <!-- Right Form -->
        <div class="col-md-6">
          <div class="login-form">
            <h2 class="login-title">Chào mừng trở lại!</h2>
            <p class="login-subtitle">Đăng nhập để quản lý hồ sơ của bạn</p>

            <!-- Error Message -->
            <c:if test="${not empty error}">
              <div
                class="alert alert-danger alert-dismissible fade show"
                role="alert"
              >
                <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="alert"
                ></button>
              </div>
            </c:if>

            <!-- Login Form -->
            <form
              action="${pageContext.request.contextPath}/login"
              method="post"
            >
              <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input
                  type="email"
                  class="form-control"
                  id="email"
                  name="email"
                  placeholder="example@gmail.com"
                  value="${email}"
                  required
                  autofocus
                />
              </div>

              <div class="mb-4">
                <label for="password" class="form-label">Mật khẩu</label>
                <input
                  type="password"
                  class="form-control"
                  id="password"
                  name="password"
                  placeholder="••••••••"
                  required
                />
              </div>

              <button type="submit" class="btn btn-login">Đăng nhập</button>
            </form>

            <!-- Test Accounts -->
            <div class="test-accounts">
              <h6>🧪 Tài khoản test:</h6>

              <div class="account-item">
                <strong>Email:</strong> <code>customer1@gmail.com</code><br />
                <strong>Pass:</strong> <code>123456</code>
              </div>

              <div class="account-item">
                <strong>Email:</strong> <code>customer2@gmail.com</code><br />
                <strong>Pass:</strong> <code>123456</code>
              </div>

              <div class="account-item">
                <strong>Email:</strong> <code>customer3@gmail.com</code><br />
                <strong>Pass:</strong> <code>123456</code>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
