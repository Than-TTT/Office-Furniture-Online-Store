<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Xác nhận OTP</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <style>
      .countdown {
        font-size: 1.2rem;
        color: #dc3545;
        font-weight: bold;
      }
      .countdown.expired {
        color: #6c757d;
      }
    </style>
  </head>
  <body>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-5">
          <h2 class="text-center mb-4">Xác nhận mã OTP</h2>
          <p class="text-muted text-center">
            Nhập mã xác nhận đã được gửi đến email của bạn
          </p>

          <div class="text-center mb-3">
            <span>Mã còn hiệu lực trong: </span>
            <span id="countdown" class="countdown">90</span>
            <span> giây</span>
          </div>

          <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
          </c:if>
          <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
          </c:if>

          <form
            action="${pageContext.request.contextPath}/customer/verify-otp"
            method="POST"
            id="otpForm"
          >
            <div class="form-group">
              <label for="otp"
                >Mã xác nhận <span class="text-danger">*</span></label
              >
              <input
                type="text"
                class="form-control text-center"
                id="otp"
                name="otp"
                maxlength="6"
                required
                placeholder="Nhập mã 6 số"
              />
            </div>

            <button
              type="submit"
              class="btn btn-primary btn-block mt-3"
              id="verifyBtn"
            >
              Xác nhận
            </button>
          </form>

          <div class="text-center mt-3">
            <a
              href="${pageContext.request.contextPath}/customer/resend-otp"
              id="resendLink"
              class="btn btn-outline-secondary"
              style="display: none"
              >Gửi lại mã</a
            >
          </div>

          <p class="text-center mt-3">
            <a
              href="${pageContext.request.contextPath}/customer/forgot-password"
              >Quay lại</a
            >
          </p>
        </div>
      </div>
    </div>

    <script>
      let timeLeft = 90;
      const countdownEl = document.getElementById("countdown");
      const resendLink = document.getElementById("resendLink");
      const verifyBtn = document.getElementById("verifyBtn");

      const countdown = setInterval(function () {
        timeLeft--;
        countdownEl.textContent = timeLeft;

        if (timeLeft <= 0) {
          clearInterval(countdown);
          countdownEl.textContent = "0";
          countdownEl.classList.add("expired");
          resendLink.style.display = "inline-block";
          verifyBtn.disabled = true;
          verifyBtn.textContent = "Mã đã hết hạn";
        }
      }, 1000);

      <c:if test="${showResend}">
        clearInterval(countdown); countdownEl.textContent = '0';
        countdownEl.classList.add('expired'); resendLink.style.display =
        'inline-block'; verifyBtn.disabled = true; verifyBtn.textContent = 'Mã
        đã hết hạn';
      </c:if>;
    </script>
  </body>
</html>
