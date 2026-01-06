<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Product & Variant Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .product-img { object-fit: cover; border-radius: 4px; border: 1px solid #ddd; margin-right: 5px; }
        .modal-xl { max-width: 95%; }
        .section-title { border-left: 4px solid #0d6efd; padding-left: 10px; margin-bottom: 20px; font-weight: bold; }
        
        /* Tối ưu bảng biến thể */
        .variant-table input { min-width: 50px; text-align: center; }
        .variant-table th, .variant-table td { vertical-align: middle; white-space: nowrap; }
        .highlight-row { background-color: #fff3cd !important; transition: 1s; }
        
        /* Giảm padding cho các ô input kích thước để tiết kiệm không gian */
        .size-input { padding: 0.25rem 0.2rem !important; font-size: 0.75rem; }
    </style>
</head>
<body class="bg-light">

<div class="container mt-4">
    <h1 class="text-center mb-4 fw-bold text-dark">ERGO Admin System</h1>

    <div class="card shadow-sm mb-5">
        <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
            <h5 class="mb-0">Kho hàng sản phẩm</h5>
            <button class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#addProductModal">+ Thêm sản phẩm</button>
        </div>
        <div class="card-body p-0">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th class="ps-3" style="width: 5%">ID</th>
                        <th style="width: 30%">Tên sản phẩm</th>
                        <th style="width: 25%">Hình ảnh</th>
                        <th style="width: 20%">Danh mục</th>
                        <th class="text-end pe-3" style="width: 20%">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${listProduct}">
                        <tr id="prod-row-${product.productId}">
                            <td class="ps-3 text-muted">${product.productId}</td>
                            <td><span class="fw-bold">${product.name}</span></td>
                            <td>
                                <c:forEach var="image" items="${product.productImages}">
                                    <img src="../product-images/${image.productImage}" class="product-img" width="45" height="45">
                                </c:forEach>
                            </td>
                            <td><span class="badge bg-info text-dark">${product.category.categoryName}</span></td>
                            <td class="text-end pe-3">
                                <button class="btn btn-warning btn-sm btn-edit-product" 
                                        data-id="${product.productId}"
                                        data-name="${product.name}"
                                        data-desc="${product.descript}"
                                        data-cat="${product.category.categoryId}"
                                        data-variants='[<c:forEach var="type" items="${product.productTypes}" varStatus="status">{"typeId":"${type.typeId}","color":"${type.color}","price":"${type.price}","quantity":"${type.quantity}","material":"${type.material}","width":"${type.width}","height":"${type.height}","length":"${type.length}","weight":"${type.weight}"}${not status.last ? "," : ""}</c:forEach>]'>
                                    Sửa
                                </button>
							<a href="product/deleteproduct?productId=${product.productId}" class="btn btn-outline-danger btn-sm" onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="editProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header bg-warning">
                <h5 class="modal-title fw-bold">Modify Product & Variants</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body bg-light">
                
                <form action="product/editproduct" method="post" enctype="multipart/form-data" class="card shadow-sm mb-4">
                    <div class="card-body">
                        <h6 class="section-title">General Information</h6>
                        <input type="hidden" name="productId" id="editProdId">
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label class="form-label small fw-bold">Tên sản phẩm</label>
                                <input type="text" class="form-control" name="productName" id="editProdName" required>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label class="form-label small fw-bold">Danh mục</label>
                                <select class="form-select" name="category" id="editProdCat">
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.categoryId}">${cat.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label class="form-label small fw-bold">Thêm ảnh mới</label>
                                <input type="file" class="form-control" name="newImages" multiple>
                            </div>
                            <div class="col-12 mb-3">
                                <label class="form-label small fw-bold">Mô tả sản phẩm</label>
                                <textarea class="form-control" name="description" id="editProdDesc" rows="2"></textarea>
                            </div>
                        </div>
                        <div class="text-end">
                            <button type="submit" class="btn btn-primary btn-sm px-4">Lưu thông tin chung</button>
                        </div>
                    </div>
                </form>

                <div class="card shadow-sm">
                    <div class="card-body">
                        <h6 class="section-title text-success">Product Variants (Types)</h6>
                        <div class="table-responsive">
                            <table class="table table-sm table-bordered align-middle text-center variant-table">
                                <thead class="table-dark small">
                                    <tr>
                                        <th style="width: 15%">Màu sắc</th>
                                        <th style="width: 15%">Chất liệu</th>
                                        <th style="width: 15%">Giá bán</th>
                                        <th style="width: 10%">SL</th>
                                        <th style="width: 35%">Kích thước (D-R-C) & Cân nặng</th>
                                        <th style="width: 18%">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody id="variantTableBody"></tbody>
                                <tfoot class="table-info">
                                    <tr>
                                        <form action="${pageContext.request.contextPath}/admin/product/createtype" method="post">
                                            <input type="hidden" name="productId" id="addTypeProdId">
                                            <td><input type="text" name="color" class="form-control form-control-sm" placeholder="Màu" required></td>
                                            <td><input type="text" name="material" class="form-control form-control-sm" placeholder="Chất liệu"></td>
                                            <td><input type="number" name="price" class="form-control form-control-sm" placeholder="Giá" required></td>
                                            <td><input type="number" name="quantity" class="form-control form-control-sm" placeholder="SL" required></td>
                                            <td>
                                                <div class="d-flex gap-1">
                                                    <input type="number" step="0.1" name="length" class="form-control form-control-sm size-input" placeholder="Dài">
                                                    <input type="number" step="0.1" name="width" class="form-control form-control-sm size-input" placeholder="Rộng">
                                                    <input type="number" step="0.1" name="height" class="form-control form-control-sm size-input" placeholder="Cao">
                                                    <input type="number" step="0.1" name="weight" class="form-control form-control-sm size-input" placeholder="Kg">
                                                </div>
                                            </td>
                                            <td>
                                                <button type="submit" class="btn btn-success btn-sm w-100 fw-bold">
                                                    + Thêm mới
                                                </button>
                                            </td>
                                        </form>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function openEditModal(button) {
        const id = button.getAttribute('data-id');
        const name = button.getAttribute('data-name');
        const desc = button.getAttribute('data-desc');
        const catId = button.getAttribute('data-cat');
        
        let variants = [];
        try {
            variants = JSON.parse(button.getAttribute('data-variants'));
        } catch (e) { console.error("Lỗi dữ liệu:", e); }

        document.getElementById('editProdId').value = id;
        document.getElementById('editProdName').value = name;
        document.getElementById('editProdDesc').value = desc;
        document.getElementById('editProdCat').value = catId;
        document.getElementById('addTypeProdId').value = id;

        const tbody = document.getElementById('variantTableBody');
        tbody.innerHTML = '';

        if (!variants || variants.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" class="p-4 text-muted fst-italic">Chưa có biến thể.</td></tr>';
        } else {
            variants.forEach(v => {
                const row = `
                    <tr>
                        <td colspan="6" class="p-0 border-0">
                            <form action="${pageContext.request.contextPath}/admin/edittype" method="post" class="m-0 border-bottom">
                                <table class="table table-borderless m-0 table-sm">
                                    <tr>
                                        <input type="hidden" name="typeId" value="\${v.typeId}">
                                        <input type="hidden" name="productId" value="\${id}">
                                        <td style="width: 12%"><input type="text" name="color" class="form-control form-control-sm" value="\${v.color || ''}"></td>
                                        <td style="width: 12%"><input type="text" name="material" class="form-control form-control-sm" value="\${v.material || ''}"></td>
                                        <td style="width: 15%"><input type="number" name="price" class="form-control form-control-sm" value="\${v.price || 0}"></td>
                                        <td style="width: 8%"><input type="number" name="quantity" class="form-control form-control-sm" value="\${v.quantity || 0}"></td>
                                        <td style="width: 35%">
                                            <div class="d-flex gap-1">
                                                <input type="number" step="0.1" name="length" class="form-control form-control-sm size-input" value="\${v.length || 0}">
                                                <input type="number" step="0.1" name="width" class="form-control form-control-sm size-input" value="\${v.width || 0}">
                                                <input type="number" step="0.1" name="height" class="form-control form-control-sm size-input" value="\${v.height || 0}">
                                                <input type="number" step="0.1" name="weight" class="form-control form-control-sm size-input" value="\${v.weight || 0}">
                                            </div>
                                        </td>
                                        <td style="width: 18%">
                                            <div class="d-flex gap-1">
                                                <button type="submit" class="btn btn-primary btn-sm flex-grow-1">Lưu</button>
                                                <a href="${pageContext.request.contextPath}/admin/deletetype?id=\${v.typeId}&productId=\${id}" 
                                                   class="btn btn-outline-danger btn-sm" onclick="return confirm('Xóa?')">Xóa</a>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </tr>`;
                tbody.innerHTML += row;
            });
        }
        new bootstrap.Modal(document.getElementById('editProductModal')).show();
    }

    document.querySelectorAll('.btn-edit-product').forEach(btn => {
        btn.addEventListener('click', () => openEditModal(btn));
    });

    window.addEventListener('DOMContentLoaded', () => {
        const hash = window.location.hash;
        if (hash.startsWith('#edit-')) {
            const prodId = hash.split('-')[1];
            const btn = document.querySelector(`.btn-edit-product[data-id="\${prodId}"]`);
            if (btn) {
                setTimeout(() => {
                    btn.click();
                    const row = document.getElementById('prod-row-' + prodId);
                    if(row) row.classList.add('highlight-row');
                }, 150);
            }
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>