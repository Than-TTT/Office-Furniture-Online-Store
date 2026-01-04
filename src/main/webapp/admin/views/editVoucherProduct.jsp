<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit Voucher (Product) | Ergo Admin</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/admin/assets/img/favicon/favicon.ico" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@2.1.1/css/boxicons.min.css" />
    <style>
        body { font-family: 'Public Sans', sans-serif; background-color: #f5f6f8; }
        .navbar-custom { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .navbar-custom .navbar-brand { font-weight: 700; color: white !important; font-size: 1.5rem; }
        .page-container { min-height: 100vh; display: flex; flex-direction: column; }
        .content-area { flex: 1; padding: 30px 20px; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/employee">
            <i class="bx bxs-dashboard"></i> Admin Panel
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>
<div class="page-container">
<div class="content-area">

<div class="container-xxl flex-grow-1 container-p-y">
  <div class="row">
    <div class="col-lg-8 mx-auto">
      <div class="card">
        <div class="card-header">
          <h5 class="card-title mb-0">Edit Voucher by Product</h5>
        </div>
        <div class="card-body">
          <form
            action="${pageContext.request.contextPath}/admin/voucher/editProduct"
            method="post"
            novalidate
          >
            <input type="hidden" name="voucherId" value="${editvoucherId}" />

            <div class="mb-3">
              <label for="editVoucherCodeProduct" class="form-label">Voucher Code</label>
              <input
                type="text"
                class="form-control"
                id="editVoucherCodeProduct"
                name="editVoucherCodeProduct"
                value="${editVoucherCodeProduct != null ? editVoucherCodeProduct : ''}"
                required
              />
            </div>

            <div class="mb-3">
              <label for="editVoucherDiscountProduct" class="form-label"
                >Discount (%)</label
              >
              <input
                type="number"
                class="form-control"
                id="editVoucherDiscountProduct"
                name="editVoucherDiscountProduct"
                step="0.01"
                value="${editVoucherDiscountProduct != null ? editVoucherDiscountProduct : ''}"
                required
              />
            </div>

            <div class="row g-2">
              <div class="col-md-6 mb-3">
                <label for="editVoucherDateStartProduct" class="form-label"
                  >Start Date</label
                >
                <input
                  type="date"
                  class="form-control"
                  id="editVoucherDateStartProduct"
                  name="editVoucherDateStartProduct"
                  value="${editVoucherDateStartProduct != null ? editVoucherDateStartProduct : ''}"
                />
              </div>
              <div class="col-md-6 mb-3">
                <label for="editVoucherDateEndProduct" class="form-label">End Date</label>
                <input
                  type="date"
                  class="form-control"
                  id="editVoucherDateEndProduct"
                  name="editVoucherDateEndProduct"
                  value="${editVoucherDateEndProduct != null ? editVoucherDateEndProduct : ''}"
                />
              </div>
            </div>

            <div class="mb-3">
              <label for="productTypes" class="form-label"
                >Applicable Product Types</label
              >
              <select multiple class="form-select" id="productTypes" name="productTypes">
                <c:forEach var="productType" items="${productTypes}">
                  <option value="${productType.typeId}">
                    ${productType.product.name} | ${productType.color}
                  </option>
                </c:forEach>
              </select>
              <small class="text-muted"
                >Hold CTRL (Windows) or CMD (Mac) to select multiple types.</small
              >
            </div>

            <div class="d-flex justify-content-between">
              <a
                class="btn btn-secondary"
                href="${pageContext.request.contextPath}/admin/marketing"
                >Cancel</a
              >
              <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
          </form>
        </div>
      </div>
      <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger mt-3">${errorMessage}</div>
      </c:if>
    </div>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
