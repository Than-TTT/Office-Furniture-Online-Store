<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Product & Category Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .btn-group { display: flex; gap: 5px; }
        .product-img { object-fit: cover; border-radius: 4px; border: 1px solid #ddd; margin-right: 5px; }
        .card-header { font-weight: bold; }
        .badge-category { font-size: 0.85rem; }
    </style>
</head>
<body>
<div class="container mt-4">
    <h1 class="text-center mb-4">Admin Management System</h1>

    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error:</strong> ${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% session.removeAttribute("error"); %>
    </c:if>

    <div class="card mb-5 shadow-sm">
        <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
            <h4 class="mb-0">Product Inventory</h4>
            <button class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#addProductModal">Add New Product</button>
        </div>
        <div class="card-body">
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Product Name</th>
                        <th>Images</th>
                        <th>Category</th>
                        <th style="width: 150px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${listProduct}">
                        <tr>
                            <td>${product.productId}</td>
                            <td><strong>${product.name}</strong></td>
                            <td>
                                <c:forEach var="image" items="${product.productImages}">
                                    <img src="../product-images/${image.productImage}" class="product-img" width="45" height="45" alt="product">
                                </c:forEach>
                            </td>
                            <td>
                                <span class="badge bg-info text-dark badge-category">
                                    ${product.category != null ? product.category.categoryName : 'Uncategorized'}
                                </span>
                            </td>
                            <td>
                                <div class="btn-group">
                                    <button class="btn btn-warning btn-sm" 
                                            onclick="openEditProductModal('${product.productId}', '${product.name}', '${product.descript}', '${product.category.categoryId}')">Edit</button>
                                    <a href="product/deleteproduct?productId=${product.productId}" 
                                       class="btn btn-danger btn-sm" 
                                       onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty listProduct}">
                        <tr><td colspan="5" class="text-center text-muted">No products found.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-7">
            <div class="card shadow-sm">
                <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
                    <h4 class="mb-0">Category Management</h4>
                    <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addCategoryModal">Add Category</button>
                </div>
                <div class="card-body">
                    <table class="table table-sm table-striped align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Category Name</th>
                                <th style="width: 150px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="category" items="${categories}">
                                <tr>
                                    <td>${category.categoryId}</td>
                                    <td>${category.categoryName}</td>
                                    <td>
                                        <button class="btn btn-outline-warning btn-sm" 
                                                onclick="openEditCategory('${category.categoryId}', '${category.categoryName}')">Edit</button>
                                        <a href="category/delete?id=${category.categoryId}" 
                                           class="btn btn-outline-danger btn-sm" 
                                           onclick="return confirm('Deleting this category may affect linked products. Proceed?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addCategoryModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/admin/category/add" method="post" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Create New Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label class="form-label">Category Name</label>
                    <input type="text" class="form-control" name="categoryName" placeholder="e.g. Electronics" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-primary">Save Category</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="editCategoryModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
		<form action="${pageContext.request.contextPath}/admin/category/update" method="post" class="modal-content">            
			<div class="modal-header">
                <h5 class="modal-title">Update Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="categoryId" id="editCatId">
                <div class="mb-3">
                    <label class="form-label">New Category Name</label>
                    <input type="text" class="form-control" name="categoryName" id="editCatName" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-warning">Update Changes</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="addProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <form action="product/addproduct" method="post" enctype="multipart/form-data" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Register New Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Product Name</label>
                        <input type="text" class="form-control" name="productName" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Category</label>
                        <select class="form-select" name="category" required>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryId}">${cat.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Description</label>
                    <textarea class="form-control" name="description" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Product Images</label>
                    <input type="file" class="form-control" name="images" multiple>
                    <small class="text-muted">You can select multiple files.</small>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-bs-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-success">Confirm Addition</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="editProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <form action="product/editproduct" method="post" enctype="multipart/form-data" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modify Product Information</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="productId" id="editProdId">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Product Name</label>
                        <input type="text" class="form-control" name="productName" id="editProdName" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Category</label>
                        <select class="form-select" name="category" id="editProdCat" required>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryId}">${cat.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Description</label>
                    <textarea class="form-control" name="description" id="editProdDesc" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Upload Additional Images</label>
                    <input type="file" class="form-control" name="newImages" multiple>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-warning text-dark">Save Changes</button>
            </div>
        </form>
    </div>
</div>

<script>
    // Functions to populate data into modals
    function openEditCategory(id, name) {
        document.getElementById('editCatId').value = id;
        document.getElementById('editCatName').value = name;
        new bootstrap.Modal(document.getElementById('editCategoryModal')).show();
    }

    function openEditProductModal(id, name, desc, catId) {
        document.getElementById('editProdId').value = id;
        document.getElementById('editProdName').value = name;
        document.getElementById('editProdDesc').value = desc;
        document.getElementById('editProdCat').value = catId;
        new bootstrap.Modal(document.getElementById('editProductModal')).show();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>