<html>
<head>
    <title>Edit Voucher Price</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (required for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        // Script to show modal on page load
        $(document).ready(function() {
            $('#editVoucherPrice').modal('show');
        });
    </script>
</head>
<body>
<!-- Edit Voucher Price -->
<div class="show" id="editVoucherPrice" tabindex="-1" aria-labelledby="editVoucherModal" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editVoucherPriceLabel">Edit Voucher</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Form to edit a voucher -->
                <form action="editPrice" method="post">
                    <!-- Common Fields -->
                    <div class="mb-3">
                        <label for="editVoucherCodePrice" class="form-label">Voucher Code</label>
                        <input type="text" class="form-control" id="editVoucherCodePrice" name="editVoucherCodePrice" required>
                    </div>
                    <div class="mb-3">
                        <label for="editVoucherDiscountPrice" class="form-label">Discount (%)</label>
                        <input type="number" class="form-control" id="editVoucherDiscountPrice" name="editVoucherDiscountPrice" step="0.01" value="${editVoucherDiscountPrice}" required>
                    </div>
                    <div class="mb-3">
                        <label for="editVoucherDateStartPrice" class="form-label">Start Date</label>
                        <input type="date" class="form-control" id="editVoucherDateStartPrice" name="editVoucherDateStartPrice" value="${editVoucherDateStartPrice}" required>
                    </div>
                    <div class="mb-3">
                        <label for="editvoucherDateEndPrice" class="form-label">End Date</label>
                        <input type="date" class="form-control" id="editVoucherDateEndPrice" name="editVoucherDateEndPrice" value="${editVoucherDateEndPrice}" required>
                    </div>

                    <div class="mb-3">
                        <label for="editVoucherLowerbound" class="form-label">Minimum Order Value</label>
                        <input type="number" class="form-control" id="editVoucherLowerbound" name="editVoucherLowerbound" step="0.01">

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
