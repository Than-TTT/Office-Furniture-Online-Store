<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title><jsp:doBody /></title>

    <!-- Bootstrap -->
    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />

    <!-- CSS chung -->
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/main.css"
    />
  </head>
  <body>
    <!-- NAVBAR -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/home">
        Office Furniture
      </a>

      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNav"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/products/search"
              >Sản phẩm</a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Giỏ hàng</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Đăng nhập</a>
          </li>
        </ul>
      </div>
    </nav>
  </body>
</html>
