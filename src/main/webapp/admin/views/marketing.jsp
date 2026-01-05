<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Marketing & Voucher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .img-thumbnail { object-fit: cover; border-radius: 8px; }
        .table thead { background-color: #212529; color: white; }
        .nav-tabs .nav-link { font-weight: bold; color: #495057; }
        .nav-tabs .nav-link.active { color: #0d6efd; border-bottom: 3px solid #0d6efd; }
    </style>
</head>
<body class="container mt-5">

    <h2 class="mb-4 text-primary text-center fw-bold">Hệ thống Quản lý Marketing</h2>

    <ul class="nav nav-tabs mb-4" id="marketingTab" role="tablist">
        <li class="nav-item">
            <button class="nav-link active" id="campaign-tab" data-bs-toggle="tab" data-bs-target="#campaign-panel" type="button">Chiến dịch Banner</button>
        </li>
        <li class="nav-item">
            <button class="nav-link" id="voucher-tab" data-bs-toggle="tab" data-bs-target="#voucher-panel" type="button">Quản lý Voucher</button>
        </li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane fade show active" id="campaign-panel" role="tabpanel">
            <div class="card mb-4 shadow-sm border-primary">
                <div class="card-header bg-primary text-white fw-bold">Thêm Banner</div>
                <form action="${pageContext.request.contextPath}/admin/campaign/addCampaign" method="post" enctype="multipart/form-data">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-8"><textarea name="content" class="form-control" placeholder="Nội dung banner..." required></textarea></div>
                            <div class="col-md-4"><input type="file" name="imageFile" class="form-control" accept="image/*" required></div>
                        </div>
                    </div>
                    <div class="card-footer text-end"><button type="submit" class="btn btn-primary px-4">Lưu Banner</button></div>
                </form>
            </div>
            
            <table class="table table-hover text-center border shadow-sm">
                <thead><tr><th>ID</th><th>Ảnh</th><th>Nội dung</th><th>Thao tác</th></tr></thead>
                <tbody>
                    <c:forEach var="campaign" items="${campaigns}">
                        <tr>
                            <td>${campaign.campaignId}</td>
                            <td>
                                <c:forEach var="img" items="${campaign.campaignImages}">
                                    <img src="${pageContext.request.contextPath}/marketing-images/${img.imagePath}" width="60" height="60" class="img-thumbnail">
                                </c:forEach>
                            </td>
                            <td class="text-start">${campaign.content}</td>
                            <td>
                                <button class="btn btn-warning btn-sm" onclick="openEditCampaign('${campaign.campaignId}', '${campaign.content}')">Sửa</button>
                                <a href="${pageContext.request.contextPath}/admin/campaign/delete?id=${campaign.campaignId}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa banner?')">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tab-pane fade" id="voucher-panel" role="tabpanel">
            <div class="card mb-4 shadow-sm border-success">
                <div class="card-header bg-success text-white fw-bold">Tạo Voucher Mới</div>
                <form action="${pageContext.request.contextPath}/admin/addVoucher" method="post">
                    <div class="card-body row">
                        <div class="col-md-3 mb-2">
                            <label class="small fw-bold">Loại</label>
                            <select name="voucherType" id="vType" class="form-select" onchange="toggleFields('vType', 'pField', 'prodField')">
                                <option value="byPrice">Giảm theo Đơn hàng</option>
                                <option value="byProduct">Giảm theo Sản phẩm</option>
                            </select>
                        </div>
                        <div class="col-md-3 mb-2"><label class="small fw-bold">Mã CODE</label><input type="text" name="code" class="form-control" required></div>
                        <div class="col-md-3 mb-2"><label class="small fw-bold">Số tiền giảm</label><input type="number" name="discount" class="form-control" required></div>
                        <div class="col-md-3 mb-2" id="pField"><label class="small fw-bold">Đơn tối thiểu</label><input type="number" name="lowerbound" class="form-control" value="0"></div>
                        <div class="col-md-6 mb-2 d-none" id="prodField">
                            <label class="small fw-bold text-success">Dòng SP áp dụng</label>
                            <div class="border p-2 rounded bg-light" style="max-height: 80px; overflow-y: auto;">
                                <c:forEach var="pt" items="${productTypes}">
                                    <div class="form-check-inline">
                                        <input class="form-check-input" type="checkbox" name="productTypes" value="${pt.productTypeId}"> ${pt.productTypeName}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-md-3 mb-2"><label class="small fw-bold">Bắt đầu</label><input type="date" name="dateStart" class="form-control" required></div>
                        <div class="col-md-3 mb-2"><label class="small fw-bold">Kết thúc</label><input type="date" name="dateEnd" class="form-control" required></div>
                    </div>
                    <div class="card-footer text-end"><button type="submit" class="btn btn-success px-4">Tạo Voucher</button></div>
                </form>
            </div>

            <table class="table table-hover text-center border shadow-sm">
                <thead class="table-dark"><tr><th>CODE</th><th>Loại</th><th>Giảm</th><th>Điều kiện</th><th>Hạn dùng</th><th>Thao tác</th></tr></thead>
                <tbody>
                    <c:forEach var="v" items="${vouchers}">
                        <tr>
                            <td class="fw-bold text-primary">${v.code}</td>
                            <td>
                                <span class="badge ${v['class'].simpleName == 'VoucherByPrice' ? 'bg-info' : 'bg-warning'} text-dark">
                                    ${v['class'].simpleName == 'VoucherByPrice' ? 'Đơn hàng' : 'Sản phẩm'}
                                </span>
                            </td>
                            <td class="text-danger fw-bold"><fmt:formatNumber value="${v.discount}"/>đ</td>
                            <td>
                                <c:choose>
                                    <c:when test="${v['class'].simpleName == 'VoucherByPrice'}">Đơn > <fmt:formatNumber value="${v.lowerbound}"/>đ</c:when>
                                    <c:otherwise>Theo dòng SP</c:otherwise>
                                </c:choose>
                            </td>
                            <td><small><fmt:formatDate value="${v.dateStart}" pattern="dd/MM"/> - <fmt:formatDate value="${v.dateEnd}" pattern="dd/MM/yy"/></small></td>
                            <td>
                                <div class="btn-group">
                                    <button class="btn btn-warning btn-sm" onclick="openEditVoucher('${v.voucherId}', '${v.code}', '${v.discount}')">Sửa</button>
                                    <a href="${pageContext.request.contextPath}/admin/voucher/delete?id=${v.voucherId}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa voucher này?')">Xóa</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="modal fade" id="editCampaignModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog"><form action="${pageContext.request.contextPath}/admin/campaign/update" method="post" enctype="multipart/form-data" class="modal-content">
            <div class="modal-header bg-warning text-dark"><h5>Cập nhật Banner</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
            <div class="modal-body">
                <input type="hidden" name="campaignId" id="editCId">
                <div class="mb-3"><label class="fw-bold">Nội dung</label><textarea name="content" id="editCContent" class="form-control" rows="3" required></textarea></div>
                <div class="mb-3"><label class="fw-bold">Ảnh mới (để trống nếu giữ nguyên)</label><input type="file" name="imageFile" class="form-control"></div>
            </div>
            <div class="modal-footer"><button type="submit" class="btn btn-warning">Lưu thay đổi</button></div>
        </form></div>
    </div>

    <div class="modal fade" id="editVoucherModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog"><form action="${pageContext.request.contextPath}/admin/voucher/update" method="post" class="modal-content">
            <div class="modal-header bg-warning text-dark"><h5>Cập nhật Voucher</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
            <div class="modal-body">
                <input type="hidden" name="vId" id="editVId">
                <div class="mb-3"><label class="fw-bold">Mã Code</label><input type="text" name="code" id="editVCode" class="form-control" required></div>
                <div class="mb-3"><label class="fw-bold">Số tiền giảm</label><input type="number" name="discount" id="editVDiscount" class="form-control" required></div>
            </div>
            <div class="modal-footer"><button type="submit" class="btn btn-warning">Lưu thay đổi</button></div>
        </form></div>
    </div>

    <script>
        // 1. Tự động mở Tab cũ dựa trên URL param ?tab=voucher
        document.addEventListener("DOMContentLoaded", function() {
            const urlParams = new URLSearchParams(window.location.search);
            const tab = urlParams.get('tab');
            if (tab === 'voucher') {
                const vTab = new bootstrap.Tab(document.getElementById('voucher-tab'));
                vTab.show();
            }
        });

        // 2. Toggle hiển thị trường nhập theo loại Voucher
        function toggleFields(sId, pId, prId) {
            const type = document.getElementById(sId).value;
            document.getElementById(pId).classList.toggle('d-none', type !== 'byPrice');
            document.getElementById(prId).classList.toggle('d-none', type === 'byPrice');
        }

        // 3. Xử lý Modals
        function openEditCampaign(id, content) {
            document.getElementById('editCId').value = id;
            document.getElementById('editCContent').value = content;
            new bootstrap.Modal(document.getElementById('editCampaignModal')).show();
        }

        function openEditVoucher(id, code, discount) {
            document.getElementById('editVId').value = id;
            document.getElementById('editVCode').value = code;
            document.getElementById('editVDiscount').value = discount;
            new bootstrap.Modal(document.getElementById('editVoucherModal')).show();
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>