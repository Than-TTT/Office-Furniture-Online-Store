<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> <%
request.setAttribute("pageTitle", "Edit Voucher (Product)"); %> <%@ include
file="/WEB-INF/includes/admin_header.jspf" %>

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

<%@ include file="/WEB-INF/includes/admin_footer.jspf" %>