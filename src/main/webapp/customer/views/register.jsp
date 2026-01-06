<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="decorator" content="none" />
    <title>Đăng ký tài khoản - ERGOFFICE</title>
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
      .register-wrapper {
        width: 100%;
        max-width: 340px;
      }
      .logo-section {
        text-align: center;
        margin-bottom: 15px;
      }
      .logo-section img {
        max-height: 55px;
        margin-bottom: 8px;
      }
      .logo-section h4 {
        color: #333;
        font-weight: 400;
        font-size: 0.95rem;
      }
      .register-card {
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
        padding: 20px 20px;
      }
      .form-group {
        margin-bottom: 10px;
      }
      .form-group label {
        font-weight: 600;
        color: #333;
        margin-bottom: 4px;
        display: block;
        font-size: 12px;
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
      .input-group.is-invalid {
        border-color: #dc3545;
      }
      .input-group-prepend .input-group-text {
        background-color: #f0f7ff;
        border: none;
        color: #4a90d9;
        padding: 8px 10px;
        font-size: 12px;
      }
      .form-control {
        border: none;
        padding: 8px 10px;
        height: auto;
        background-color: #f0f7ff;
        font-size: 13px;
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
        padding: 8px 10px;
        color: #666;
      }
      .input-group-append .input-group-text:hover {
        color: #4a90d9;
      }
      .btn-register {
        background: linear-gradient(135deg, #4a90d9 0%, #667eea 100%);
        border: none;
        border-radius: 6px;
        padding: 10px;
        font-weight: 600;
        font-size: 13px;
        width: 100%;
        margin-top: 6px;
        transition: transform 0.2s, box-shadow 0.2s;
      }
      .btn-register:hover {
        transform: translateY(-2px);
        box-shadow: 0 5px 20px rgba(74, 144, 217, 0.4);
      }
      .links-section {
        text-align: center;
        margin-top: 12px;
        padding-top: 10px;
        border-top: 1px solid #eee;
      }
      .links-section p {
        color: #666;
        margin-bottom: 0;
        font-size: 12px;
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
      .alert {
        border-radius: 6px;
        margin-bottom: 12px;
        font-size: 12px;
        padding: 8px 12px;
      }
      .error-message {
        color: #dc3545;
        font-size: 0.7rem;
        margin-top: 3px;
        display: block;
      }
      .text-danger {
        color: #dc3545 !important;
      }
    </style>
  </head>

  <body>
    <div class="register-wrapper">
      <!-- Logo Section -->
      <div class="logo-section">
        <img
          src="${pageContext.request.contextPath}/customer/assets/images/logo.png"
          alt="ERGOFFICE Logo"
        />
        <h4>Đăng ký tài khoản mới</h4>
      </div>

      <!-- Register Card -->
      <div class="register-card">
        <%-- Hiển thị thông báo lỗi --%>
        <c:if test="${not empty error}">
          <div class="alert alert-danger" role="alert">
            <i class="fas fa-exclamation-circle mr-2"></i>${error}
          </div>
        </c:if>

        <%-- Hiển thị thông báo thành công --%>
        <c:if test="${not empty success}">
          <div class="alert alert-success" role="alert">
            <i class="fas fa-check-circle mr-2"></i>${success}
          </div>
        </c:if>

        <form
          action="${pageContext.request.contextPath}/customer/register"
          method="post"
          id="registerForm"
          novalidate
        >
          <!-- Name Field -->
          <div class="form-group">
            <label for="name">Họ tên <span class="text-danger">*</span></label>
            <div class="input-group" id="nameGroup">
              <div class="input-group-prepend">
                <span class="input-group-text"
                  ><i class="fas fa-user"></i
                ></span>
              </div>
              <input
                type="text"
                class="form-control"
                id="name"
                name="name"
                value="${param.name}"
              />
            </div>
            <span class="error-message" id="nameError"></span>
          </div>

          <!-- Email Field -->
          <div class="form-group">
            <label for="email">Email <span class="text-danger">*</span></label>
            <div class="input-group" id="emailGroup">
              <div class="input-group-prepend">
                <span class="input-group-text"
                  ><i class="fas fa-envelope"></i
                ></span>
              </div>
              <input
                type="email"
                class="form-control"
                id="email"
                name="email"
                value="${param.email}"
              />
            </div>
            <span class="error-message" id="emailError">
              <c:if test="${not empty emailError}">${emailError}</c:if>
            </span>
          </div>

          <!-- Phone Field -->
          <div class="form-group">
            <label for="phonenumber"
              >Số điện thoại <span class="text-danger">*</span></label
            >
            <div class="input-group" id="phoneGroup">
              <div class="input-group-prepend">
                <span class="input-group-text"
                  ><i class="fas fa-phone"></i
                ></span>
              </div>
              <input
                type="tel"
                class="form-control"
                id="phonenumber"
                name="phonenumber"
                value="${param.phonenumber}"
              />
            </div>
            <span class="error-message" id="phoneError">
              <c:if test="${not empty phoneError}">${phoneError}</c:if>
            </span>
          </div>

          <!-- Password Field -->
          <div class="form-group">
            <label for="password"
              >Mật khẩu <span class="text-danger">*</span></label
            >
            <div class="input-group" id="passwordGroup">
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
              />
              <div class="input-group-append">
                <span
                  class="input-group-text"
                  onclick="togglePassword('password', 'toggleIcon1')"
                >
                  <i class="fas fa-eye-slash" id="toggleIcon1"></i>
                </span>
              </div>
            </div>
            <span class="error-message" id="passwordError"></span>
          </div>

          <!-- Confirm Password Field -->
          <div class="form-group">
            <label for="confirmPassword"
              >Xác nhận mật khẩu <span class="text-danger">*</span></label
            >
            <div class="input-group" id="confirmPasswordGroup">
              <div class="input-group-prepend">
                <span class="input-group-text"
                  ><i class="fas fa-lock"></i
                ></span>
              </div>
              <input
                type="password"
                class="form-control"
                id="confirmPassword"
                name="confirmPassword"
              />
              <div class="input-group-append">
                <span
                  class="input-group-text"
                  onclick="togglePassword('confirmPassword', 'toggleIcon2')"
                >
                  <i class="fas fa-eye-slash" id="toggleIcon2"></i>
                </span>
              </div>
            </div>
            <span class="error-message" id="confirmPasswordError"></span>
          </div>

          <button type="submit" class="btn btn-primary btn-register">
            Đăng ký
          </button>
        </form>

        <!-- Links Section -->
        <div class="links-section">
          <p>
            Đã có tài khoản?
            <a href="${pageContext.request.contextPath}/customer/login"
              >Đăng nhập</a
            >
          </p>
        </div>
      </div>
    </div>

    <script>
      function togglePassword(fieldId, iconId) {
        const passwordField = document.getElementById(fieldId);
        const toggleIcon = document.getElementById(iconId);

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

      document
        .getElementById("registerForm")
        .addEventListener("submit", function (e) {
          let isValid = true;

          // Clear previous errors
          document
            .querySelectorAll(".error-message")
            .forEach((el) => (el.textContent = ""));
          document
            .querySelectorAll(".input-group")
            .forEach((el) => el.classList.remove("is-invalid"));

          // Validate name
          const name = document.getElementById("name");
          if (!name.value.trim()) {
            document.getElementById("nameError").textContent =
              "Vui lòng nhập trường này";
            document.getElementById("nameGroup").classList.add("is-invalid");
            isValid = false;
          }

          // Validate email
          const email = document.getElementById("email");
          const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
          if (!email.value.trim()) {
            document.getElementById("emailError").textContent =
              "Vui lòng nhập trường này";
            document.getElementById("emailGroup").classList.add("is-invalid");
            isValid = false;
          } else if (!emailRegex.test(email.value)) {
            document.getElementById("emailError").textContent =
              "Email không hợp lệ";
            document.getElementById("emailGroup").classList.add("is-invalid");
            isValid = false;
          }

          // Validate phone
          const phone = document.getElementById("phonenumber");
          const phoneRegex = /^(0[3|5|7|8|9])+([0-9]{8})$/;
          if (!phone.value.trim()) {
            document.getElementById("phoneError").textContent =
              "Vui lòng nhập trường này";
            document.getElementById("phoneGroup").classList.add("is-invalid");
            isValid = false;
          } else if (!phoneRegex.test(phone.value)) {
            document.getElementById("phoneError").textContent =
              "Số điện thoại không hợp lệ";
            document.getElementById("phoneGroup").classList.add("is-invalid");
            isValid = false;
          }

          // Validate password
          const password = document.getElementById("password");
          if (!password.value) {
            document.getElementById("passwordError").textContent =
              "Vui lòng nhập trường này";
            document
              .getElementById("passwordGroup")
              .classList.add("is-invalid");
            isValid = false;
          } else if (password.value.length < 6) {
            document.getElementById("passwordError").textContent =
              "Mật khẩu tối thiểu 6 ký tự";
            document
              .getElementById("passwordGroup")
              .classList.add("is-invalid");
            isValid = false;
          }

          // Validate confirm password
          const confirmPassword = document.getElementById("confirmPassword");
          if (!confirmPassword.value) {
            document.getElementById("confirmPasswordError").textContent =
              "Vui lòng nhập trường này";
            document
              .getElementById("confirmPasswordGroup")
              .classList.add("is-invalid");
            isValid = false;
          } else if (password.value !== confirmPassword.value) {
            document.getElementById("confirmPasswordError").textContent =
              "Mật khẩu nhập lại không khớp";
            document
              .getElementById("confirmPasswordGroup")
              .classList.add("is-invalid");
            isValid = false;
          }

          if (!isValid) {
            e.preventDefault();
          }
        });
    </script>
  </body>
</html>
