<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Marketing & Voucher | Ergo Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; font-family: 'Segoe UI', sans-serif; }
        .nav-tabs .nav-link { font-weight: bold; color: #6c757d; border: none; padding: 12px 25px; }
        .nav-tabs .nav-link.active { color: #0d6efd; border-bottom: 3px solid #0d6efd; background: transparent; }
        .card { border-radius: 15px; border: none; box-shadow: 0 4px 12px rgba(0,0,0,0.08); }
        .img-banner { object-fit: cover; border-radius: 8px; border: 1px solid #dee2e6; transition: 0.3s; }
        .img-banner:hover { transform: scale(1.05); }
        .badge-voucher { font-size: 0.75rem; padding: 4px 10px; border-radius: 20px; font-weight: 600; }
        .table thead { background-color: #f1f3f5; }
    </style>
</head>
<body class="container py-5">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold text-dark m-0"><i class="bi bi-megaphone-fill text-primary me-2"></i>Hệ thống Marketing</h2>
    </div>

    <ul class="nav nav-tabs mb-4" id="mktTab" role="tablist">
        <li class="nav-item">
            <button class="nav-link active" id="banner-tab" data-bs-toggle="tab" data-bs-target="#banner-panel">
                <i class="bi bi-images me-1"></i> Chiến dịch Banner
            </button>
        </li>
        <li class="nav-item">
            <button class="nav-link" id="voucher-tab" data-bs-toggle="tab" data-bs-target="#voucher-panel">
                <i class="bi bi-ticket-perforated me-1"></i> Quản lý Voucher
            </button>
        </li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane fade show active" id="banner-panel">
            <div class="card mb-4 p-4 border-start border-primary border-4">
                <h5 class="fw-bold mb-3 text-primary">Thêm Chiến dịch Banner</h5>
                <form action="${pageContext.request.contextPath}/admin/campaign/addCampaign" method="post" enctype="multipart/form-data" class="row g-3">
                    <div class="col-md-5">
                        <label class="form-label small fw-bold">Nội dung hiển thị</label>
                        <textarea name="content" class="form-control" rows="1" placeholder="Mô tả chiến dịch..." required></textarea>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label small fw-bold">Voucher đính kèm</label>
                        <select name="voucherId" class="form-select">
                            <option value="">-- Không đính kèm --</option>
                            <c:forEach var="v" items="${vouchers}">
                                <option value="${v.voucherId}">${v.code} (Giảm ${v.discount}đ)</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label small fw-bold">Hình ảnh</label>
                        <input type="file" name="imageFile" class="form-control" accept="image/*" required>
                    </div>
                    <div class="col-12 text-end">
                        <button type="submit" class="btn btn-primary px-4 shadow-sm fw-bold">Lưu Chiến dịch</button>
                    </div>
                </form>
            </div>
            
            <div class="card p-3">
                <table class="table table-hover align-middle">
                    <thead>
                        <tr class="text-center">
                            <th width="5%">ID</th>
                            <th width="15%">Banner</th>
                            <th width="40%" class="text-start">Nội dung mô tả</th>
                            <th width="20%">Voucher áp dụng</th>
                            <th width="20%">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="campaign" items="${campaigns}">
                            <tr class="text-center">
                                <td class="fw-bold">#${campaign.campaignId}</td>
                                <td>
                                    <c:forEach var="img" items="${campaign.campaignImages}">
                                        <img src="${pageContext.request.contextPath}/marketing-images/${img.imagePath}" width="100" height="60" class="img-banner">
                                    </c:forEach>
                                </td>
                                <td class="text-start">${campaign.content}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty campaign.voucher}">
                                            <span class="badge bg-primary-subtle text-primary border border-primary px-2">
                                                <i class="bi bi-tag-fill me-1"></i>${campaign.voucher.code}
                                            </span>
                                        </c:when>
                                        <c:otherwise><span class="text-muted small italic">Không có</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-outline-warning me-1" 
                                        onclick="openEditCampaign('${campaign.campaignId}', '${campaign.content}', '${campaign.voucher.voucherId}')">
                                        <i class="bi bi-pencil"></i>
                                    </button>
                                    <a href="${pageContext.request.contextPath}/admin/campaign/delete?id=${campaign.campaignId}" 
                                       class="btn btn-sm btn-outline-danger" onclick="return confirm('Xóa chiến dịch này?')">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="tab-pane fade" id="voucher-panel">
            <div class="card mb-4 p-4 border-top border-success border-4">
                <h5 class="fw-bold text-success mb-3">Phát hành Voucher</h5>
                <form action="${pageContext.request.contextPath}/admin/addVoucher" method="post" class="row g-3">
                    <div class="col-md-3">
                        <label class="form-label small fw-bold">Loại áp dụng</label>
                        <select name="voucherType" id="vType" class="form-select" onchange="toggleAddFields()">
                            <option value="byPrice">Giảm theo tổng đơn</option>
                            <option value="byProduct">Giảm theo dòng SP</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label small fw-bold">Mã Code</label>
                        <input type="text" name="code" class="form-control text-uppercase fw-bold" placeholder="HELLO2024" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label small fw-bold">Mức giảm (đ)</label>
                        <input type="number" name="discount" class="form-control" required>
                    </div>
                    <div class="col-md-3" id="addPriceField">
                        <label class="form-label small fw-bold">Đơn tối thiểu</label>
                        <input type="number" name="lowerbound" class="form-control" value="0">
                    </div>
                    
                    <div class="col-12 d-none" id="addProductField">
                        <label class="form-label small fw-bold text-success">Chọn dòng sản phẩm</label>
                        <div class="border p-2 rounded bg-light d-flex flex-wrap gap-2" style="max-height: 100px; overflow-y: auto;">
                            <c:forEach var="pt" items="${productTypes}">
                                <div class="form-check border px-2 py-1 rounded bg-white shadow-sm">
                                    <input class="form-check-input" type="checkbox" name="productTypes" value="${pt.typeId}">
                                    <label class="form-check-label small">${pt.product.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    
                    <div class="col-md-3"><label class="form-label small fw-bold">Bắt đầu</label><input type="date" name="dateStart" class="form-control" required></div>
                    <div class="col-md-3"><label class="form-label small fw-bold">Kết thúc</label><input type="date" name="dateEnd" class="form-control" required></div>
                    <div class="col-md-6 text-end align-self-end"><button type="submit" class="btn btn-success px-5 fw-bold shadow-sm">Tạo Voucher</button></div>
                </form>
            </div>

            <div class="card p-3">
                <table class="table table-hover align-middle text-center">
                    <thead class="table-light">
                        <tr><th>CODE</th><th>Phân loại</th><th>Mức giảm</th><th>Điều kiện</th><th>Hạn dùng</th><th>Thao tác</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${vouchers}">
                            <c:set var="isPrice" value="${v['class'].simpleName == 'VoucherByPrice'}" />
                            <tr>
                                <td class="fw-bold text-primary">${v.code}</td>
                                <td>
                                    <span class="badge badge-voucher ${isPrice ? 'bg-info-subtle text-info border-info' : 'bg-success-subtle text-success border-success'} border">
                                        ${isPrice ? 'Đơn hàng' : 'Sản phẩm'}
                                    </span>
                                </td>
                                <td class="text-danger fw-bold"><fmt:formatNumber value="${v.discount}"/>đ</td>
                                <td class="small">
                                    <c:choose>
                                        <c:when test="${isPrice}">Đơn ≥ <fmt:formatNumber value="${v.lowerbound}"/>đ</c:when>
                                        <c:otherwise>Theo dòng SP</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><small class="text-muted"><fmt:formatDate value="${v.dateEnd}" pattern="dd/MM/yyyy"/></small></td>
                                <td>
                                    <button class="btn btn-sm btn-outline-warning" 
                                        onclick="openEditVoucher('${v.voucherId}', '${v.code}', '${v.discount}', 
                                        '<fmt:formatDate value="${v.dateStart}" pattern="yyyy-MM-dd"/>', 
                                        '<fmt:formatDate value="${v.dateEnd}" pattern="yyyy-MM-dd"/>', 
                                        '${isPrice}', '${isPrice ? v.lowerbound : 0}')">
                                        <i class="bi bi-pencil"></i>
                                    </button>
                                    <a href="${pageContext.request.contextPath}/admin/voucher/${isPrice ? 'deletePrice' : 'deleteProduct'}?voucherId=${v.voucherId}" 
                                       class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editCampaignModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <form id="editCampaignForm" action="${pageContext.request.contextPath}/admin/campaign/update" method="post" enctype="multipart/form-data" class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="fw-bold mb-0">Cập nhật Chiến dịch</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4 row g-3">
                    <input type="hidden" name="campaignId" id="editCamId">
                    <div class="col-12">
                        <label class="form-label small fw-bold">Nội dung chiến dịch</label>
                        <textarea name="content" id="editCamContent" class="form-control" rows="3" required></textarea>
                    </div>
                    <div class="col-12">
                        <label class="form-label small fw-bold">Voucher đính kèm</label>
                        <select name="editVoucherId" id="editCamVoucher" class="form-select">
                            <option value="">-- Không đính kèm --</option>
                            <c:forEach var="v" items="${vouchers}">
                                <option value="${v.voucherId}">${v.code} (Giảm ${v.discount}đ)</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-12">
                        <label class="form-label small fw-bold">Đổi ảnh Banner (Để trống nếu giữ nguyên)</label>
                        <input type="file" name="imageFile" class="form-control" accept="image/*">
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-primary fw-bold px-4">Lưu thay đổi</button>
                </div>
            </form>
        </div>
    </div>

    <div class="modal fade" id="editVoucherModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <form id="editVoucherForm" action="" method="post" class="modal-content">
                <div class="modal-header bg-warning text-dark">
                    <h5 class="fw-bold mb-0"><i class="bi bi-pencil-square me-2"></i>Cập nhật Voucher</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body row g-3 p-4">
                    <input type="hidden" name="voucherId" id="editVId">
                    <div class="col-md-6"><label class="small fw-bold">Mã Code</label><input type="text" name="editVoucherCode" id="editVCode" class="form-control" required></div>
                    <div class="col-md-6"><label class="small fw-bold">Số tiền giảm (đ)</label><input type="number" name="editVoucherDiscount" id="editVDiscount" class="form-control" required></div>
                    <div class="col-md-6"><label class="small fw-bold">Ngày bắt đầu</label><input type="date" name="editVoucherDateStart" id="editVStart" class="form-control" required></div>
                    <div class="col-md-6"><label class="small fw-bold">Ngày kết thúc</label><input type="date" name="editVoucherDateEnd" id="editVEnd" class="form-control" required></div>
                    <div id="editPriceArea" class="col-12 d-none"><label class="small fw-bold">Đơn tối thiểu</label><input type="number" name="editLowerbound" id="editVLower" class="form-control"></div>
                    <div id="editProductArea" class="col-12 d-none">
                        <label class="small fw-bold text-success">Chọn dòng SP áp dụng</label>
                        <select multiple name="productTypes" class="form-select" style="height: 120px;">
                            <c:forEach var="pt" items="${productTypes}"><option value="${pt.typeId}">${pt.product.name}</option></c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-warning fw-bold px-4">Lưu thay đổi</button>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Mở Modal Campaign và đổ dữ liệu
        function openEditCampaign(id, content, voucherId) {
            document.getElementById('editCamId').value = id;
            document.getElementById('editCamContent').value = content;
            document.getElementById('editCamVoucher').value = voucherId || "";
            
            var camModal = new bootstrap.Modal(document.getElementById('editCampaignModal'));
            camModal.show();
        }

        // Mở Modal Voucher và đổ dữ liệu đa hình
        function openEditVoucher(id, code, discount, start, end, isPrice, lowerbound) {
            document.getElementById('editVId').value = id;
            document.getElementById('editVCode').value = code;
            document.getElementById('editVDiscount').value = discount;
            document.getElementById('editVStart').value = start;
            document.getElementById('editVEnd').value = end;

            const form = document.getElementById('editVoucherForm');
            if (isPrice === 'true') {
                form.action = "${pageContext.request.contextPath}/admin/voucher/editPrice";
                document.getElementById('editPriceArea').classList.remove('d-none');
                document.getElementById('editProductArea').classList.add('d-none');
                document.getElementById('editVLower').value = lowerbound;
            } else {
                form.action = "${pageContext.request.contextPath}/admin/voucher/editProduct";
                document.getElementById('editProductArea').classList.remove('d-none');
                document.getElementById('editPriceArea').classList.add('d-none');
            }
            new bootstrap.Modal(document.getElementById('editVoucherModal')).show();
        }

        function toggleAddFields() {
            const isProd = document.getElementById('vType').value === 'byProduct';
            document.getElementById('addPriceField').classList.toggle('d-none', isProd);
            document.getElementById('addProductField').classList.toggle('d-none', !isProd);
        }

        document.addEventListener("DOMContentLoaded", function() {
            const params = new URLSearchParams(window.location.search);
            if (params.get('tab') === 'voucher') {
                bootstrap.Tab.getOrCreateInstance(document.querySelector('#voucher-tab')).show();
            }
        });
    </script>
</body>
</html>