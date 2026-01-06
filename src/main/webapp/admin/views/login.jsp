<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login | Furniture Admin</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/assets/vendor/fonts/boxicons.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/assets/vendor/css/core.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/assets/vendor/css/theme-default.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/assets/css/demo.css" />
    <style>
        .authentication-wrapper {
            display: flex;
            flex-basis: 100%;
            min-height: 100vh;
            width: 100%;
        }
        .authentication-inner {
            width: 100%;
            max-width: 400px;
        }
    </style>
</head>
<body>
<div class="container-xxl">
    <div class="authentication-wrapper authentication-basic container-p-y">
        <div class="authentication-inner py-4">
            <div class="card">
                <div class="card-body">
                    <div class="app-brand justify-content-center mb-4">
                        <a href="${pageContext.request.contextPath}/admin/home" class="app-brand-link gap-2">
                            <span class="app-brand-logo demo">
                                <i class="bx bx-chair" style="font-size: 2.5rem; color: #696cff;"></i>
                            </span>
                            <span class="app-brand-text demo text-body fw-bolder" style="font-size: 1.5rem;">Furniture Admin</span>
                        </a>
                    </div>

                    <h4 class="mb-2 text-center">Welcome! 👋</h4>
                    <p class="mb-4 text-center text-muted">Please sign in to your admin account</p>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible mb-3" role="alert">
                            ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/login" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" autofocus required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="password">Password</label>
                            <div class="input-group input-group-merge">
                                <input type="password" id="password" class="form-control" name="password" placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;" required />
                                <span class="input-group-text cursor-pointer" onclick="togglePassword()"><i class="bx bx-hide" id="toggleIcon"></i></span>
                            </div>
                        </div>
                        <div class="mb-3">
                            <button class="btn btn-primary d-grid w-100" type="submit">Sign in</button>
                        </div>
                    </form>

                    <p class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/" class="d-flex align-items-center justify-content-center">
                            <i class="bx bx-chevron-left"></i> Back to Store
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/admin/assets/vendor/js/bootstrap.js"></script>
<script>
    function togglePassword() {
        var passwordInput = document.getElementById('password');
        var toggleIcon = document.getElementById('toggleIcon');
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleIcon.classList.remove('bx-hide');
            toggleIcon.classList.add('bx-show');
        } else {
            passwordInput.type = 'password';
            toggleIcon.classList.remove('bx-show');
            toggleIcon.classList.add('bx-hide');
        }
    }
</script>
</body>
</html>
