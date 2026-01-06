<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("pageTitle", "Dashboard"); %>
<%@ include file="/WEB-INF/includes/admin_header.jspf" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <div class="row">
        <div class="col-12">
            <div class="card mb-4">
                <div class="card-header d-flex align-items-center justify-content-between">
                    <h5 class="mb-0">Welcome to Admin Dashboard</h5>
                </div>
                <div class="card-body">
                    <p class="text-muted">Use the sidebar to navigate between different management sections.</p>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-4 col-md-6 mb-4">
            <a href="employee" class="text-decoration-none">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <i class="bx bx-user text-primary" style="font-size: 3rem;"></i>
                        <h5 class="card-title mt-3">Employees</h5>
                        <p class="card-text text-muted">Manage employee accounts</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-6 mb-4">
            <a href="customer" class="text-decoration-none">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <i class="bx bx-group text-success" style="font-size: 3rem;"></i>
                        <h5 class="card-title mt-3">Customers</h5>
                        <p class="card-text text-muted">Manage customer accounts</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-6 mb-4">
            <a href="order" class="text-decoration-none">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <i class="bx bx-cart text-warning" style="font-size: 3rem;"></i>
                        <h5 class="card-title mt-3">Orders</h5>
                        <p class="card-text text-muted">View and manage orders</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-6 mb-4">
            <a href="product" class="text-decoration-none">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <i class="bx bx-box text-info" style="font-size: 3rem;"></i>
                        <h5 class="card-title mt-3">Products</h5>
                        <p class="card-text text-muted">Manage product catalog</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-6 mb-4">
            <a href="marketing" class="text-decoration-none">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <i class="bx bxs-megaphone text-danger" style="font-size: 3rem;"></i>
                        <h5 class="card-title mt-3">Marketing</h5>
                        <p class="card-text text-muted">Campaigns and vouchers</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-4 col-md-6 mb-4">
            <a href="statistic" class="text-decoration-none">
                <div class="card h-100">
                    <div class="card-body text-center">
                        <i class="bx bx-line-chart text-secondary" style="font-size: 3rem;"></i>
                        <h5 class="card-title mt-3">Statistics</h5>
                        <p class="card-text text-muted">View sales analytics</p>
                    </div>
                </div>
            </a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/admin_footer.jspf" %>