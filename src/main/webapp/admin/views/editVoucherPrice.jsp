<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> <%
request.setAttribute("pageTitle", "Edit Voucher (Price)"); %> <%@ include
file="/WEB-INF/includes/admin_header.jspf" %>

<div class="container-xxl flex-grow-1 container-p-y">
  <div class="row">
    <div class="col-lg-8 mx-auto">
      <div class="card">
        <div class="card-header">
          <h5 class="card-title mb-0">Edit Voucher by Price</h5>
        </div>
        <div class="card-body">
          <form
            action="${pageContext.request.contextPath}/admin/voucher/editPrice"
            method="post"
            novalidate
          >
            <input type="hidden" name="voucherId" value="${editvoucherId}" />
            <div class="mb-3">
              <label for="editVoucherCodePrice" class="form-label">Code</label>
              <input
                id="editVoucherCodePrice"
                name="editVoucherCodePrice"
                class="form-control"
                value="${editVoucherCodePrice != null ? editVoucherCodePrice : ''}"
                required
              />
            </div>

            <div class="mb-3">
              <label for="editVoucherDiscountPrice" class="form-label"
                >Discount (%)</label
              >
              <input
                id="editVoucherDiscountPrice"
                name="editVoucherDiscountPrice"
                type="number"
                step="0.01"
                class="form-control"
                value="${editVoucherDiscountPrice != null ? editVoucherDiscountPrice : ''}"
                required
              />
            </div>

            <div class="row g-2">
              <div class="col-md-6 mb-3">
                <label for="editVoucherDateStartPrice" class="form-label"
                  >Start Date</label
                >
                <input
                  id="editVoucherDateStartPrice"
                  name="editVoucherDateStartPrice"
                  type="date"
                  class="form-control"
                  value="${editVoucherDateStartPrice != null ? editVoucherDateStartPrice : ''}"
                />
              </div>
              <div class="col-md-6 mb-3">
                <label for="editVoucherDateEndPrice" class="form-label">End Date</label>
                <input
                  id="editVoucherDateEndPrice"
                  name="editVoucherDateEndPrice"
                  type="date"
                  class="form-control"
                  value="${editVoucherDateEndPrice != null ? editVoucherDateEndPrice : ''}"
                />
              </div>
            </div>

            <div class="mb-3">
              <label for="editVoucherLowerbound" class="form-label"
                >Minimum Order Value</label
              >
              <input
                id="editVoucherLowerbound"
                name="editVoucherLowerbound"
                type="number"
                step="0.01"
                class="form-control"
                value="${editVoucherLowerbound != null ? editVoucherLowerbound : ''}"
              />
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
      <!-- possible error message -->
      <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger mt-3">${errorMessage}</div>
      </c:if>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/includes/admin_footer.jspf" %>
