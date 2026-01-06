<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %> <%
request.setAttribute("pageTitle", "Edit Campaign"); %> <%@ include
file="/WEB-INF/includes/admin_header.jspf" %>

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
                  <option value="${voucher.voucherId}" ${voucher.voucherId == currentVoucherId ? 'selected' : ''}>
                    ${voucher.code} - Discount: ${voucher.discount}%
                  </option>
                </c:forEach>
              </select>
            </div>

            <div class="mb-3">
              <label for="image" class="form-label">Campaign Image</label>
              <c:if test="${not empty image}">
                <div class="mb-2">
                  <img src="${pageContext.request.contextPath}/${image}" alt="Current Image" style="max-width: 200px; max-height: 150px; border-radius: 4px;">
                  <p class="text-muted small">Current image. Upload a new one to replace.</p>
                </div>
              </c:if>
              <input type="file" class="form-control" id="campaignImage" name="campaignImage" accept="image/*">
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