<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
    import="model.VoucherByPrice,model.VoucherByProduct,model.Voucher,java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Marketing | Ergo Admin</title>
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

<div class="container mt-4">
    <h1 class="text-center">Marketing Campaign Management</h1>

    <div class="mb-3">
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addCampaignModal">Add New Campaign</button>
    </div>

    <table class="table table-bordered table-hover mt-3">
        <thead class="table-dark">
        <tr>
            <th>Campaign ID</th>
            <th>Content</th>
            <th>Voucher</th>
            <th>Status</th>
            <th>Images</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="campaign" items="${campaigns}">
            <tr>
                <td>${campaign.campaignId}</td>
                <td>${campaign.content}</td>
                <td>
                    <c:choose>
                        <c:when test="${campaign.voucher != null}">
                            ${campaign.voucher.voucherId}
                        </c:when>
                        <c:otherwise>
                            No Voucher
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${campaign.isDelete}">
                            Deleted
                        </c:when>
                        <c:otherwise>
                            Active
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty campaign.campaignImages}">
                            <img src="${pageContext.request.contextPath}/${campaign.campaignImages[0].imagePath}" alt="Campaign image" style="max-width: 100px; max-height: 100px; object-fit: contain;">
                        </c:when>
                        <c:otherwise>
                            No Image
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <div class="d-flex justify-content-center">
                        <form action="${pageContext.request.contextPath}/admin/campaign/editCampaign" method="get" style="margin-right: 5px;">
                            <input type="hidden" name="campaignId" value="${campaign.campaignId}">
                            <button type="submit" class="btn btn-warning btn-sm">Edit</button>
                        </form>

                        <form action="${pageContext.request.contextPath}/admin/campaign/deleteCampaign" method="post">
                            <input type="hidden" name="campaignId" value="${campaign.campaignId}">
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this campaign?');">Delete</button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty campaigns}">
            <tr>
                <td colspan="6" class="text-center">No campaigns available!</td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <!-- Add Campaign Modal -->
    <div class="modal fade" id="addCampaignModal" tabindex="-1" aria-labelledby="addCampaignModal" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCampaignModalLabel">Add New Campaign</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/admin/campaign/addCampaign" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="content" class="form-label">Content</label>
                            <textarea class="form-control" id="content" name="content" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="voucherId" class="form-label">Voucher</label>
                            <select class="form-select" id="voucherId" name="voucherId">
                                <option value="">Select a voucher</option>
                                <c:forEach var="voucher" items="${vouchers}">
                                    <option value="${voucher.voucherId}">${voucher.code} - Discount: ${voucher.discount}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="campaignImage" class="form-label">Campaign Image</label>
                            <input type="file" class="form-control" id="campaignImage" name="campaignImage" accept="image/*">
                            <small class="text-muted">Upload an image for the campaign (JPG, PNG, GIF)</small>
                        </div>
                        <button type="submit" class="btn btn-primary">Add Campaign</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Voucher Management Section -->
    <div class="mt-5">
        <h2 class="text-center">Voucher Management</h2>
        <div class="mb-3">
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addVoucherModal">Add New Voucher</button>
        </div>

        <table class="table table-bordered table-hover mt-3">
            <thead class="table-dark">
            <tr>
                <th>Voucher ID</th>
                <th>Code</th>
                <th>Discount</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% 
                List<Voucher> voucherList = (List<Voucher>) request.getAttribute("vouchers");
                if (voucherList != null && !voucherList.isEmpty()) {
                    for (Voucher voucher : voucherList) {
                        boolean isVoucherByPrice = voucher instanceof VoucherByPrice;
            %>
            <tr>
                <td><%= voucher.getVoucherId() %></td>
                <td><%= voucher.getCode() %></td>
                <td><%= voucher.getDiscount() %></td>
                <td><%= voucher.getDateStart() %></td>
                <td><%= voucher.getDateEnd() %></td>
                <td>
                    <div class="d-flex justify-content-center">
                        <% if (isVoucherByPrice) { %>
                            <form action="${pageContext.request.contextPath}/admin/voucher/editPrice" method="get" style="margin-right:5px;">
                                <input type="hidden" name="voucherId" value="<%= voucher.getVoucherId() %>">
                                <button type="submit" class="btn btn-warning btn-sm">Edit</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/admin/voucher/deletePrice" method="post">
                                <input type="hidden" name="voucherId" value="<%= voucher.getVoucherId() %>">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Delete voucher?');">Delete</button>
                            </form>
                        <% } else { %>
                            <form action="${pageContext.request.contextPath}/admin/voucher/editProduct" method="get" style="margin-right:5px;">
                                <input type="hidden" name="voucherId" value="<%= voucher.getVoucherId() %>">
                                <button type="submit" class="btn btn-warning btn-sm">Edit</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/admin/voucher/deleteProduct" method="post">
                                <input type="hidden" name="voucherId" value="<%= voucher.getVoucherId() %>">
                                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Delete voucher?');">Delete</button>
                            </form>
                        <% } %>
                    </div>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6" class="text-center">No vouchers available!</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Add Voucher Modal -->
    <div class="modal fade" id="addVoucherModal" tabindex="-1" aria-labelledby="addVoucherModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addVoucherModalLabel">Add New Voucher</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/admin/voucher/add" method="post">
                        <div class="mb-3">
                            <label for="voucherType" class="form-label">Voucher Type</label>
                            <select class="form-select" id="voucherType" name="voucherType" required>
                                <option value="byPrice" selected>Voucher by Price</option>
                                <option value="byProduct">Voucher by Product</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="code" class="form-label">Voucher Code</label>
                            <input type="text" class="form-control" id="code" name="code" required>
                        </div>
                        <div class="mb-3">
                            <label for="discount" class="form-label">Discount (%)</label>
                            <input type="number" class="form-control" id="discount" name="discount" step="0.01" required>
                        </div>
                        <div class="mb-3">
                            <label for="dateStart" class="form-label">Start Date</label>
                            <input type="date" class="form-control" id="dateStart" name="dateStart" required>
                        </div>
                        <div class="mb-3">
                            <label for="dateEnd" class="form-label">End Date</label>
                            <input type="date" class="form-control" id="dateEnd" name="dateEnd" required>
                        </div>

                        <div class="voucherByPriceFields d-none">
                            <div class="mb-3">
                                <label for="lowerbound" class="form-label">Minimum Order Value</label>
                                <input type="number" class="form-control" id="lowerbound" name="lowerbound" step="0.01">
                            </div>
                        </div>

                        <div class="voucherByProductFields d-none">
                            <div class="mb-3">
                                <label for="productTypes" class="form-label">Applicable Product Types</label>
                                <select multiple class="form-select" id="productTypes" name="productTypes">
                                    <c:forEach var="productType" items="${productTypes}">
                                        <option value="${productType.typeId}">${productType.product.name} | ${productType.color}</option>
                                    </c:forEach>
                                </select>
                                <small class="text-muted">Hold CTRL (Windows) or CMD (Mac) to select multiple types.</small>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary">Add Voucher</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

<script>
    (function () {
        var voucherTypeEl = document.getElementById("voucherType");
        if (!voucherTypeEl) return;
        function updateFields() {
            var voucherType = voucherTypeEl.value;
            var priceFields = document.querySelector('.voucherByPriceFields');
            var productFields = document.querySelector('.voucherByProductFields');

            if (voucherType === "byPrice") {
                priceFields.classList.remove("d-none");
                productFields.classList.add("d-none");
            } else if (voucherType === "byProduct") {
                productFields.classList.remove("d-none");
                priceFields.classList.add("d-none");
            } else {
                priceFields.classList.add("d-none");
                productFields.classList.add("d-none");
            }
        }
        voucherTypeEl.addEventListener("change", updateFields);
        document.addEventListener("DOMContentLoaded", updateFields);
    })();
</script>
</div>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
