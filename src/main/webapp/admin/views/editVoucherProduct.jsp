<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("pageTitle", "Edit Voucher (Product)"); %>
<%@ include file="/WEB-INF/includes/admin_header.jspf" %>

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
              <label for="editVoucherDiscountProduct" class="form-label">Discount (%)</label>
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
                <label for="editVoucherDateStartProduct" class="form-label">Start Date</label>
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
              <label class="form-label">Applicable Product Types</label>
              <div class="mb-2">
                <input type="text" class="form-control form-control-sm" id="productTypeSearch" placeholder="Search products..." onkeyup="filterProductTypes()">
              </div>
              <div class="border rounded p-2" style="max-height: 250px; overflow-y: auto;" id="productTypeCheckboxes">
                <c:forEach var="productType" items="${productTypes}">
                  <div class="form-check product-type-item">
                    <input class="form-check-input" type="checkbox" name="productTypes" value="${productType.typeId}" id="pt_${productType.typeId}"
                      <c:if test="${selectedTypeIds.contains(productType.typeId)}">checked</c:if>
                    >
                    <label class="form-check-label" for="pt_${productType.typeId}">
                      <strong>${productType.product.name}</strong> 
                      <span class="text-muted">| ${productType.color}</span>
                      <span class="badge bg-secondary ms-1">${productType.price} VND</span>
                    </label>
                  </div>
                </c:forEach>
              </div>
              <div class="mt-2 d-flex gap-2">
                <button type="button" class="btn btn-outline-secondary btn-sm" onclick="selectAllProductTypes()">Select All</button>
                <button type="button" class="btn btn-outline-secondary btn-sm" onclick="deselectAllProductTypes()">Deselect All</button>
                <span class="ms-auto text-muted small" id="selectedCount">0 selected</span>
              </div>
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

<script>
  function filterProductTypes() {
    var searchText = document.getElementById('productTypeSearch').value.toLowerCase();
    var items = document.querySelectorAll('.product-type-item');
    items.forEach(function(item) {
      var label = item.querySelector('label').textContent.toLowerCase();
      item.style.display = label.includes(searchText) ? '' : 'none';
    });
  }
  
  function selectAllProductTypes() {
    var checkboxes = document.querySelectorAll('#productTypeCheckboxes input[type="checkbox"]');
    checkboxes.forEach(function(cb) {
      if (cb.closest('.product-type-item').style.display !== 'none') {
        cb.checked = true;
      }
    });
    updateSelectedCount();
  }
  
  function deselectAllProductTypes() {
    var checkboxes = document.querySelectorAll('#productTypeCheckboxes input[type="checkbox"]');
    checkboxes.forEach(function(cb) { cb.checked = false; });
    updateSelectedCount();
  }
  
  function updateSelectedCount() {
    var count = document.querySelectorAll('#productTypeCheckboxes input[type="checkbox"]:checked').length;
    document.getElementById('selectedCount').textContent = count + ' selected';
  }
  
  document.addEventListener('DOMContentLoaded', function() {
    var checkboxes = document.querySelectorAll('#productTypeCheckboxes input[type="checkbox"]');
    checkboxes.forEach(function(cb) {
      cb.addEventListener('change', updateSelectedCount);
    });
    updateSelectedCount();
  });
</script>

<%@ include file="/WEB-INF/includes/admin_footer.jspf" %>