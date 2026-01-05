<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Voucher Product</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (required for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        // Script to show modal on page load
        $(document).ready(function() {
            $('#editVoucherProduct').modal('show');
        });
    </script>
</head>
<body>
<!-- Edit Voucher Product -->
<div class="show" id="editVoucherProduct" tabindex="-1" aria-labelledby="editVoucherModal" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editVoucherProductLabel">Edit Voucher</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Form to edit a voucher -->
                <form action="editProduct" method="post">
                    <!-- Common Fields -->
                    <div class="mb-3">
                        <label for="editVoucherCodeProduct" class="form-label">Voucher Code</label>
                        <input type="text" class="form-control" id="editVoucherCodeProduct" name="editVoucherCodeProduct" value="${editVoucherCodeProduct}" required>
                    </div>
                    <div class="mb-3">
                        <label for="editVoucherDiscountProduct" class="form-label">Discount (%)</label>
                        <input type="number" class="form-control" id="editVoucherDiscountProduct" name="editVoucherDiscountProduct" step="0.01" value="${editVoucherDiscountProduct}" required>
                    </div>
                    <div class="mb-3">
                        <label for="editVoucherDateStartProduct" class="form-label">Start Date</label>
                        <input type="date" class="form-control" id="editVoucherDateStartProduct" name="editVoucherDateStartProduct" value="${editVoucherDateStartProduct}" required>
                    </div>
                    <div class="mb-3">
                        <label for="editvoucherDateEndProduct" class="form-label">End Date</label>
                        <input type="date" class="form-control" id="editVoucherDateEndProduct" name="editVoucherDateEndProduct" value="${editVoucherDateEndProduct}" required>
                    </div>

                    <div class="mb-3">
                        <label for="productTypes" class="form-label">Applicable Product Types</label>
                        <select multiple class="form-select" id="productTypes" name="productTypes">
                            <c:forEach var="productType" items="${productTypes}">
                                <option value="${productType.getTypeId()}">${productType.getProduct().getName()} | ${productType.getColor()}</option>
                            </c:forEach>
                        </select>
                        <small class="text-muted">Hold CTRL (Windows) or CMD (Mac) to select multiple types.</small>
                        <div class="mb-3">
                            <input type="hidden" name="voucherId" id="editVoucherId" value="${editvoucherId}">
                            <button type="submit" class="btn btn-danger btn-sm">
                                Edit
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
