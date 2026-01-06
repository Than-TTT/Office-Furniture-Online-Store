<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đặt lại mật khẩu</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <style>
      .error-message {
        color: red;
        font-size: 0.875rem;
        margin-top: 0.25rem;
      }
    </style>
  </head>
  <body>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-5">
          <h2 class="text-center mb-4">Đặt lại mật khẩu</h2>

          <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
          </c:if>

          <form
            action="${pageContext.request.contextPath}/customer/reset-password"
            method="POST"
            id="resetForm"
            novalidate
          >
            <div class="form-group">
              <label for="password"
                >Mật khẩu mới <span class="text-danger">*</span></label
              >
              <input
                type="password"
                class="form-control"
                id="password"
                name="password"
                required
                minlength="6"
              />
              <small class="form-text text-muted"
                >Mật khẩu tối thiểu 6 ký tự</small
              >
              <div class="error-message" id="passwordError"></div>
            </div>

            <div class="form-group">
              <label for="confirmPassword"
                >Xác nhận mật khẩu <span class="text-danger">*</span></label
              >
              <input
                type="password"
                class="form-control"
                id="confirmPassword"
                name="confirmPassword"
                required
              />
              <div class="error-message" id="confirmPasswordError"></div>
            </div>

            <button type="submit" class="btn btn-primary btn-block mt-3">
              Xác nhận
            </button>
          </form>

          <p class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/customer/login"
              >Quay lại đăng nhập</a
            >
          </p>
        </div>
      </div>
    </div>

    <script>
      document
        .getElementById("resetForm")
        .addEventListener("submit", function (e) {
          let isValid = true;
          document
            .querySelectorAll(".error-message")
            .forEach((el) => (el.textContent = ""));
          document
            .querySelectorAll(".form-control")
            .forEach((el) => el.classList.remove("is-invalid"));

          const password = document.getElementById("password");
          const confirmPassword = document.getElementById("confirmPassword");

          // Validate password length
          if (password.value.length < 6) {
            document.getElementById("passwordError").textContent =
              "Mật khẩu tối thiểu 6 ký tự";
            password.classList.add("is-invalid");
            isValid = false;
          }

          // Validate confirm password
          if (password.value !== confirmPassword.value) {
            document.getElementById("confirmPasswordError").textContent =
              "Mật khẩu nhập lại không khớp";
            confirmPassword.classList.add("is-invalid");
            isValid = false;
          }

          if (!isValid) {
            e.preventDefault();
          }
        });
    </script>
  </body>
</html>
