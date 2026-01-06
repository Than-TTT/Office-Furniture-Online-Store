<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .cart-item--out-stock { opacity: 0.5; pointer-events: none; }
        .cart-item--out-stock::after { content: "Hết hàng"; color: red; font-weight: bold; }
        .cart-summary { background: #f8f9fa; padding: 20px; border-radius: 8px; }
        .total-price { font-size: 1.5rem; font-weight: bold; color: #28a745; }
    </style>
</head>
<body>

<!-- Modal xóa tất cả -->
<div class="modal fade" id="deleteAllModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xóa tất cả sản phẩm đã chọn?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-danger" id="confirmDeleteAll" data-bs-dismiss="modal">Xác nhận</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal xóa 1 sản phẩm (khi qty = 1 nhấn -) -->
<div class="modal fade" id="deleteOneModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có muốn xóa sản phẩm này?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Không</button>
                <button type="button" class="btn btn-danger" id="confirmDeleteOne" data-bs-dismiss="modal">Có</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal thông báo chọn sản phẩm -->
<div class="modal fade" id="selectProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thông báo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="modalMessage">
                Vui lòng chọn sản phẩm để thanh toán
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Đồng ý</button>
            </div>
        </div>
    </div>
</div>

<div class="container mt-4">
    <h2 class="mb-4">Giỏ hàng của bạn</h2>
    
    <c:choose>
        <c:when test="${cartItems != null && cartItems.size() > 0}">
            <!-- Toolbar -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <input type="checkbox" class="form-check-input" id="selectAll">
                    <label class="form-check-label" for="selectAll">Chọn tất cả</label>
                </div>
                <div>
                    <button type="button" class="btn btn-outline-danger" id="deleteSelectedBtn" 
                            data-bs-toggle="modal" data-bs-target="#deleteAllModal" disabled>
                        Xóa đã chọn
                    </button>
                </div>
            </div>
            
            <!-- Cart Table -->
            <table id="table-cart" class="table table-hover">
                <thead class="table-primary">
                    <tr>
                        <th class="text-center" width="50"></th>
                        <th class="text-center">Hình ảnh</th>
                        <th class="text-center">Tên sản phẩm</th>
                        <th class="text-center">Số lượng</th>
                        <th class="text-center">Đơn giá</th>
                        <th class="text-center">Thành tiền</th>
                        <th class="text-center">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="grandTotal" value="0" />
                    <c:forEach var="cartItem" items="${cartItems}">
                        <c:set var="itemTotal" value="${cartItem.productType.price * cartItem.quantity}" />
                        <c:set var="grandTotal" value="${grandTotal + itemTotal}" />
                        <c:set var="isOutOfStock" value="${cartItem.productType.quantity <= 0}" />
                        <c:set var="maxQty" value="${cartItem.productType.quantity}" />
                        
                        <tr class="cart-item ${isOutOfStock ? 'cart-item--out-stock' : ''}" 
                            data-cart-item-id="${cartItem.cartItemId}"
                            data-price="${cartItem.productType.price}"
                            data-max-qty="${maxQty}">
                            
                            <td class="align-middle text-center">
                                <c:if test="${!isOutOfStock}">
                                    <input type="checkbox" class="form-check-input item-checkbox" 
                                           value="${cartItem.cartItemId}">
                                </c:if>
                                <c:if test="${isOutOfStock}">
                                    <span class="badge bg-danger">Hết hàng</span>
                                </c:if>
                            </td>
                            
                            <td class="align-middle text-center">
                                <img width="100" height="100" class="img-thumbnail rounded" 
                                     src="${cartItem.productType.product.productImages[0].productImage}" 
                                     alt="preview"/>
                            </td>
                            
                            <td class="align-middle text-center">
                                ${cartItem.productType.product.name}
                            </td>
                            
                            <td class="align-middle text-center">
                                <div class="d-flex justify-content-center align-items-center">
                                    <button type="button" class="btn btn-outline-secondary btn-sm subProductBtn" 
                                            ${isOutOfStock ? 'disabled' : ''}>-</button>
                                    <input type="number" class="form-control text-center quantity-input mx-2" 
                                           value="${cartItem.quantity}" min="1" max="${maxQty}" 
                                           style="width: 60px;" ${isOutOfStock ? 'disabled' : ''}>
                                    <button type="button" class="btn btn-outline-secondary btn-sm addProductBtn"
                                            ${isOutOfStock ? 'disabled' : ''}>+</button>
                                </div>
                            </td>
                            
                            <td class="align-middle text-center">
                                <fmt:formatNumber value="${cartItem.productType.price}" type="number" groupingUsed="true"/>₫
                            </td>
                            
                            <td class="align-middle text-center item-total">
                                <fmt:formatNumber value="${itemTotal}" type="number" groupingUsed="true"/>₫
                            </td>
                            
                            <td class="align-middle text-center">
                                <button type="button" class="btn btn-danger btn-sm removeProductBtn">
                                    Xóa
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <!-- Cart Summary -->
            <div class="row mt-4">
                <div class="col-md-6 offset-md-6">
                    <div class="cart-summary">
                        <div class="d-flex justify-content-between mb-2">
                            <span>Tổng tiền giỏ hàng:</span>
                            <span class="total-price" id="grandTotal">
                                <fmt:formatNumber value="${grandTotal}" type="number" groupingUsed="true"/>₫
                            </span>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <span>Tổng tiền đã chọn:</span>
                            <span class="total-price text-primary" id="selectedTotal">0₫</span>
                        </div>
                        <button type="button" class="btn btn-warning btn-lg w-100" id="checkoutBtn">
                            Thanh toán
                        </button>
                    </div>
                </div>
            </div>
        </c:when>
        
        <c:otherwise>
            <div class="text-center p-5">
                <h4 class="text-muted mb-3">Giỏ hàng của bạn đang trống</h4>
                <a href="<%= request.getContextPath() %>/customer/home" class="btn btn-primary btn-lg">
                    Mua ngay
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const contextPath = '<%= request.getContextPath() %>';
    let pendingDeleteCartItemId = null;
    
    // Chọn tất cả
    document.getElementById('selectAll')?.addEventListener('change', function() {
        document.querySelectorAll('.item-checkbox').forEach(cb => {
            cb.checked = this.checked;
        });
        updateSelectedTotal();
        updateDeleteButton();
    });
    
    // Checkbox từng item
    document.querySelectorAll('.item-checkbox').forEach(cb => {
        cb.addEventListener('change', function() {
            updateSelectedTotal();
            updateDeleteButton();
            // Update selectAll state
            const allChecked = document.querySelectorAll('.item-checkbox:checked').length === 
                               document.querySelectorAll('.item-checkbox').length;
            document.getElementById('selectAll').checked = allChecked;
        });
    });
    
    // Cập nhật tổng tiền đã chọn
    function updateSelectedTotal() {
        let total = 0;
        document.querySelectorAll('.item-checkbox:checked').forEach(cb => {
            const row = cb.closest('tr');
            const price = parseFloat(row.dataset.price);
            const qty = parseInt(row.querySelector('.quantity-input').value);
            total += price * qty;
        });
        document.getElementById('selectedTotal').textContent = 
            total.toLocaleString('vi-VN') + '₫';
    }
    
    // Enable/disable nút xóa
    function updateDeleteButton() {
        const hasChecked = document.querySelectorAll('.item-checkbox:checked').length > 0;
        document.getElementById('deleteSelectedBtn').disabled = !hasChecked;
    }
    
    // Nút + tăng số lượng
    document.querySelectorAll('.addProductBtn').forEach(btn => {
        btn.addEventListener('click', function() {
            const row = this.closest('tr');
            const input = row.querySelector('.quantity-input');
            const maxQty = parseInt(row.dataset.maxQty);
            let currentQty = parseInt(input.value);
            
            if (currentQty >= maxQty) {
                showModal('selectProductModal', 'Số lượng không đủ trong kho');
                return;
            }
            
            input.value = currentQty + 1;
            updateCartItem(row.dataset.cartItemId, currentQty + 1, row);
        });
    });
    
    // Nút - giảm số lượng
    document.querySelectorAll('.subProductBtn').forEach(btn => {
        btn.addEventListener('click', function() {
            const row = this.closest('tr');
            const input = row.querySelector('.quantity-input');
            let currentQty = parseInt(input.value);
            
            if (currentQty <= 1) {
                // Hiện popup hỏi xóa
                pendingDeleteCartItemId = row.dataset.cartItemId;
                new bootstrap.Modal(document.getElementById('deleteOneModal')).show();
                return;
            }
            
            input.value = currentQty - 1;
            updateCartItem(row.dataset.cartItemId, currentQty - 1, row);
        });
    });
    
    // Input số lượng trực tiếp
    document.querySelectorAll('.quantity-input').forEach(input => {
        input.addEventListener('change', function() {
            const row = this.closest('tr');
            const maxQty = parseInt(row.dataset.maxQty);
            let newQty = parseInt(this.value);
            
            if (newQty < 1) newQty = 1;
            if (newQty > maxQty) {
                newQty = maxQty;
                showModal('selectProductModal', 'Số lượng không đủ trong kho');
            }
            
            this.value = newQty;
            updateCartItem(row.dataset.cartItemId, newQty, row);
        });
    });
    
    // Xóa 1 sản phẩm
    document.querySelectorAll('.removeProductBtn').forEach(btn => {
        btn.addEventListener('click', function() {
            const row = this.closest('tr');
            pendingDeleteCartItemId = row.dataset.cartItemId;
            new bootstrap.Modal(document.getElementById('deleteOneModal')).show();
        });
    });
    
    // Xác nhận xóa 1 sản phẩm
    document.getElementById('confirmDeleteOne')?.addEventListener('click', function() {
        if (pendingDeleteCartItemId) {
            deleteCartItem(pendingDeleteCartItemId);
        }
    });
    
    // Xác nhận xóa đã chọn
    document.getElementById('confirmDeleteAll')?.addEventListener('click', function() {
        const checkedItems = document.querySelectorAll('.item-checkbox:checked');
        checkedItems.forEach(cb => {
            const row = cb.closest('tr');
            deleteCartItem(row.dataset.cartItemId);
        });
    });
    
    // Nút thanh toán
    document.getElementById('checkoutBtn')?.addEventListener('click', function() {
        const checkedItems = document.querySelectorAll('.item-checkbox:checked');
        
        if (checkedItems.length === 0) {
            showModal('selectProductModal', 'Vui lòng chọn sản phẩm để thanh toán');
            return;
        }
        
        // Lấy các cartItemId đã chọn
        const selectedIds = [];
        checkedItems.forEach(cb => {
            selectedIds.push(cb.value);
        });
        
        // Redirect to checkout với selected items
        window.location.href = contextPath + '/order/?cartId=${cartId}&items=' + selectedIds.join(',');
    });
    
    // Helper functions
    function updateCartItem(cartItemId, quantity, row) {
        fetch(contextPath + '/customer/cart', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'action=updateQuantity&cartItemId=' + cartItemId + '&quantity=' + quantity
        })
        .then(res => res.json())
        .then(data => {
            if (data.status === 'success') {
                // Cập nhật thành tiền
                const price = parseFloat(row.dataset.price);
                const total = price * quantity;
                row.querySelector('.item-total').textContent = total.toLocaleString('vi-VN') + '₫';
                updateGrandTotal();
                updateSelectedTotal();
            } else if (data.status === 'quantityExceed') {
                row.querySelector('.quantity-input').value = data.oldQuantity;
                showModal('selectProductModal', 'Số lượng không đủ trong kho');
            }
        });
    }
    
    function deleteCartItem(cartItemId) {
        fetch(contextPath + '/customer/cart', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'action=deleteItem&cartItemId=' + cartItemId
        })
        .then(res => res.json())
        .then(data => {
            if (data.status === 'success') {
                document.querySelector('[data-cart-item-id="' + cartItemId + '"]')?.remove();
                updateGrandTotal();
                updateSelectedTotal();
                checkEmptyCart();
            }
        });
    }
    
    function updateGrandTotal() {
        let total = 0;
        document.querySelectorAll('.cart-item:not(.cart-item--out-stock)').forEach(row => {
            const price = parseFloat(row.dataset.price);
            const qty = parseInt(row.querySelector('.quantity-input').value);
            total += price * qty;
        });
        document.getElementById('grandTotal').textContent = total.toLocaleString('vi-VN') + '₫';
    }
    
    function checkEmptyCart() {
        const items = document.querySelectorAll('.cart-item');
        if (items.length === 0) {
            location.reload();
        }
    }
    
    function showModal(modalId, message) {
        if (message) {
            document.getElementById('modalMessage').textContent = message;
        }
        new bootstrap.Modal(document.getElementById(modalId)).show();
    }
</script>
</body>
</html>
