<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Customer Home</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .banner { 
      height: 50vh; 
      background: url('https://via.placeholder.com/1200x400?text=Welcome+to+Our+Store') center/cover no-repeat; 
      display: flex; 
      align-items: center; 
      justify-content: center; 
      color: white; 
      font-size: 3rem; 
      font-weight: bold; 
      text-shadow: 2px 2px 5px #000; 
    }
    .card-img-top { 
      height: 200px; 
      object-fit: cover; 
    }
  </style>
</head>
<body>

<!-- Banner giới thiệu cửa hàng -->
<div class="banner">
  Chào mừng đến với Cửa Hàng Của Chúng Tôi
</div>

<div class="container mt-5">
  <!-- Hiển thị các category -->
  <h2 class="mb-4">Danh mục nổi bật</h2>
  <div class="row">
    <c:forEach var="category" items="${categories}" end="3">
      <div class="col-md-3 mb-4">
        <div class="card h-100 shadow-sm text-center">
          <div class="card-body">
            <h5 class="card-title">${category.categoryName}</h5>
            <a href="${pageContext.request.contextPath}/products/category/${category.categoryId}" class="btn btn-sm btn-primary">Xem sản phẩm</a>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <!-- Hiển thị sản phẩm nổi bật -->
  <h2 class="mb-4 mt-5">Sản phẩm nổi bật</h2>
  <div class="row">
        <c:forEach var="product" items="${products}" end="9">
      <div class="col-md-3 mb-4">
        <div class="card h-100 shadow-sm">
          
          <!-- DEBUG: Hiển thị thông tin -->
          <div style="background: yellow; padding: 5px; font-size: 11px; word-break: break-all;">
            <strong>DEBUG INFO:</strong><br>
            Tên sản phẩm: ${product.name}<br>
            Số ảnh: ${not empty product.productImages ? product.productImages.size() : 0}<br>
            <c:if test="${not empty product.productImages}">
              Tên file: ${product.productImages[0].productImage}<br>
              URL đầy đủ: ${pageContext.request.contextPath}/uploads/${product.productImages[0].productImage}
            </c:if>
          </div>
          
          <c:choose>
            <c:when test="${not empty product.productImages}">
              <img src="${pageContext.request.contextPath}/uploads/${product.productImages[0].productImage}" 
                  class="card-img-top" alt="${product.name}"
                  onerror="console.log('Image load error:', this.src); this.onerror=null; this.src='https://via.placeholder.com/300x200?text=Error';">
            </c:when>
            <c:otherwise>
              <img src="https://via.placeholder.com/300x200?text=No+Image" 
                  class="card-img-top" alt="No Image">
            </c:otherwise>
          </c:choose>

          <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text text-truncate">${product.descript}</p>
            <p class="card-text text-danger font-weight-bold">
              Giá: 
              <c:choose>
                <c:when test="${not empty product.productTypes}">
                  ${product.productTypes[0].price} VND
                </c:when>
                <c:otherwise>Liên hệ</c:otherwise>
              </c:choose>
            </p>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <div class="text-center mt-4">
    <a href="${pageContext.request.contextPath}/products/search" class="btn btn-primary px-5">Xem tất cả sản phẩm</a>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>