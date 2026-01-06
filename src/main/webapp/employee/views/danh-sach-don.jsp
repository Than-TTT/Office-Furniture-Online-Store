<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Đơn Hàng</title>
</head>
<body>
<h5 class="card-header">Danh Sách Đơn Hàng Chưa Hoàn Thành</h5>
<div class="table-responsive text-nowrap">
    <table class="table">
        <thead class="table-dark">
        <tr>
            <th>Mã đơn hàng</th>
            <th>Tên Khách Hàng</th>
            <th>Địa Chỉ</th>
            <th>Số Điện Thoại</th>
            <th>Tổng Tiền</th>
            <th>Trạng Thái</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody class="table-border-bottom-0">
        <!-- Lặp qua các đơn hàng trong request -->
        <c:forEach var="order" items="${orders}">
            <tr>
                <td><strong>${order.orderId}</strong></td>
                <td>${order.customer.name}</td>
                <td>${order.cityOfProvince}</td>
                <td>${order.phone}</td>
                <td>${order.totalCost}</td>
                <td>
                    <span class="badge ${order.status}" style="color: #000;">${order.status}</span>
                </td>
                <td>
                    <div class="dropdown">
                        <button type="button" class="btn p-0 dropdown-toggle hide-arrow"
                                data-bs-toggle="dropdown">
                            <i class="bx bx-dots-vertical-rounded"></i>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="/employee/order-detail?orderId=${order.orderId}"><i class="bx bx-edit-alt me-1"></i> Confirm</a>
                            <a class="dropdown-item" href="deleteOrder.jsp?orderId=${order.orderId}"><i class="bx bx-trash me-1"></i> Delete</a>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
