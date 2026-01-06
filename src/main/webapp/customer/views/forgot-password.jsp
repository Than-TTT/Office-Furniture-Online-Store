<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Quên mật khẩu</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <style>
      .error-message {
        color: red;
      }
    </style>
  </head>
  <body>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-5">
          <h2 class="text-center mb-4">Quên mật khẩu</h2>
          <p class="text-muted text-center">
            Nhập email đã đăng ký để nhận mã xác nhận
          </p>

          <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
          </c:if>

          <form
            action="${pageContext.request.contextPath}/customer/forgot-password"
            method="POST"
            id="forgotForm"
          >
            <div class="form-group">
              <label for="email"
                >Email <span class="text-danger">*</span></label
              >
              <input
                type="email"
                class="form-control"
                id="email"
                name="email"
                value="${param.email}"
                required
              />
            </div>

            <button type="submit" class="btn btn-primary btn-block mt-3">
              Gửi mã xác nhận
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
  </body>
</html>
