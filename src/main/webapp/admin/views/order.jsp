<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn hàng</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2 class="my-4">Danh sách đơn hàng</h2>

    <!-- Product List Table -->
    <table class="table table-bordered table-hover mt-3">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Order Date</th>
            <th>Status</th>
            <th>Province</th>
            <th>District</th>
            <th>Ward</th>
            <th>Address</th>
            <th>Phone Number</th>
            <th>Total Cost</th>
            <th>Discount</th>
            <th>Actual Cost</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <!-- Using c:forEach to iterate through the product list -->
        <c:forEach var="order" items="${listOrder}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.orderDate}</td>
                <td>${order.status}</td>
                <td>${order.cityOfProvince}</td>
                <td>${order.district}</td>
                <td>${order.ward}</td>
                <td>${order.streetNumber}</td>
                <td>${order.phone}</td>
                <td>${order.totalCost}</td>
                <td>${order.discount}</td>
                <td>${order.actualCost}</td>
                <td>
                    <div class="btn-group" role="group">
                        <!-- Nút Edit -->
                        <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#modalEditOrder${order.orderId}">Edit</button>
                        <div class="modal fade" id="modalEditOrder${order.orderId}" tabindex="-1" aria-labelledby="modalProductModal${order.orderId}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editProductModalLabel${order.orderId}">Confirm Edit</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="order/editstatus" method="post">
                                            <input type="hidden" name="currentPage" value="${currentPage}">
                                            <input type="hidden" name="orderId" value="${order.orderId}">
                                            <input type="text" name="status" value="${order.status}">
                                            <button type="submit" class="btn btn-primary">Confirm</button>
                                        </form>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    </div>
                                    <div class="modal-footer">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <!-- Display message if the product list is empty -->
        <c:if test="${empty listOrder}">
            <tr>
                <td colspan="6" class="text-center">No order available!</td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <div class="col-auto">
        <a href="home" class="btn btn-primary">Back to Home</a>
    </div>


    <!-- Pagination Links -->
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:if test='${currentPage > 1}'>
                <li class="active">
                    <a class="page-link" href="?page=${currentPage - 1}">Previous</a>
                </li>
            </c:if>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item <c:if test='${currentPage == i}'>active</c:if>'">
                    <a class="page-link" href="?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <c:if test='${currentPage < totalPages}'>
                <li class="active">
                    <a class="page-link" href="?page=${currentPage + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>



<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
