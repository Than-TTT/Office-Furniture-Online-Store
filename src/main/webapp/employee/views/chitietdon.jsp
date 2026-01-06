<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Xác nhận đơn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container my-5">
    <h5 class="mb-4">Chi tiết hóa đơn - Mã đơn hàng: ${orderId}</h5>
    <div class="mb-4">
        <p><strong>Khách hàng:</strong> ${order.customer.name}</p> <!-- Tên khách hàng -->
        <p><strong>Địa chỉ:</strong> ${order.cityOfProvince}</p> <!-- Địa chỉ khách hàng -->
        <p><strong>Số điện thoại:</strong> ${order.phone}</p> <!-- Số điện thoại khách hàng -->
        <p><strong>Trạng thái:</strong> ${order.status}</p>
    </div>
    <!-- Bảng chi tiết hóa đơn -->
    <div class="table-responsive text-nowrap">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Tên sản phẩm</th>
                <th>Số lượng</th>
                <th>Khối lượng</th>
                <th>Màu sắc</th>
                <th>Chất liệu</th>
                <th>Đơn giá</th>
                <th>Tổng tiền</th>
            </tr>
            </thead>
            <tbody class="table-border-bottom-0">
            <!-- Lặp qua danh sách OrderItem và hiển thị thông tin -->
            <c:forEach var="item" items="${orderItems}">
                <tr>
                    <td><strong>${item.productType.product.name}</strong></td>
                    <td>${item.quantity}</td> <!-- Số lượng -->
                    <td>${item.productType.weight}</td>
                    <td>${item.productType.color}</td>
                    <td>${item.productType.material}</td>
                    <td>${item.price}đ</td> <!-- Đơn giá -->
                    <td>${item.quantity * item.price}đ</td>
                </tr>
            </c:forEach>
            <!-- Hiển thị tổng thanh toán -->
            <tr>
                <td colspan="6" class="text-end"><strong>Tổng thanh toán</strong></td>
                <td><strong>${totalAmount}đ</strong></td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- Các hành động xác nhận đơn hàng -->
    <div class="d-flex justify-content-between">
        <form action="/employee/order-detail?orderId=${orderId}" method="post">
            <input type="hidden" name="orderId" value="${orderId}">
            <button type="submit" class="btn btn-success" id="btnXacNhan">Xác nhận đơn hàng</button>
        </form>
        <form action="cancelOrder" method="post">
            <button type="submit" class="btn btn-danger">Hủy đơn hàng</button>
        </form>
    </div>
</div>

<!-- Tải các script của Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('btnXacNhan').addEventListener('click', function() {
        // Hiển thị thông báo xác nhận
        alert('Đơn hàng đã được xác nhận thành công!');
        // Reload lại trang sau 1 giây
        setTimeout(function() {
            window.location.reload();
        }, 1000);
    });
</script>
</body>
</html>
