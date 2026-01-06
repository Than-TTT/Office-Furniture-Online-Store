<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- Cực kỳ quan trọng cho Tomcat 11: Dùng URI jakarta --%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Customer Home</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .carousel-item { height: 60vh; background-color: #eee; } /* Giảm chiều cao để dễ nhìn */
    .carousel-item img { height: 100%; width: 100%; object-fit: cover; }
    .card-img-top { height: 200px; object-fit: cover; }
  </style>
</head>
<body>

<div id="campaignCarousel" class="carousel slide" data-ride="carousel">
  <div class="carousel-inner">
    <c:forEach var="campaign" items="${marketingCampaigns}" varStatus="status">
      <div class="carousel-item ${status.first ? 'active' : ''}">
        <%-- Kiểm tra nếu campaign có ảnh mới hiển thị --%>
        <c:choose>
            <c:when test="${not empty campaign.campaignImages}">
                <img src="${campaign.campaignImages[0].imagePath}" class="d-block w-100" alt="Campaign Image">
            </c:when>
            <c:otherwise>
                <div class="d-flex align-items-center justify-content-center h-100">
                    <p>No Image Available</p>
                </div>
            </c:otherwise>
        </c:choose>
        
        <div class="carousel-caption d-none d-md-block" style="background: rgba(0,0,0,0.5)">
          <h5>Campaign ${campaign.campaignId}</h5>
          <p>${campaign.content}</p>
        </div>
      </div>
    </c:forEach>
  </div>
  <a class="carousel-control-prev" href="#campaignCarousel" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
  </a>
  <a class="carousel-control-next" href="#campaignCarousel" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
  </a>
</div>

<div class="container mt-5">
  <h2 class="mb-4">Sản phẩm nổi bật</h2>
  <div class="row">
    <c:forEach var="product" items="${products}" end="9">
      <div class="col-md-3 mb-4">
        <div class="card h-100 shadow-sm">
          <%-- Kiểm tra ảnh sản phẩm --%>
          <c:choose>
              <c:when test="${not empty product.productImages}">
                  <img src="${product.productImages[0].productImage}" class="card-img-top" alt="${product.name}">
              </c:when>
              <c:otherwise>
                  <img src="https://via.placeholder.com/300x200?text=No+Image" class="card-img-top" alt="No Image">
              </c:otherwise>
          </c:choose>
          
          <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text text-truncate">${product.descript}</p>
            
            <%-- Kiểm tra giá (truy cập productTypes an toàn) --%>
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