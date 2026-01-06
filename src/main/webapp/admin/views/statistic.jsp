<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% request.setAttribute("pageTitle", "Statistics"); %>
<%@ include file="/WEB-INF/includes/admin_header.jspf" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <h1 class="text-center mb-4">Business Statistics Dashboard</h1>
    
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    
    <!-- Summary Cards -->
    <div class="row mb-4">
        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card bg-primary text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="card-title text-white mb-1">Total Revenue</h6>
                            <h3 class="mb-0">
                                <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                            </h3>
                        </div>
                        <i class="bx bx-money bx-lg"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card bg-success text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="card-title text-white mb-1">Est. Profit</h6>
                            <h3 class="mb-0">
                                <fmt:formatNumber value="${totalProfit}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                            </h3>
                        </div>
                        <i class="bx bx-trending-up bx-lg"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card bg-info text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="card-title text-white mb-1">Total Orders</h6>
                            <h3 class="mb-0">${totalOrders}</h3>
                        </div>
                        <i class="bx bx-cart bx-lg"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-3 col-md-6 mb-3">
            <div class="card bg-warning text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="card-title text-white mb-1">Products</h6>
                            <h3 class="mb-0">${totalProducts}</h3>
                        </div>
                        <i class="bx bx-box bx-lg"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Order Status Cards -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Order Status Overview</h5>
                </div>
                <div class="card-body">
                    <div class="row text-center">
                        <div class="col-md-3 col-6 mb-3">
                            <div class="border rounded p-3">
                                <h4 class="text-success mb-1">${deliveredOrders}</h4>
                                <small class="text-muted">Delivered</small>
                            </div>
                        </div>
                        <div class="col-md-3 col-6 mb-3">
                            <div class="border rounded p-3">
                                <h4 class="text-info mb-1">${processingOrders}</h4>
                                <small class="text-muted">Processing</small>
                            </div>
                        </div>
                        <div class="col-md-3 col-6 mb-3">
                            <div class="border rounded p-3">
                                <h4 class="text-warning mb-1">${pendingOrders}</h4>
                                <small class="text-muted">Pending</small>
                            </div>
                        </div>
                        <div class="col-md-3 col-6 mb-3">
                            <div class="border rounded p-3">
                                <h4 class="text-danger mb-1">${cancelledOrders}</h4>
                                <small class="text-muted">Cancelled</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Charts Row -->
    <div class="row mb-4">
        <div class="col-lg-8 mb-3">
            <div class="card h-100">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="card-title mb-0">Monthly Revenue (Last 6 Months)</h5>
                </div>
                <div class="card-body">
                    <div id="revenueChart"></div>
                </div>
            </div>
        </div>
        <div class="col-lg-4 mb-3">
            <div class="card h-100">
                <div class="card-header">
                    <h5 class="card-title mb-0">Products by Category</h5>
                </div>
                <div class="card-body">
                    <div id="categoryChart"></div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Order Count Chart -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Monthly Order Count (Last 6 Months)</h5>
                </div>
                <div class="card-body">
                    <div id="orderCountChart"></div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Product Statistics Cards -->
    <div class="row mb-4">
        <div class="col-md-3 col-6 mb-3">
            <div class="card">
                <div class="card-body text-center">
                    <i class="bx bx-package bx-lg text-primary mb-2"></i>
                    <h4 class="mb-1">${totalProductTypes}</h4>
                    <small class="text-muted">Product Variants</small>
                </div>
            </div>
        </div>
        <div class="col-md-3 col-6 mb-3">
            <div class="card">
                <div class="card-body text-center">
                    <i class="bx bx-error bx-lg text-warning mb-2"></i>
                    <h4 class="mb-1">${lowStockCount}</h4>
                    <small class="text-muted">Low Stock (&lt;10)</small>
                </div>
            </div>
        </div>
        <div class="col-md-3 col-6 mb-3">
            <div class="card">
                <div class="card-body text-center">
                    <i class="bx bx-x-circle bx-lg text-danger mb-2"></i>
                    <h4 class="mb-1">${outOfStockCount}</h4>
                    <small class="text-muted">Out of Stock</small>
                </div>
            </div>
        </div>
        <div class="col-md-3 col-6 mb-3">
            <div class="card">
                <div class="card-body text-center">
                    <i class="bx bx-check-circle bx-lg text-success mb-2"></i>
                    <h4 class="mb-1">${totalProductTypes - lowStockCount - outOfStockCount}</h4>
                    <small class="text-muted">In Stock</small>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Product Stock Table -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="card-title mb-0">Product Inventory Status</h5>
                    <div>
                        <button class="btn btn-sm btn-outline-primary" onclick="filterTable('all')">All</button>
                        <button class="btn btn-sm btn-outline-warning" onclick="filterTable('low')">Low Stock</button>
                        <button class="btn btn-sm btn-outline-danger" onclick="filterTable('out')">Out of Stock</button>
                    </div>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="productTable">
                            <thead class="table-dark">
                                <tr>
                                    <th>Type ID</th>
                                    <th>Product Name</th>
                                    <th>Color</th>
                                    <th>Material</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="stat" items="${productStats}" varStatus="loop">
                                    <c:if test="${loop.index < 50}">
                                        <tr class="product-row" data-quantity="${stat.quantity}">
                                            <td>${stat.typeId}</td>
                                            <td>${stat.productName}</td>
                                            <td>${stat.color}</td>
                                            <td>${stat.material}</td>
                                            <td><fmt:formatNumber value="${stat.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                                            <td>${stat.quantity}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${stat.quantity <= 0}">
                                                        <span class="badge bg-danger">Out of Stock</span>
                                                    </c:when>
                                                    <c:when test="${stat.quantity < 10}">
                                                        <span class="badge bg-warning">Low Stock</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-success">In Stock</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Revenue Chart
    var revenueOptions = {
        series: [{
            name: 'Revenue',
            data: ${revenueDataJson}
        }],
        chart: {
            type: 'area',
            height: 350,
            toolbar: { show: false }
        },
        dataLabels: { enabled: false },
        stroke: { curve: 'smooth', width: 2 },
        xaxis: {
            categories: ${revenueLabelsJson}
        },
        yaxis: {
            labels: {
                formatter: function(val) {
                    return (val / 1000000).toFixed(1) + 'M';
                }
            }
        },
        colors: ['#696cff'],
        fill: {
            type: 'gradient',
            gradient: {
                shadeIntensity: 1,
                opacityFrom: 0.7,
                opacityTo: 0.2
            }
        },
        tooltip: {
            y: {
                formatter: function(val) {
                    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(val);
                }
            }
        }
    };
    var revenueChart = new ApexCharts(document.querySelector("#revenueChart"), revenueOptions);
    revenueChart.render();
    
    // Category Pie Chart
    var categoryOptions = {
        series: ${categoryDataJson},
        chart: {
            type: 'donut',
            height: 300
        },
        labels: ${categoryLabelsJson},
        colors: ['#696cff', '#71dd37', '#ffab00', '#03c3ec', '#ff3e1d', '#8592a3'],
        legend: {
            position: 'bottom'
        },
        responsive: [{
            breakpoint: 480,
            options: {
                chart: { width: 200 },
                legend: { position: 'bottom' }
            }
        }]
    };
    var categoryChart = new ApexCharts(document.querySelector("#categoryChart"), categoryOptions);
    categoryChart.render();
    
    // Order Count Bar Chart
    var orderCountOptions = {
        series: [{
            name: 'Orders',
            data: ${orderCountDataJson}
        }],
        chart: {
            type: 'bar',
            height: 250,
            toolbar: { show: false }
        },
        plotOptions: {
            bar: {
                borderRadius: 4,
                horizontal: false,
                columnWidth: '50%'
            }
        },
        dataLabels: { enabled: true },
        xaxis: {
            categories: ${revenueLabelsJson}
        },
        colors: ['#71dd37'],
        fill: { opacity: 1 }
    };
    var orderCountChart = new ApexCharts(document.querySelector("#orderCountChart"), orderCountOptions);
    orderCountChart.render();
});

function filterTable(type) {
    var rows = document.querySelectorAll('.product-row');
    rows.forEach(function(row) {
        var qty = parseInt(row.getAttribute('data-quantity'));
        if (type === 'all') {
            row.style.display = '';
        } else if (type === 'low' && qty > 0 && qty < 10) {
            row.style.display = '';
        } else if (type === 'out' && qty <= 0) {
            row.style.display = '';
        } else if (type === 'low' || type === 'out') {
            row.style.display = 'none';
        }
    });
}
</script>

<%@ include file="/WEB-INF/includes/admin_footer.jspf" %>