<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Order History</title>

    <!-- Bootstrap 5 CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />

    <!-- Font Awesome -->
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    />

    <style>
      body {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        min-height: 100vh;
        padding: 20px 0;
      }

      .container {
        background: white;
        border-radius: 20px;
        padding: 30px;
        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
      }

      h1 {
        color: #667eea;
        font-weight: 700;
        margin-bottom: 30px;
        text-align: center;
      }

      .table {
        margin-top: 20px;
      }

      .badge {
        padding: 8px 15px;
        font-size: 0.85rem;
      }

      .btn-review {
        transition: all 0.3s ease;
      }

      .btn-review:hover {
        transform: scale(1.05);
      }

      .modal-header {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
      }

      .btn-close {
        filter: brightness(0) invert(1);
      }

      .empty-state {
        text-align: center;
        padding: 60px 20px;
        color: #6c757d;
      }

      .empty-state i {
        font-size: 4rem;
        margin-bottom: 20px;
        opacity: 0.5;
      }

      .product-list {
        font-size: 0.9rem;
      }
    </style>
  </head>
  <body>
    <div class="container mt-5">
      <h1><i class="fas fa-history"></i> Order History</h1>

      <!-- Toast Notifications -->
      <div
        class="toast-container position-fixed bottom-0 end-0 p-3"
        style="z-index: 11"
      >
        <div
          id="toastSuccess"
          class="toast"
          role="alert"
          aria-live="assertive"
          aria-atomic="true"
        >
          <div class="toast-header bg-success text-white">
            <i class="fas fa-check-circle me-2"></i>
            <strong class="me-auto">Success</strong>
            <button
              type="button"
              class="btn-close btn-close-white"
              data-bs-dismiss="toast"
              aria-label="Close"
            ></button>
          </div>
          <div class="toast-body">Review submitted successfully!</div>
        </div>

        <div
          id="toastError"
          class="toast"
          role="alert"
          aria-live="assertive"
          aria-atomic="true"
        >
          <div class="toast-header bg-danger text-white">
            <i class="fas fa-exclamation-circle me-2"></i>
            <strong class="me-auto">Error</strong>
            <button
              type="button"
              class="btn-close btn-close-white"
              data-bs-dismiss="toast"
              aria-label="Close"
            ></button>
          </div>
          <div class="toast-body">
            Failed to submit review. Please try again.
          </div>
        </div>
      </div>

      <c:choose>
        <c:when test="${not empty orderHistory}">
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead class="table-dark">
                <tr>
                  <th><i class="fas fa-hashtag"></i> Order ID</th>
                  <th><i class="fas fa-box"></i> Products</th>
                  <th><i class="fas fa-calendar"></i> Date</th>
                  <th><i class="fas fa-info-circle"></i> Status</th>
                  <th><i class="fas fa-dollar-sign"></i> Total</th>
                  <th><i class="fas fa-cog"></i> Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="order" items="${orderHistory}">
                  <tr>
                    <td><strong>#${order.orderId}</strong></td>
                    <td class="product-list">
                      <c:choose>
                        <c:when test="${not empty order.orderItems}">
                          <c:forEach var="item" items="${order.orderItems}" varStatus="status">
                            <c:if test="${not empty item.productType && not empty item.productType.product}">
                              ${item.productType.product.name}
                              <c:if test="${!status.last}">, </c:if>
                            </c:if>
                          </c:forEach>
                        </c:when>
                        <c:otherwise>
                          <span class="text-muted">No products</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>${order.orderDate}</td>
                    <td>
                      <c:choose>
                        <c:when test="${order.status == 'DELIVERED'}">
                          <span class="badge bg-success">
                            <i class="fas fa-check"></i> ${order.status}
                          </span>
                        </c:when>
                        <c:when test="${order.status == 'PENDING'}">
                          <span class="badge bg-warning text-dark">
                            <i class="fas fa-clock"></i> ${order.status}
                          </span>
                        </c:when>
                        <c:when test="${order.status == 'CANCELLED'}">
                          <span class="badge bg-danger">
                            <i class="fas fa-times"></i> ${order.status}
                          </span>
                        </c:when>
                        <c:otherwise>
                          <span class="badge bg-info">
                            <i class="fas fa-shipping-fast"></i> ${order.status}
                          </span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td><strong>$${order.totalCost}</strong></td>
                    <td>
                      <c:choose>
                        <c:when test="${order.status == 'DELIVERED' && not empty order.orderItems}">
                          <button
                            class="btn btn-warning btn-sm btn-review"
                            data-bs-toggle="modal"
                            data-bs-target="#reviewModal"
                            data-order-id="${order.orderId}"
                            data-product-id="${order.orderItems[0].productType.product.productId}"
                            data-order-date="${order.orderDate}"
                            data-order-status="${order.status}"
                          >
                            <i class="fas fa-star"></i> Review
                          </button>
                        </c:when>
                        <c:otherwise>
                          <button class="btn btn-secondary btn-sm" disabled>
                            <i class="fas fa-star"></i> Review
                          </button>
                        </c:otherwise>
                      </c:choose>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:when>
        <c:otherwise>
          <div class="empty-state">
            <i class="fas fa-shopping-bag"></i>
            <h3>No Orders Yet</h3>
            <p>You haven't placed any orders yet.</p>
            
              href="${pageContext.request.contextPath}/customer/products"
              class="btn btn-primary mt-3"
            >
              <i class="fas fa-shopping-cart"></i> Start Shopping
            </a>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Modal Review -->
    <div
      class="modal fade"
      id="reviewModal"
      tabindex="-1"
      aria-labelledby="reviewModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="reviewModalLabel">
              <i class="fas fa-star"></i> Write a Review
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form
              action="${pageContext.request.contextPath}/review/add"
              method="post"
              id="reviewForm"
            >
              <input type="hidden" id="reviewOrderId" name="orderId" />
              <input type="hidden" id="reviewProductId" name="productId" />

              <div class="mb-3">
                <label class="form-label fw-bold">Order Information</label>
                <div class="alert alert-info">
                  <i class="fas fa-info-circle"></i> Order ID:
                  <span id="displayOrderId"></span>
                </div>
              </div>

              <div class="mb-3">
                <label for="rating" class="form-label fw-bold">
                  <i class="fas fa-star text-warning"></i> Rating
                </label>
                <select class="form-select" id="rating" name="rating" required>
                  <option value="">Select Rating</option>
                  <option value="5">(5 - Excellent)</option>
                  <option value="4">(4 - Good)</option>
                  <option value="3">(3 - Average)</option>
                  <option value="2">(2 - Poor)</option>
                  <option value="1">(1 - Very Poor)</option>
                </select>
              </div>

              <div class="mb-3">
                <label for="content" class="form-label fw-bold">
                  <i class="fas fa-comment"></i> Review Content
                </label>
                <textarea
                  class="form-control"
                  id="content"
                  name="content"
                  rows="4"
                  placeholder="Share your experience with this product..."
                  required
                ></textarea>
                <small class="text-muted">Minimum 10 characters</small>
              </div>

              <div class="modal-footer border-0">
                <button
                  type="button"
                  class="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  <i class="fas fa-times"></i> Cancel
                </button>
                <button type="submit" class="btn btn-primary">
                  <i class="fas fa-paper-plane"></i> Submit Review
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Bootstrap 5 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
      document.addEventListener("DOMContentLoaded", function () {
        // Kiểm tra URL params cho toast notification
        const urlParams = new URLSearchParams(window.location.search);
        const reviewStatus = urlParams.get("reviewStatus");

        // Hiển thị toast thông báo
        if (reviewStatus === "success") {
          const toastEl = document.getElementById("toastSuccess");
          const toast = new bootstrap.Toast(toastEl);
          toast.show();

          // Xóa param khỏi URL
          window.history.replaceState(
            {},
            document.title,
            window.location.pathname
          );
        } else if (reviewStatus === "error") {
          const toastEl = document.getElementById("toastError");
          const toast = new bootstrap.Toast(toastEl);
          toast.show();

          // Xóa param khỏi URL
          window.history.replaceState(
            {},
            document.title,
            window.location.pathname
          );
        }

        // Xử lý khi modal được mở
        const reviewModal = document.getElementById("reviewModal");
        reviewModal.addEventListener("show.bs.modal", function (event) {
          // Lấy button đã kích hoạt modal
          const button = event.relatedTarget;

          // Lấy dữ liệu từ data attributes
          const orderId = button.getAttribute("data-order-id");
          const productId = button.getAttribute("data-product-id");

          // Debug log
          console.log("Order ID:", orderId);
          console.log("Product ID:", productId);

          // Gán giá trị vào form
          document.getElementById("reviewOrderId").value = orderId;
          document.getElementById("reviewProductId").value = productId;
          document.getElementById("displayOrderId").textContent = "#" + orderId;
        });

        // Validate form trước khi submit
        const reviewForm = document.getElementById("reviewForm");
        reviewForm.addEventListener("submit", function (e) {
          const content = document.getElementById("content").value;
          const rating = document.getElementById("rating").value;
          const productId = document.getElementById("reviewProductId").value;

          if (content.length < 10) {
            e.preventDefault();
            alert("Review content must be at least 10 characters long!");
            return false;
          }

          if (!rating) {
            e.preventDefault();
            alert("Please select a rating!");
            return false;
          }

          if (!productId) {
            e.preventDefault();
            alert("Product ID is missing!");
            return false;
          }

          // Debug log trước khi submit
          console.log("Submitting review:", {
            orderId: document.getElementById("reviewOrderId").value,
            productId: productId,
            rating: rating,
            content: content
          });
        });
      });
    </script>
  </body>
</html>