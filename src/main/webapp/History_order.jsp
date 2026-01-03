<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Lịch Sử Đơn Hàng - Furniture Store</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />

    <style>
        body {
            background-color: #f4f6f9;
        }
        .navbar-custom {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 1rem 0;
        }
        .navbar-custom .navbar-brand {
            color: white;
            font-weight: 700;
        }
        .order-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }
        .order-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
        .status-badge {
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
        }
        .status-pending { background: #fff3cd; color: #856404; }
        .status-processing { background: #cfe2ff; color: #084298; }
        .status-shipping { background: #d1ecf1; color: #0c5460; }
        .status-delivered { background: #d1e7dd; color: #0f5132; }
        .status-cancelled { background: #f8d7da; color: #842029; }
        .btn-review {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
        }
        .btn-review:hover {
            opacity: 0.9;
            color: white;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-custom mb-4">
        <div class="container">
            <a href="${pageContext.request.contextPath}/customer/profile" class="navbar-brand">
                🏢 Furniture Store
            </a>
            <div>
                <span class="text-white me-3">
                    Xin chào, <strong>${sessionScope.customer.name}</strong>
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
                    Đăng xuất
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/customer/profile">Hồ sơ</a>
                </li>
                <li class="breadcrumb-item active">Lịch sử đơn hàng</li>
            </ol>
        </nav>

        <h2 class="mb-4">📦 Lịch Sử Đơn Hàng</h2>

        <!-- Success/Error Messages -->
        <c:if test="${param.success == 'review_added'}">
            <div class="alert alert-success alert-dismissible fade show">
                ✅ Đánh giá của bạn đã được gửi thành công!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${param.error == 'review_failed'}">
            <div class="alert alert-danger alert-dismissible fade show">
                ❌ Không thể gửi đánh giá. Bạn có thể đã đánh giá sản phẩm này rồi hoặc chưa mua hàng.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Filter -->
        <div class="card mb-4">
            <div class="card-body">
                <form method="get" class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Lọc theo trạng thái:</label>
                        <select name="status" class="form-select" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <option value="Pending" ${statusFilter == 'Pending' ? 'selected' : ''}>Chờ xử lý</option>
                            <option value="Processing" ${statusFilter == 'Processing' ? 'selected' : ''}>Đang xử lý</option>
                            <option value="Shipping" ${statusFilter == 'Shipping' ? 'selected' : ''}>Đang giao</option>
                            <option value="Delivered" ${statusFilter == 'Delivered' ? 'selected' : ''}>Đã giao</option>
                            <option value="Cancelled" ${statusFilter == 'Cancelled' ? 'selected' : ''}>Đã hủy</option>
                        </select>
                    </div>
                </form>
            </div>
        </div>

        <!-- Orders List -->
        <c:choose>
            <c:when test="${empty orders}">
                <div class="alert alert-info text-center">
                    <h5>📭 Chưa có đơn hàng nào</h5>
                    <p>Hãy bắt đầu mua sắm để xem lịch sử đơn hàng của bạn!</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="order" items="${orders}">
                    <div class="order-card">
                        <div class="row">
                            <div class="col-md-8">
                                <h5>Đơn hàng #${order.orderId}</h5>
                                <p class="text-muted mb-2">
                                    <small>
                                        Ngày đặt: ${order.formattedOrderDate}
                                    </small>
                                </p>
                                <p class="mb-2">
                                    <strong>Tổng tiền:</strong> 
                                    <span class="text-primary">
                                        <fmt:formatNumber value="${order.totalCost}" type="currency" currencySymbol="₫" />
                                    </span>
                                </p>
                                <p class="mb-0">
                                    <strong>Số lượng sản phẩm:</strong> ${order.orderItems.size()}
                                </p>
                            </div>
                            <div class="col-md-4 text-end">
                                <div class="mb-3">
                                    <c:choose>
                                        <c:when test="${order.status == 'Pending'}">
                                            <span class="status-badge status-pending">⏳ Chờ xử lý</span>
                                        </c:when>
                                        <c:when test="${order.status == 'Processing'}">
                                            <span class="status-badge status-processing">🔄 Đang xử lý</span>
                                        </c:when>
                                        <c:when test="${order.status == 'Shipping'}">
                                            <span class="status-badge status-shipping">🚚 Đang giao</span>
                                        </c:when>
                                        <c:when test="${order.status == 'Delivered'}">
                                            <span class="status-badge status-delivered">✅ Đã giao</span>
                                        </c:when>
                                        <c:when test="${order.status == 'Cancelled'}">
                                            <span class="status-badge status-cancelled">❌ Đã hủy</span>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <a href="${pageContext.request.contextPath}/customer/order-detail?orderId=${order.orderId}" 
                                   class="btn btn-outline-primary btn-sm">
                                    Xem chi tiết
                                </a>
                            </div>
                        </div>

                        <!-- Order Items Preview -->
                        <hr>
                        <div class="row">
                            <c:forEach var="item" items="${order.orderItems}" varStatus="status">
                                <c:if test="${status.index < 3}">
                                    <div class="col-md-4 mb-2">
                                        <div class="d-flex align-items-center">
                                            <div class="flex-grow-1">
                                                <small>
                                                    <strong>${item.product.name}</strong><br>
                                                    SL: ${item.quantity} × 
                                                    <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫" />
                                                </small>
                                            </div>
                                            <!-- Review Button for Delivered Orders -->
                                            <c:if test="${order.status == 'Delivered'}">
                                                <button class="btn btn-review btn-sm ms-2"
                                                        data-bs-toggle="modal"
                                                        data-bs-target="#reviewModal"
                                                        data-product-id="${item.productId}"
                                                        data-product-name="${item.product.name}">
                                                    ⭐ Đánh giá
                                                </button>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <c:if test="${order.orderItems.size() > 3}">
                                <div class="col-12">
                                    <small class="text-muted">... và ${order.orderItems.size() - 3} sản phẩm khác</small>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Review Modal -->
    <div class="modal fade" id="reviewModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">⭐ Đánh Giá Sản Phẩm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="${pageContext.request.contextPath}/customer/add-review" method="post">
                    <div class="modal-body">
                        <input type="hidden" name="productId" id="reviewProductId" />
                        
                        <div class="mb-3">
                            <label class="form-label">Sản phẩm:</label>
                            <p id="reviewProductName" class="fw-bold"></p>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Đánh giá sao:</label>
                            <div class="rating-stars">
                                <input type="radio" name="rating" value="5" id="star5" required />
                                <label for="star5">⭐⭐⭐⭐⭐</label><br>
                                <input type="radio" name="rating" value="4" id="star4" />
                                <label for="star4">⭐⭐⭐⭐</label><br>
                                <input type="radio" name="rating" value="3" id="star3" />
                                <label for="star3">⭐⭐⭐</label><br>
                                <input type="radio" name="rating" value="2" id="star2" />
                                <label for="star2">⭐⭐</label><br>
                                <input type="radio" name="rating" value="1" id="star1" />
                                <label for="star1">⭐</label>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Nhận xét:</label>
                            <textarea class="form-control" name="comment" rows="4" 
                                      placeholder="Chia sẻ trải nghiệm của bạn về sản phẩm..." 
                                      required></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-review">Gửi đánh giá</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const reviewModal = document.getElementById('reviewModal');
        reviewModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const productId = button.getAttribute('data-product-id');
            const productName = button.getAttribute('data-product-name');
            
            document.getElementById('reviewProductId').value = productId;
            document.getElementById('reviewProductName').textContent = productName;
        });
    </script>
</body>
</html>