<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <!-- Shipping Info -->
    <div class="card mb-4 shadow-sm" style="border-radius: 12px; overflow: hidden;">
        <div class="card-header" 
             style="background-color: #000000; 
                    font-family: 'Poppins', sans-serif; /* Font Poppins */
                    font-weight: bold; 
                    font-size: 18px; 
                    color: #ffffff; /* Màu chữ trắng */
                    padding: 15px 20px; 
                    text-transform: uppercase;">
            Thông Tin Giao Hàng
        </div>

        <div class="card-body d-flex justify-content-between align-items-center" 
             style="padding: 20px; background-color: #ffffff;">
            <!-- Order Details -->
            <div style="max-width: 80%; font-family: 'Roboto', sans-serif;">
                <h5 class="text-highlight mb-2" 
                    style="font-weight: bold; color: #007bff; font-size: 18px;">
                    Mã đơn hàng: ${order.orderId}
                </h5>
                <p class="mb-1" style="font-size: 16px; color: #333;">
                    Khách hàng: 
                    <span class="fw-bold">${order.customer.name}</span>
                </p>
                <p class="small text-muted" style="margin-bottom: 0;">
                    ${order.streetNumber}, ${order.ward}, ${order.district}, ${order.cityOfProvince}
                </p>
            </div>

            <!-- Edit Button -->
			<a href="javascript:void(0);" 
			   onclick="document.getElementById('addressModal').style.display='flex';" 
			   class="btn-edit d-flex align-items-center justify-content-center"
			   style="background-color: #007bff; color: #ffffff; width: 40px; height: 40px; 
			          border-radius: 50%; text-decoration: none; transition: transform 0.3s;">
			    <img src="https://cdn-icons-png.flaticon.com/512/1250/1250615.png" 
			         width="18" alt="Edit Icon" style="filter: invert(1);">
			</a>
			            
			<!-- Modal -->
			<div id="addressModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 9999; align-items: center; justify-content: center;">
			    <div style="background: white; width: 90%; max-width: 500px; padding: 20px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); position: relative;">
			        <h2 style="text-align: center; margin-bottom: 20px;">Nhập Thông Tin</h2>

			        <!-- Begin Form -->
					<form id="addressForm" method="POST" action="${pageContext.request.contextPath}/customer/order/shippingInfo" onsubmit="return validateForm()">
					    <input type="hidden" name="orderId" value="${order.orderId}" />
					
					    <!-- Phone -->
					    <div style="margin-bottom: 15px;">
					        <label for="phone" style="display: block; font-weight: bold;">Số điện thoại</label>
					        <input type="text" id="phone" name="phone" value="${order.phone}" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required />
					    </div>
					
					    <!-- City/Province -->
					    <div style="margin-bottom: 15px;">
					        <label for="cityOfProvince" style="display: block; font-weight: bold;">Tỉnh/Thành phố</label>
					        <select id="cityOfProvince" name="cityOfProvince" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" onchange="document.getElementById('cityOtherInput').style.display = this.value === 'other' ? 'block' : 'none';" required>
					            <option value="" disabled selected>Chọn tỉnh/thành phố</option>
					            <option value="Hà Nội" ${order.cityOfProvince == 'Hà Nội' ? 'selected' : ''}>Hà Nội</option>
					            <option value="TP. Hồ Chí Minh" ${order.cityOfProvince == 'TP. Hồ Chí Minh' ? 'selected' : ''}>TP. Hồ Chí Minh</option>
					            <option value="Đà Nẵng" ${order.cityOfProvince == 'Đà Nẵng' ? 'selected' : ''}>Đà Nẵng</option>
					            <option value="other" ${order.cityOfProvince != 'Hà Nội' && order.cityOfProvince != 'TP. Hồ Chí Minh' && order.cityOfProvince != 'Đà Nẵng' ? 'selected' : ''}>Khác...</option>
					        </select>
					        <input type="text" id="cityOtherInput" name="cityOther" value="${order.cityOfProvince != 'Hà Nội' && order.cityOfProvince != 'TP. Hồ Chí Minh' && order.cityOfProvince != 'Đà Nẵng' ? order.cityOfProvince : ''}" placeholder="Nhập tên tỉnh/thành phố" style="display: ${order.cityOfProvince != 'Hà Nội' && order.cityOfProvince != 'TP. Hồ Chí Minh' && order.cityOfProvince != 'Đà Nẵng' ? 'block' : 'none'}; margin-top: 10px; width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" />
					    </div>
					
					    <!-- District -->
					    <div style="margin-bottom: 15px;">
					        <label for="district" style="display: block; font-weight: bold;">Quận/Huyện</label>
					        <select id="district" name="district" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" onchange="document.getElementById('districtOtherInput').style.display = this.value === 'other' ? 'block' : 'none';" required>
					            <option value="" disabled selected>Chọn quận/huyện</option>
					            <option value="Ba Đình" ${order.district == 'Ba Đình' ? 'selected' : ''}>Ba Đình</option>
					            <option value="Hoàn Kiếm" ${order.district == 'Hoàn Kiếm' ? 'selected' : ''}>Hoàn Kiếm</option>
					            <option value="other" ${order.district != 'Ba Đình' && order.district != 'Hoàn Kiếm' ? 'selected' : ''}>Khác...</option>
					        </select>
					        <input type="text" id="districtOtherInput" name="districtOther" value="${order.district != 'Ba Đình' && order.district != 'Hoàn Kiếm' ? order.district : ''}" placeholder="Nhập tên quận/huyện" style="display: ${order.district != 'Ba Đình' && order.district != 'Hoàn Kiếm' ? 'block' : 'none'}; margin-top: 10px; width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" />
					    </div>
					
					    <!-- Ward -->
					    <div style="margin-bottom: 15px;">
					        <label for="ward" style="display: block; font-weight: bold;">Phường/Xã</label>
					        <select id="ward" name="ward" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" onchange="document.getElementById('wardOtherInput').style.display = this.value === 'other' ? 'block' : 'none';" required>
					            <option value="" disabled selected>Chọn phường/xã</option>
					            <option value="Phường 1" ${order.ward == 'Phường 1' ? 'selected' : ''}>Phường 1</option>
					            <option value="Phường 2" ${order.ward == 'Phường 2' ? 'selected' : ''}>Phường 2</option>
					            <option value="other" ${order.ward != 'Phường 1' && order.ward != 'Phường 2' ? 'selected' : ''}>Khác...</option>
					        </select>
					        <input type="text" id="wardOtherInput" name="wardOther" value="${order.ward != 'Phường 1' && order.ward != 'Phường 2' ? order.ward : ''}" placeholder="Nhập tên phường/xã" style="display: ${order.ward != 'Phường 1' && order.ward != 'Phường 2' ? 'block' : 'none'}; margin-top: 10px; width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" />
					    </div>
					
					    <!-- Street Number -->
					    <div style="margin-bottom: 15px;">
					        <label for="streetNumber" style="display: block; font-weight: bold;">Số nhà/Đường</label>
					        <input type="text" id="streetNumber" name="streetNumber" value="${order.streetNumber}" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required />
					    </div>
					
					    <!-- Submit and Close Buttons -->
					    <div style="display: flex; justify-content: space-between; margin-top: 20px;">
					        <button type="button" onclick="document.getElementById('addressModal').style.display='none';" 
					                style="padding: 10px 20px; background-color: #aaa; color: white; border: none; border-radius: 4px;">
					            Đóng
					        </button>
					        <button type="submit" style="padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px;">
					            Lưu
					        </button>
					    </div>
					</form>
					<!-- End Form -->
					
					<script>
				    function validateForm() {
				        // Lấy các giá trị từ form
				        const phone = document.getElementById('phone').value.trim();
				        const cityOfProvince = document.getElementById('cityOfProvince').value;
				        const cityOtherInput = document.getElementById('cityOtherInput').style.display === 'block' ? document.getElementById('cityOtherInput').value.trim() : '';
				        const district = document.getElementById('district').value;
				        const districtOtherInput = document.getElementById('districtOtherInput').style.display === 'block' ? document.getElementById('districtOtherInput').value.trim() : '';
				        const ward = document.getElementById('ward').value;
				        const wardOtherInput = document.getElementById('wardOtherInput').style.display === 'block' ? document.getElementById('wardOtherInput').value.trim() : '';
				        const streetNumber = document.getElementById('streetNumber').value.trim();
				
				        // Kiểm tra thông tin nhập
				        if (!phone) {
				            alert('Vui lòng nhập số điện thoại.');
				            return false;
				        }
				
				        if (!cityOfProvince || (cityOfProvince === 'other' && !cityOtherInput)) {
				            alert('Vui lòng chọn hoặc nhập tỉnh/thành phố.');
				            return false;
				        }
				
				        if (!district || (district === 'other' && !districtOtherInput)) {
				            alert('Vui lòng chọn hoặc nhập quận/huyện.');
				            return false;
				        }
				
				        if (!ward || (ward === 'other' && !wardOtherInput)) {
				            alert('Vui lòng chọn hoặc nhập phường/xã.');
				            return false;
				        }
				
				        if (!streetNumber) {
				            alert('Vui lòng nhập số nhà/đường.');
				            return false;
				        }
				
				        // Nếu mọi thông tin hợp lệ, cho phép form submit
				        return true;
				    }
				</script>


			    </div>
			</div>
            
        </div>
    </div>

    <!-- Order Items -->
    <div class="card mb-4"> <!-- Thêm lớp Bootstrap để tạo khoảng cách -->
        <div class="card-header" 
             style="background-color: #000000; 
                    font-family: 'Poppins', sans-serif; 
                    font-weight: bold; 
                    font-size: 18px; 
                    color: #ffffff; 
                    padding: 15px 20px;">
            DANH SÁCH SẢN PHẨM
        </div>
        <div class="card-body">
            <ul class="list-group">
                <c:forEach var="item" items="${orderItems}">
                    <li class="list-group-item d-flex align-items-center" 
                        style="border: 1px solid #ddd; border-radius: 10px; margin-bottom: 15px; padding: 15px; 
                               transition: background-color 0.3s ease;">
                        <!-- Product Image -->
                        <img src="${item.productType.product.productImages[0].productImage}" 
                             alt="Product Image" class="product-image me-3" 
                             style="width: 100px; height: 100px; object-fit: cover; border-radius: 8px;">
                        
                        <!-- Product Details -->
                        <div class="product-details" style="flex-grow: 1;">
                            <h6 class="mb-1" style="font-weight: 600; font-size: 16px; color: #333;">${item.productType.product.name}</h6>
                            <p class="small text-muted mb-1">Kích thước: ${item.productType.length} x ${item.productType.width} x ${item.productType.height}</p>
                            <p class="small text-muted mb-0">Số lượng: ${item.quantity} | Đơn giá: ${item.price}</p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    
    <!-- Voucher -->
    <div class="promo-checkbox" style="margin-top: 20px; display: flex; align-items: center; gap: 15px;"> 
        <!-- Nút Xem Voucher -->
        <div class="promo-code">
            <a href="javascript:void(0);" class="btn-list-voucher" 
               data-bs-toggle="modal" data-bs-target="#voucherModal"
			   style="background-color: #000000; 
			          color: white; 
			          border: none; 
			          padding: 17px 24px; 
			          border-radius: 8px; 
			          cursor: pointer; 
			          font-family: 'Poppins', sans-serif; 
			          font-weight: 600; 
			          font-size: 16px; 
			          text-decoration: none; /* Bỏ gạch chân */
			          transition: background-color 0.3s ease, transform 0.2s ease;" 
			   onmouseover="this.style.backgroundColor='#333333'; this.style.transform='scale(1.05)';" 
			   onmouseout="this.style.backgroundColor='#000000'; this.style.transform='scale(1)';">			    
			    Xem voucher
			</a>
			
			<!-- Modal list voucher-->
			<div class="modal fade" id="voucherModal" tabindex="-1" aria-labelledby="voucherModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content" style="background-color: #fff; border-radius: 12px; color: #333; padding: 20px;">
			      <div class="modal-header" style="border-bottom: 2px solid #e0e0e0; background-color: #f9f9f9;">
			        <h5 class="modal-title" id="voucherModalLabel" style="font-weight: 600; font-size: 1.25rem; color: #333;">Voucher Details</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="border: none; background: transparent; color: #333;"></button>
			      </div>
			      <form method="POST" action="${pageContext.request.contextPath}/customer/order/voucher">
			      	<input type="hidden" name="orderId" value="${order.orderId}" />
			        <div class="modal-body" style="font-size: 1rem; color: #333;">
					   <c:if test="${not empty listVoucher}">
					        <!-- Radio button để bỏ chọn voucher -->
					        <input type="radio" name="selectedVoucher" 
					               value="" 
					               id="noVoucher" 
					               style="display: none;" 
					               <c:if test="${empty voucherId}">checked</c:if> />
					
					        <ul style="list-style-type: none; padding: 0;">
					            <c:forEach var="voucher" items="${listVoucher}">
								    <li style="margin-bottom: 20px; border-bottom: 1px solid #ddd; padding-bottom: 15px;">
								        <input type="radio" name="selectedVoucher" 
								               value="${voucher.voucherId}" 
								               id="voucher${voucher.code}" 
								               style="margin-right: 15px; accent-color: #007bff;" 
								               <c:if test="${voucherId == voucher.voucherId}">checked</c:if> />
								        <label for="voucher${voucher.code}" style="font-size: 1.1rem; color: #333; font-weight: 500;">
								            ${voucher.code} - <fmt:formatNumber value="${voucher.discount}" pattern="#%" />
								        </label>
								        <p style="font-size: 0.9rem; color: #777;">Hạn sử dụng: ${voucher.dateEnd}</p>
								        
								        <!-- Kiểm tra nếu voucher.voucherByPriceId == 0 -->
								        <c:if test="${voucher.voucherByPriceId == 0}">
								            <p style="font-size: 1rem; color: #333; font-weight: 500;">Voucher áp dụng với sản phẩm</p>
								        </c:if>
								    </li>
								</c:forEach>
					        </ul>
			          	</c:if>
			          	
			          <c:if test="${empty listVoucher}">
			            <p style="font-size: 1rem; color: #888;">Không có voucher nào đủ điều kiện áp dụng</p>
			          </c:if>
			          
			          <!--  Voucher By Price chưa đủ đk áp dụng  -->
			          <c:if test="${not empty listVoucherCanNotApply}">
						  <ul style="list-style-type: none; padding: 0;">
						    <c:forEach var="voucher" items="${listVoucherCanNotApply}">
						      <li style="margin-bottom: 20px; border-bottom: 1px solid #ddd; padding-bottom: 15px; opacity: 0.6; pointer-events: none;">
						        <div style="display: flex; align-items: center;">
						          <input type="radio" disabled name="disabledVoucher" value="${voucher.voucherId}" id="disabledVoucher${voucher.code}" style="margin-right: 15px; accent-color: #aaa;" />
						          <label for="disabledVoucher${voucher.code}" style="font-size: 1.1rem; color: #aaa; font-weight: 500;">
						            ${voucher.code} - <fmt:formatNumber value="${voucher.discount}" pattern="#%" />
						          </label>
						        </div>
						        <p style="font-size: 0.9rem; color: #aaa;">Hạn sử dụng: ${voucher.dateEnd}</p>
						        <p style="font-size: 0.85rem; color: #b71c1c; margin-top: 5px;">
						            Chi thêm <fmt:formatNumber value="${voucher.lowerbound - order.totalCost}" pattern="#,###"/>đ để được áp dụng voucher này.
						        </p>
						      </li>
						    </c:forEach>
						  </ul>
						</c:if>
									          
			        </div>
			        <div class="modal-footer" style="border-top: 2px solid #e0e0e0; background-color: #f9f9f9;">
			          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="background-color: #6c757d; color: white; border: none; padding: 8px 16px; border-radius: 4px;">Close</button>
			          <button type="submit" class="btn btn-primary" style="background-color: #007bff; color: white; border: none; padding: 8px 16px; border-radius: 4px;">Select Voucher</button>
			        </div>
			      </form>
			    </div>
			  </div>
			</div>
        </div>
        <!-- Voucher đã chọn hoặc thông báo -->
        <div class="selected-voucher" 
             style="padding: 10px; background-color: #f8f9fa; border-radius: 8px; 
                    border: 1px solid #ddd; font-family: 'Poppins', sans-serif; font-size: 14px; color: #333;">
            <c:choose>
                <c:when test="${selectedVoucher == null || selectedVoucher eq ''}">
                    <span>Bạn chưa chọn voucher nào.</span>
                </c:when>
                <c:otherwise>
                    <span>Voucher đã chọn: 
                        <span style="font-weight: bold; color: #007bff;">${selectedVoucher}</span>
                    </span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>


<form method="POST" action="${pageContext.request.contextPath}/customer/order/paymentMethod" 
      style="background: #fff; width: 100%; max-width: 500px; padding: 30px; margin: 40px auto; border-radius: 16px; 
             box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1); transition: transform 0.3s ease, box-shadow 0.3s ease;">
    <input type="hidden" name="orderId" value="${order.orderId}" />
    <!-- Tiêu đề -->
    <h3 style="text-align: center; font-size: 1.3rem; color: #000; margin-bottom: 20px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
        Chọn Phương Thức Thanh Toán
    </h3>

    <!-- Ship COD (Mặc định chọn) -->
    <div style="display: flex; align-items: center; margin-bottom: 20px;">
        <input type="radio" id="codPayment" name="paymentMethod" value="cod" 
               checked onchange="document.getElementById('qrContainer').style.display='none'" 
               required style="margin-right: 10px; accent-color: #333;">
        <label for="codPayment" style="font-size: 1rem; color: #555; cursor: pointer;">Thanh toán khi nhận hàng (Ship COD)</label>
    </div>

    <!-- Thanh toán ngay -->
    <div style="display: flex; align-items: center; margin-bottom: 20px;">
        <input type="radio" id="onlinePayment" name="paymentMethod" value="online" 
               onchange="document.getElementById('qrContainer').style.display='block'" 
               required style="margin-right: 10px; accent-color: #333;">
        <label for="onlinePayment" style="font-size: 1rem; color: #555; cursor: pointer;">Thanh toán ngay bằng mã</label>
    </div>

    <!-- QR Code Container -->
    <div id="qrContainer" style="display: none; text-align: center; margin-bottom: 20px;">
        <img id="qrImage" src="${paymentCode}" alt="QR Code" style="max-width: 100%; height: auto; border-radius: 12px; border: 1px solid #ccc;">
    </div>

        <!-- Hóa đơn thanh toán -->
        <div style="background: #fff; border-radius: 12px; padding: 20px; border: 1px solid #ccc; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); margin-bottom: 20px;">
            <h4 style="text-align: center; margin-bottom: 15px; font-size: 1.2rem; font-weight: bold; color: #000;">Shopping Bill</h4>
            <table style="width: 100%; font-size: 0.9rem; border-collapse: collapse; color: #333;">
                <tbody>
                    <tr>
                        <td style="padding: 10px; color: #555;">Shipping Fee:</td>
                        <td style="padding: 10px; text-align: right;"><fmt:formatNumber value= "${ship}" type="number" /> đ</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; color: #555;">Discount:</td>
                        <td style="padding: 10px; text-align: right;">
                            <c:choose>
                                <c:when test="${isVoucherByProduct == true}">
                                    - <fmt:formatNumber value="${sale}" type="number" /> đ
                                </c:when>
                                <c:when test="${isVoucherByProduct == false}">
                                    - <fmt:formatNumber value="${sale}" type="number" /> đ
                                </c:when>
                                <c:otherwise>
                                    - 0 đ
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; color: #555;">Price Total:</td>
                        <td style="padding: 10px; text-align: right;"><fmt:formatNumber value="${order.totalCost}" type="number" /> đ</td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr style="font-size: 1.1rem; font-weight: bold;">
                        <td style="padding: 10px; color: #000;">Total:</td>
                        <td style="padding: 10px; text-align: right; color: #000;"><fmt:formatNumber value="${order.actualCost}" type="number" /> đ</td>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        <c:if test="${not empty errorMessage}">
		    <div style="color: red; text-align: center; margin-bottom: 20px;">
		        ${errorMessage}
		    </div>
		</c:if>
		
        <!-- Nút Mua Ngay -->
    <button type="submit" 
            style="display: block; width: 100%; padding: 12px 20px; font-size: 1rem; font-weight: bold; color: #fff; 
                   background: #333; border: none; border-radius: 8px; text-align: center; cursor: pointer; 
                   transition: background 0.3s ease, transform 0.2s ease;"
            onmouseover="this.style.background='#000'; this.style.transform='scale(1.05)';"
            onmouseout="this.style.background='#333'; this.style.transform='scale(1)';"
            onmousedown="this.style.background='#555';">
        Mua Ngay
    </button>
</form>

		<svg xmlns="http://www.w3.org/2000/svg" style="display: none">
		  <symbol id="icon-shopping-bag" viewBox="0 0 24 24">
		    <path d="M20 7h-4v-3c0-2.209-1.791-4-4-4s-4 1.791-4 4v3h-4l-2 17h20l-2-17zm-11-3c0-1.654 1.346-3 3-3s3 1.346 3 3v3h-6v-3zm-4.751 18l1.529-13h2.222v1.5c0 .276.224.5.5.5s.5-.224.5-.5v-1.5h6v1.5c0 .276.224.5.5.5s.5-.224.5-.5v-1.5h2.222l1.529 13h-15.502z" />
		  </symbol>
		</svg>

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>


