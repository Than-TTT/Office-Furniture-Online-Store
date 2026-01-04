<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit Campaign | Ergo Admin</title>
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
          <h5 class="card-title mb-0">Edit Campaign</h5>
        </div>
        <div class="card-body">
          <!-- Form to edit campaign -->
          <form
            id="editCampaignForm"
            action="${pageContext.request.contextPath}/admin/campaign/editCampaign"
            method="post"
            enctype="multipart/form-data"
            novalidate
          >
            <input type="hidden" name="campaignId" value="${campaignId}" />

            <div class="mb-3">
              <label for="content" class="form-label">Content</label>
              <textarea class="form-control" id="content" name="content" required>
${content}</textarea
              >
            </div>

            <div class="mb-3">
              <label for="voucherId" class="form-label">Voucher</label>
              <select class="form-select" id="voucherId" name="voucherId">
                <option value="">Select a voucher</option>
                <c:forEach var="voucher" items="${vouchers}">
                  <option value="${voucher.voucherId}">
                    ${voucher.code} - Discount: ${voucher.discount}%
                  </option>
                </c:forEach>
              </select>
            </div>

            <div class="mb-3">
              <label for="campaignImage" class="form-label">Campaign Image</label>
              <c:if test="${not empty image}">
                <div class="mb-2">
                  <p class="text-muted">Current image:</p>
                  <img src="${pageContext.request.contextPath}/${image}" alt="Current campaign image" style="max-width: 200px; max-height: 200px;">
                </div>
              </c:if>
              <input
                type="file"
                class="form-control"
                id="campaignImage"
                name="campaignImage"
                accept="image/*"
              />
              <small class="text-muted">Upload a new image to replace the current one (JPG, PNG, GIF)</small>
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
