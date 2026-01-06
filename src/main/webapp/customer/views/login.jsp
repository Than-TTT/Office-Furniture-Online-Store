<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="decorator" content="none" />
    <title>Đăng nhập - ERGOFFICE</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
    />
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      html,
      body {
        height: 100%;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      }
      body {
        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        min-height: 100vh;
        padding: 20px;
      }
      .login-wrapper {
        width: 100%;
        max-width: 420px;
      }
      .logo-section {
        text-align: center;
        margin-bottom: 30px;
      }
      .logo-section img {
        max-height: 100px;
        margin-bottom: 15px;
      }
      .logo-section h4 {
        color: #333;
        font-weight: 400;
        font-size: 1.3rem;
      }
      .login-card {
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
        padding: 35px 30px;
      }
      .form-group {
        margin-bottom: 20px;
      }
      .form-group label {
        font-weight: 600;
        color: #333;
        margin-bottom: 10px;
        display: block;
        font-size: 14px;
      }
      .input-group {
        border: 2px solid #e0e0e0;
        border-radius: 8px;
        overflow: hidden;
        transition: border-color 0.3s;
      }
      .input-group:focus-within {
        border-color: #4a90d9;
      }
      .input-group-prepend .input-group-text {
        background-color: #f0f7ff;
        border: none;
        color: #4a90d9;
        padding: 12px 15px;
        font-size: 16px;
      }
      .form-control {
        border: none;
        padding: 12px 15px;
        height: auto;
        background-color: #f0f7ff;
        font-size: 15px;
      }
      .form-control:focus {
        box-shadow: none;
        background-color: #f0f7ff;
      }
      .form-control::placeholder {
        color: #999;
      }
      .input-group-append .input-group-text {
        background-color: #f0f7ff;
        border: none;
        cursor: pointer;
        padding: 12px 15px;
        color: #666;
      }
      .input-group-append .input-group-text:hover {
        color: #4a90d9;
      }
      .btn-login {
        background: linear-gradient(135deg, #4a90d9 0%, #667eea 100%);
        border: none;
        border-radius: 8px;
        padding: 14px;
        font-weight: 600;
        font-size: 16px;
        width: 100%;
        margin-top: 10px;
        transition: transform 0.2s, box-shadow 0.2s;
      }
      .btn-login:hover {
        transform: translateY(-2px);
        box-shadow: 0 5px 20px rgba(74, 144, 217, 0.4);
      }
      .links-section {
        text-align: center;
        margin-top: 25px;
        padding-top: 20px;
        border-top: 1px solid #eee;
      }
      .links-section p {
        color: #666;
        margin-bottom: 12px;
        font-size: 14px;
      }
      .links-section a {
        color: #4a90d9;
        text-decoration: none;
        font-weight: 600;
      }
      .links-section a:hover {
        text-decoration: underline;
        color: #667eea;
      }
      .forgot-link {
        display: block;
        margin-top: 8px;
      }
      .alert {
        border-radius: 8px;
        margin-bottom: 20px;
        font-size: 14px;
      }
    </style>
  </head>

  <body>
    <div class="login-wrapper">
      <!-- Logo Section -->
      <div class="logo-section">
        <img
          src="${pageContext.request.contextPath}/customer/assets/images/logo.png"
          alt="ERGOFFICE Logo"
        />
        <h4>Đăng nhập vào ERGOFFICE</h4>
      </div>

      <!-- Login Card -->
      <div class="login-card">
        <%-- Hiển thị thông báo lỗi --%> <% if (request.getAttribute("error") !=
        null) { %>
        <div class="alert alert-danger" role="alert">
          <i class="fas fa-exclamation-circle mr-2"></i><%=
          request.getAttribute("error") %>
        </div>
        <% } %> <%-- Hiển thị thông báo thành công --%> <% if
        (session.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success" role="alert">
          <i class="fas fa-check-circle mr-2"></i><%=
          session.getAttribute("successMessage") %>
        </div>
        <% session.removeAttribute("successMessage"); %> <% } %>

        <form
          action="${pageContext.request.contextPath}/customer/login"
          method="post"
          id="loginForm"
        >
          <!-- Email Field -->
          <div class="form-group">
            <label for="email">Email</label>
            <div class="input-group">
              <div class="input-group-prepend">
                <span class="input-group-text"
                  ><i class="fas fa-envelope"></i
                ></span>
              </div>
              <input type="email" class="form-control" id="email" name="email"
              placeholder="Nhập email của bạn" value="<%=
              request.getAttribute("email") != null ?
              request.getAttribute("email") : "" %>">
            </div>
          </div>

          <!-- Password Field -->
          <div class="form-group">
            <label for="password">Mật khẩu</label>
            <div class="input-group">
              <div class="input-group-prepend">
                <span class="input-group-text"
                  ><i class="fas fa-lock"></i
                ></span>
              </div>
              <input
                type="password"
                class="form-control"
                id="password"
                name="password"
                placeholder="Nhập mật khẩu"
              />
              <div class="input-group-append">
                <span
                  class="input-group-text"
                  onclick="togglePassword()"
                  title="Hiện/Ẩn mật khẩu"
                >
                  <i class="fas fa-eye-slash" id="toggleIcon"></i>
                </span>
              </div>
            </div>
          </div>

          <button type="submit" class="btn btn-primary btn-login">
            Đăng nhập
          </button>
        </form>

        <!-- Links Section -->
        <div class="links-section">
          <p>
            Bạn chưa có tài khoản?
            <a href="${pageContext.request.contextPath}/customer/register"
              >Đăng ký!</a
            >
          </p>
          <a
            href="${pageContext.request.contextPath}/customer/forgot-password"
            class="forgot-link"
            >Quên mật khẩu?</a
          >
        </div>
      </div>
    </div>

    <script>
      function togglePassword() {
        const passwordField = document.getElementById("password");
        const toggleIcon = document.getElementById("toggleIcon");

        if (passwordField.type === "password") {
          passwordField.type = "text";
          toggleIcon.classList.remove("fa-eye-slash");
          toggleIcon.classList.add("fa-eye");
        } else {
          passwordField.type = "password";
          toggleIcon.classList.remove("fa-eye");
          toggleIcon.classList.add("fa-eye-slash");
        }
      }
    </script>
  </body>
</html>
