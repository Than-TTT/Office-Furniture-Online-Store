<aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
    <div class="app-brand demo">
        <a href="employee" class="app-brand-link">
            <img src="${pageContext.request.contextPath}/CommonImage/Logo.jpg" alt="logo" class="img-fluid" style="width: 100px; height: auto;">
        </a>
    </div>

    <div class="menu-inner-shadow"></div>

    <ul class="menu-inner py-1">
        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Employees</span>
        </li>
        <li class="menu-item active">
            <a href="employee" class="menu-link">
                <i class="menu-icon tf-icons bx bx-user"></i>
                <div data-i18n="Analytics">Employees</div>
            </a>
        </li>

        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Customers</span>
        </li>
        <li class="menu-item active">
            <a href="customer" class="menu-link">
                <i class="menu-icon tf-icons bx bx-group"></i>
                <div data-i18n="Analytics">Customers</div>
            </a>
        </li>

        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Marketing Campaigns</span>
        </li>
        <li class="menu-item active">
            <a href="marketing" class="menu-link">
                <i class="menu-icon tf-icons bx bxs-megaphone"></i>
                <div data-i18n="Analytics">Marketing Campaigns</div>
            </a>
        </li>

        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Payment Account</span>
        </li>
        <li class="menu-item active">
            <a href="paymentaccount" class="menu-link">
                <i class="menu-icon tf-icons bx bx-wallet"></i>
                <div data-i18n="Analytics">Payment Account</div>
            </a>
        </li>


        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Orders</span>
        </li>
        <li class="menu-item active">
            <a href="order" class="menu-link">
                <i class="menu-icon tf-icons bx bx-cart"></i>
                <div data-i18n="Analytics">Orders</div>
            </a>
        </li>


        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Products</span>
        </li>
        <li class="menu-item active">
            <a href="product" class="menu-link">
                <i class="menu-icon tf-icons bx bx-box"></i>
                <div data-i18n="Analytics">Products</div>
            </a>
        </li>
        <li class="menu-item active">
            <a href="producttype" class="menu-link">
                <i class="menu-icon tf-icons bx bx-package"></i>
                <div data-i18n="Analytics">Product Types</div>
            </a>
        </li>

        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Blogs</span>
        </li>
        <li class="menu-item active">
            <a href="blog" class="menu-link">
                <i class="menu-icon tf-icons bx bx-pencil"></i>
                <div data-i18n="Analytics">Blogs</div>
            </a>
        </li>


        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Statistics</span>
        </li>
        <li class="menu-item active">
            <a href="statistic" class="menu-link">
                <i class="menu-icon tf-icons bx bx-line-chart"></i>
                <div data-i18n="Analytics">Statistics</div>
            </a>
        </li>
<%--        show admin name from sessionScope--%>
        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">${sessionScope.admin.name}</span>
        </li>
        <li class="menu-item active">
            <a href="admin" class="menu-link">
                <i class="menu-icon tf-icons bx bx-user"></i>
                <div data-i18n="Analytics">Admin</div>
            </a>
        </li>

<%--        logout--%>
        <li class="menu-header small text-uppercase">
            <span class="menu-header-text">Logout</span>
        </li>
        <li class="menu-item active">
            <a href="logout" class="menu-link">
                <i class="menu-icon tf-icons bx bx-log-out"></i>
                <div data-i18n="Analytics">Logout</div>
            </a>
        </li>
    </ul>
</aside>