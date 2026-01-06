<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Product Detail</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

</head>

<body>
	<div class="container my-5">
		<form id="productForm" action="${pageContext.request.contextPath}/customer/cart/add"
			method="post">
		<div class="row">
			<div class="col-md-6">
				<div class="card">
					<img src="${product.productImages[0].productImage}"
						class="card-img-top" alt="${product.name}">
				</div>
			</div>
			<div class="col-md-6">
				<h3>${product.name}</h3>
				<h4 class="text-danger font-weight-bold" style="font-size: 24px;">
					<span id="price-display">${product.productTypes[0].price}</span>
				</h4>

				<p>${product.descript != null ? product.descript : "Không có mô tả"}</p>
				<p>${product.category != null ? product.category.categoryName : 'Chưa có danh mục'}</p>

				<!-- Dropdown cho các thuộc tính product type -->
				<div class="my-3">
					<label for="productTypeSelect"><strong>Chọn loại
							sản phẩm:</strong></label> <select id="productTypeSelect" class="form-control"
						onchange="updateProductTypeDetails(this)" name = "SelectedTypeId">
						<c:forEach var="type" items="${product.productTypes}">
							<option value="${type.typeId}" data-color="${type.color}"
								data-material="${type.material}" data-price="${type.price}"
								data-quantity="${type.quantity}" data-height="${type.height}"
								data-length="${type.length}" data-weight="${type.weight}"
								data-width="${type.width}">${type.color}-
								${type.material} - ${type.price} VNĐ</option>
						</c:forEach>
					</select>
				</div>

				<!-- Hiển thị chi tiết thuộc tính được chọn -->
				<div id="product-details" class="my-3">
					<p>
						<strong>Màu sắc:</strong> <span id="color-display">${product.productTypes[0].color}</span>
					</p>
					<p>
						<strong>Chất liệu:</strong> <span id="material-display">${product.productTypes[0].material}</span>
					</p>
					<p>
						<strong>Kích thước:</strong> <span id="dimensions-display">${product.productTypes[0].length}x${product.productTypes[0].width}x${product.productTypes[0].height}
							cm</span>
					</p>
					<p>
						<strong>Trọng lượng:</strong> <span id="weight-display">${product.productTypes[0].weight}
							kg</span>
					</p>
					<p>
						<strong>Số lượng:</strong> <span id="quantity-display">${product.productTypes[0].quantity}</span>
					</p>
				</div>

				<!-- Điều chỉnh số lượng -->
				<div class="input-group my-3">
					<div class="input-group-prepend">
						<button class="btn btn-outline-secondary" type="button"
							onclick="decrementQuantity()">-</button>
					</div>
					<input type="text" class="form-control text-center" id="count" name="quantity"
						value="${product.productTypes[0].quantity}" readonly
						style="max-width: 80px;">
					<div class="input-group-append">
						<button class="btn btn-outline-secondary" type="button"
							onclick="incrementQuantity()">+</button>
					</div>
				</div>

				<!-- Nút thêm vào giỏ -->
				<button type="submit" class="btn btn-primary">Thêm vào giỏ</button>
			</div>
			</div>
			</form>
			<script>
			function updateProductTypeDetails(select) {
			    // Get the selected option
			    const selectedOption = select.options[select.selectedIndex];

			    // Get the attributes from the selected option
			    const color = selectedOption.getAttribute("data-color");
			    const material = selectedOption.getAttribute("data-material");
			    const price = selectedOption.getAttribute("data-price");
			    const quantity = selectedOption.getAttribute("data-quantity");
			    const height = selectedOption.getAttribute("data-height");
			    const length = selectedOption.getAttribute("data-length");
			    const weight = selectedOption.getAttribute("data-weight");
			    const width = selectedOption.getAttribute("data-width");

			    // Update the display elements with the selected option's attributes
			    document.getElementById("price-display").innerText = price + " VNĐ";
			    document.getElementById("color-display").innerText = color;
			    document.getElementById("material-display").innerText = material;
			    document.getElementById("dimensions-display").innerText = length + "x" + width + "x" + height + " cm";
			    document.getElementById("weight-display").innerText = weight + " kg";
			    document.getElementById("quantity-display").innerText = quantity;
			    const priceElement = document.getElementById("price-display");
			    priceElement.style.fontSize = "24px";
			    priceElement.style.color = "red";
			    priceElement.style.fontWeight = "bold";

			    // Update the quantity input field
			    document.getElementById("count").value = quantity;
			}

			// Add change event listener to the dropdown
			document.getElementById('productTypeSelect').addEventListener('change', function() {
			    updateProductTypeDetails(this);
			});

			// Add click event listener to each option in the dropdown
			document.querySelectorAll('#productTypeSelect option').forEach(option => {
			    option.addEventListener('click', function() {
			        updateProductTypeDetails(document.getElementById('productTypeSelect'));
			    });
			});

			// Call the function on page load to set initial values
			document.addEventListener('DOMContentLoaded', function() {
			    updateProductTypeDetails(document.getElementById('productTypeSelect'));
			});

			</script>
		</div>
		<ul class="nav nav-tabs mt-5" role="tablist">
			<li class="nav-item"><a class="nav-link active"
				data-toggle="tab" href="#tabs-1" role="tab" aria-selected="true">Mô
					tả</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#tabs-2" role="tab" aria-selected="false">Thông tin</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="tab"
				href="#tabs-3" role="tab" aria-selected="false">Đánh giá</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="tabs-1" role="tabpanel">
				<h6 class="my-3">MÔ TẢ SẢN PHẨM</h6>
				<p>${product.descript}</p>
			</div>
			<div class="tab-pane" id="tabs-2" role="tabpanel">
				<h6 class="my-3">THÔNG TIN SẢN PHẨM</h6>
				<c:forEach var="type" items="${product.productTypes}">
					<div class="border p-3 mb-3">
						<p>
							<strong>Color:</strong> ${type.color}
						</p>
						<p>
							<strong>Material:</strong> ${type.material}
						</p>
						<p>
							<strong>Dimensions:</strong> ${type.length} x ${type.width} x
							${type.height}
						</p>
						<p>
							<strong>Weight:</strong> ${type.weight}
						</p>
						<p>
							<strong>Price:</strong> ${type.price}
						</p>
						<p>
							<strong>Quantity:</strong> ${type.quantity}
						</p>
					</div>
				</c:forEach>
			</div>
			<div class="tab-pane" id="tabs-3" role="tabpanel">
				<h6 class="my-3">ĐÁNH GIÁ SẢN PHẨM</h6>
				<div class="border p-3">
					<c:if test="${not empty reviews}">
						<ul class="list-group">
							<c:forEach var="review" items="${reviews}">
								<li class="list-group-item"><strong>${review.customer.name}</strong>
									- <span>${review.rating} stars</span>
									<p>${review.content}</p> <small>Reviewed on:
										${review.createAt}</small></li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${empty reviews}">
						<p class="text-muted text-center">No reviews for this product
							yet.</p>
					</c:if>
				</div>
			</div>
		</div>
		<!-- Related Product Section Begin -->
		<section class="related-product">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<div class="section-title related__product__title">
							<h2>Sản phẩm liên quan</h2>
						</div>
					</div>
				</div>

				<div class="row">
					<!-- Hiển thị sản phẩm liên quan -->
					<c:forEach var="product" items="${relatedProducts}">
						<div class="col-lg-3 col-md-4 col-sm-6">
							<div class="product__item">
								<div class="product__item__pic">

									<img src="${product.productImages[0].productImage}"
										alt="${product.name}">
									<ul class="product__item__pic__hover">
										<li><a href="#"
											onclick="updateFavorite(${product.productId})"> <i
												class="fa fa-heart"></i>
										</a></li>
										<li><a href="detail?id=${product.productId}">
												<i class="fa fa-search"></i>
										</a></li>
										<li><a href="#"
											onclick="submitForm(${product.productId}, ${check})"> <i
												class="fa fa-shopping-cart"></i>
										</a></li>
									</ul>
								</div>
								<div class="product__item__text">
									<h6>
										<a href="detail?id=${product.productId}">${product.name}</a>
									</h6>
									<h5>${product.productTypes[0].price}</h5>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>

				<!-- Phân trang -->
				<div class="row">
					<div class="flex-c-m flex-w w-full p-t-38">
						<!-- Nút phân trang đầu tiên -->
						<c:if test="${currentPage > 1}">
							<button onclick="changePage(1)"
								class="flex-c-m how-pagination1 trans-04 m-all-7">First</button>
						</c:if>

						<!-- Các nút phân trang giữa -->
						<c:forEach begin="1" end="${totalPages}" varStatus="loop">
							<button onclick="changePage('${loop.index}')"
								class="flex-c-m how-pagination1 trans-04 m-all-7 ${currentPage == loop.index ? 'active-pagination1' : ''}">
								${loop.index}</button>
						</c:forEach>

						<!-- Nút phân trang cuối cùng -->
						<c:if test="${currentPage < totalPages}">
							<button onclick="changePage(${totalPages})"
								class="flex-c-m how-pagination1 trans-04 m-all-7">Last</button>
						</c:if>
					</div>
				</div>
			</div>
		</section>

		<script>
		function changePage(pageNumber) {
			const urlParams = new URLSearchParams(window.location.search);
			urlParams.set('page', pageNumber);  // Cập nhật tham số 'page' trong URL
			window.location.search = urlParams.toString();  // Reload trang với số trang mới
		}
	</script>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script>

	function incrementQuantity() {
		const countInput = document.getElementById("count");
		let value = parseInt(countInput.value, 10);
		if (!isNaN(value)) {
			countInput.value = value + 1;
		}
	}

	function decrementQuantity() {
		const countInput = document.getElementById("count");
		let value = parseInt(countInput.value, 10);
		if (!isNaN(value) && value > 1) {
			countInput.value = value - 1;
		}
	}
	document.getElementById("productTypeSelect").addEventListener("change", function () {
		document.getElementById("selectedTypeId").value = this.value;
	});

	function changePage(pageNumber) {
		const urlParams = new URLSearchParams(window.location.search);
		urlParams.set('page', pageNumber);
		window.location.search = urlParams.toString();
	}
</script>
</body>
</html>
