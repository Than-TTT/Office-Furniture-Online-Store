<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer List</title>
    <!-- Nhúng jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Nhúng Bootstrap nếu cần -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1>Order History</h1>
    <!-- Hiển thị Toast -->
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="toastSuccess" class="toast bg-success text-white" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-body">
                Review submitted successfully!
            </div>
        </div>
        <div id="toastError" class="toast bg-danger text-black" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-body">
                Failed to submit review. Please try again.
            </div>
        </div>
    </div>
    <table class="table table-bordered">
        <thead class="table-dark">
        <tr>
            <th>Order ID</th>
            <th>Date</th>
            <th>Status</th>
            <th>Total</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty orderHistory}">
            <c:forEach var="order" items="${orderHistory}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.status}</td>
                    <td>${order.totalCost}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.status == 'Delivered'}">
                                <!-- Nút Review hiển thị nếu status là 'Delivered' -->
                                <button class="btn btn-warning btn-sm review-btn" data-bs-toggle="modal" data-bs-target="#reviewModal"
                                        data-order-id="${order.orderId}"
                                        data-order-date="${order.orderDate}"
                                        data-order-status="${order.status}"
                                        >Review
                                </button>
                            </c:when>
                            <c:otherwise>
                                <!-- Nút Review bị vô hiệu hóa nếu không phải 'Delivered' -->
                                <button class="btn btn-secondary btn-sm" disabled>Review</button>
                            </c:otherwise>
                        </c:choose>

                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

    <!-- Pagination -->
<%--    <nav>--%>
<%--        <ul class="pagination">--%>
<%--            <c:forEach var="i" begin="1" end="${totalPages}">--%>
<%--                <li class="page-item ${i == currentPage ? 'active' : ''}">--%>
<%--                    <a class="page-link" href="${pageContext.request.contextPath}/admin/customer?page=${i}">${i}</a>--%>
<%--                </li>--%>
<%--            </c:forEach>--%>
<%--        </ul>--%>
<%--    </nav>--%>

    <!-- Modal Review -->
    <div class="modal fade" id="reviewModal" tabindex="-1" aria-labelledby="reviewModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="reviewModalLabel">Review Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/review/add" method="post">
                        <input type="hidden" id="reviewOrderId" name="orderId">
                        <input type="hidden" id="reviewProductId" name="productId">
                        <div class="mb-3">
                            <label for="content" class="form-label">Review Content</label>
                            <textarea class="form-control" id="content" name="content" rows="3" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="rating" class="form-label">Rating</label>
                            <select class="form-control" id="rating" name="rating" required>
                                <option value="5">5 - Excellent</option>
                                <option value="4">4 - Good</option>
                                <option value="3">3 - Average</option>
                                <option value="2">2 - Poor</option>
                                <option value="1">1 - Very Poor</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit Review</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const urlParams = new URLSearchParams(window.location.search);
            const reviewStatus = urlParams.get('reviewStatus');

            // Hiển thị toast thông báo
            if (reviewStatus === 'success') {
                const toast = new bootstrap.Toast(document.getElementById('toastSuccess'));
                toast.show();
            } else if (reviewStatus === 'error') {
                const toast = new bootstrap.Toast(document.getElementById('toastError'));
                toast.show();
            }

            // Gán giá trị vào modal khi nhấn Review
            const reviewModal = document.getElementById('reviewModal');
            reviewModal.addEventListener('show.bs.modal', function (event) {
                const button = event.relatedTarget;
                document.getElementById('reviewOrderId').value = button.getAttribute('data-order-id');
                // document.getElementById('reviewProductId').value = button.getAttribute('data-product-id');
            });
        });

    </script>
</div>
</body>
</html>
