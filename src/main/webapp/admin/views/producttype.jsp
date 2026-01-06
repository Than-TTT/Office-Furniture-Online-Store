<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("pageTitle", "Product Types"); %>
<%@ include file="/WEB-INF/includes/admin_header.jspf" %>

<style>
    .btn-group { display: flex; gap: 5px; }
</style>

<div class="container mt-4">
    <h1 class="text-center">Product Type Management</h1>
    <table class="table table-bordered table-hover mt-3">
        <thead class="table-dark">
        <tr>
            <th>Product Name</th>
            <th>Type Id</th>
            <th>Color</th>
            <th>Width</th>
            <th>Height</th>
            <th>Length</th>
            <th>Weight</th>
            <th>Material</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="producttype" items="${listProductType}">
                <tr>
                    <td>${producttype.product.name}</td>
                    <td>${producttype.typeId}</td>
                    <td>${producttype.color}</td>
                    <td>${producttype.width}</td>
                    <td>${producttype.height}</td>
                    <td>${producttype.length}</td>
                    <td>${producttype.weight}</td>
                    <td>${producttype.material}</td>
                    <td>${producttype.price}</td>
                    <td>${producttype.quantity}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editProductTypeModal${producttype.typeId}">Edit</button>
                        <!-- Edit Product Modal -->
                        <div class="modal fade" id="editProductTypeModal${producttype.typeId}" tabindex="-1" aria-labelledby="editProductModalLabel${producttype.typeId}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editProductTypeModalLabel${producttype.typeId}">Edit Product</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="edittype" method="post">
                                            <input type="hidden" value="${producttype.product.productId}" name="productId">
                                            <input type="hidden" value="${producttype.typeId}" name="typeId">
                                            <div class="mb-3">
                                                <label class="form-label">Color</label>
                                                <input type="text" class="form-control" name="color" value="${producttype.color}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Width</label>
                                                <input type="text" class="form-control" name="width" value="${producttype.width}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Height</label>
                                                <input type="text" class="form-control" name="height" value="${producttype.height}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Length</label>
                                                <input type="text" class="form-control" name="length" value="${producttype.length}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Weight</label>
                                                <input type="text" class="form-control" name="weight" value="${producttype.weight}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Material</label>
                                                <input type="text" class="form-control" name="material" value="${producttype.material}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Price</label>
                                                <input type="text" class="form-control" name="price" value="${producttype.price}" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Quantity</label>
                                                <input type="text" class="form-control" name="quantity" value="${producttype.quantity}" required>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Save Changes</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- End Edit Product Modal -->
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#createProductTypeModal">Create Type</button>
    <!-- Edit Product Modal -->
    <div class="modal fade" id="createProductTypeModal" tabindex="-1" aria-labelledby="createProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createProductTypeModalLabel">Edit Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="product/createtype" method="post">
                        <div class="mb-3">
                            <label class="form-label">Product Id</label>
                            <input type="text" class="form-control" name="productId" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Color</label>
                            <input type="text" class="form-control" name="color" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Width</label>
                            <input type="text" class="form-control" name="width" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Height</label>
                            <input type="text" class="form-control" name="height" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Length</label>
                            <input type="text" class="form-control" name="length" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Weight</label>
                            <input type="text" class="form-control" name="weight" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Material</label>
                            <input type="text" class="form-control" name="material" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Price</label>
                            <input type="text" class="form-control" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Quantity</label>
                            <input type="text" class="form-control" name="quantity" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </form>
                </div>
            </div>
        </div>
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


    <div class="container mt-3">
        <div class="row justify-content-center">
            <!-- Back to home page -->
            <div class="col-auto">
                <a href="home" class="btn btn-primary">Back to Home</a>
            </div>
        </div>
    </div>
</div>




<%@ include file="/WEB-INF/includes/admin_footer.jspf" %>
