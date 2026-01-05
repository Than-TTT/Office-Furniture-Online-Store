<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Layout</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="../assets/js/jquery-1.11.0.min.js"></script>
    <!-- Nhúng Bootstrap nếu cần -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style>
        body {
            background-color: #f4f4f4;
        }
        .card {
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .progress {
            height: 8px;
        }
        .social-links a {
            display: block;
            margin-top: 5px;
            text-decoration: none;
            color: #555;
        }
        .social-links a i {
            margin-right: 5px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row">
        <h1>Profile Customer</h1>
        <!-- Left Profile Card -->
        <div class="col-md-4">
            <div class="card text-center p-4">
                <img src="https://via.placeholder.com/120" alt="Avatar" class="rounded-circle mx-auto mb-3" width="120" height="120">
                <h3>${sessionScope.customer.name}</h3>
                <c:if test="${not empty sessionScope.customer}">
                    <button class="btn btn-dark btn-sm" data-bs-toggle="modal" data-bs-target="#editCustomerModal"
                            data-id="${sessionScope.customer.userId}"
                            data-name="${sessionScope.customer.name}"
                            data-email="${sessionScope.customer.email}"
                            data-phone="${sessionScope.customer.phone}"
                            data-address="${sessionScope.customer.address}"
                            data-gender="${sessionScope.customer.gender}"
                            data-status="${sessionScope.customer.status}"
                            data-password="${sessionScope.customer.password}">Edit profile</button>
                    <br/>
                    <a href="${pageContext.request.contextPath}/customer/order-history" class="btn btn-info btn-sm">
                        History Order
                    </a>

                </c:if>
            </div>
        </div>
        <!-- Middle Contact Card -->
        <div class="col-md-8">
            <div class="card p-4">
                <h5 class="card-title">Detail Information</h5>
                <c:if test="${not empty sessionScope.customer}">
                    <p><strong>Full Name:</strong>${sessionScope.customer.name}</p>
                    <p><strong>Email:</strong> ${sessionScope.customer.email}</p>
                    <p><strong>Phone:</strong> ${sessionScope.customer.phone}</p>
                    <p><strong>Password:</strong>*******
                        <button class="btn btn-dark btn-sm" data-bs-toggle="modal" data-bs-target="#changePasswordModal">Change password</button>
                    </p>
                    <p><strong>Gender:</strong>${sessionScope.customer.gender}</p>
                    <p><strong>Address:</strong>${sessionScope.customer.address}</p>
                </c:if>
            </div>
        </div>
        <div class="modal fade" id="editCustomerModal" tabindex="-1" aria-labelledby="editCustomerModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editCustomerModalLabel">Edit Customer</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/customer/update" method="post" id="editCustomerForm">
                            <input type="hidden" id="editUserId" name="userId">
                            <div class="mb-3">
                                <label for="editName" class="form-label">Name</label>
                                <input type="text" class="form-control" id="editName" name="name" required>
                            </div>
                            <div class="mb-3">
                                <label for="editEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="editEmail" name="email" required>
                            </div>
                            <div class="mb-3">
                                <label for="editPhone" class="form-label">Phone</label>
                                <input type="text" class="form-control" id="editPhone" name="phone" required>
                            </div>
                            <div class="mb-3">
                                <label for="editAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" id="editAddress" name="address" required>
                            </div>
                            <div class="mb-3">
                                <label for="editGender" class="form-label">Gender</label>
                                <select class="form-control" id="editGender" name="gender" required>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" form="editCustomerForm">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="changePasswordModalLabel">Change Password</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/customer/change-password" method="post" id="changePasswordForm">
                            <div class="mb-3">
                                <label for="oldPassword" class="form-label">Old Password</label>
                                <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
                            </div>
                            <div class="mb-3">
                                <label for="newPassword" class="form-label">New Password</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                            </div>
                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Confirm New Password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" form="changePasswordForm">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="toastMessage" class="toast align-items-center text-bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true" style="position: fixed; bottom: 20px; right: 20px; z-index: 1055; display: none;">
            <div class="d-flex">
                <div class="toast-body" id="toastBody">
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>

    </div>

</div>
<script type="text/javascript">
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        const status = urlParams.get('status'); // Lấy 'status'
        const message = urlParams.get('message'); // Lấy 'message'

        document.getElementById('editCustomerModal').addEventListener('show.bs.modal', function(event) {
            console.log("Modal triggered");
            var button = $(event.relatedTarget);
            console.log("Name: " + button.data('name'));
            console.log("Email: " + button.data('email'));
            console.log("Phone: " + button.data('phone'));
            console.log("Address: " + button.data('address'));
            console.log("Gender: " + button.data('gender'));

            $('#editName').val(button.data('name'));
            $('#editEmail').val(button.data('email'));
            $('#editPhone').val(button.data('phone'));
            $('#editAddress').val(button.data('address'));
            $('#editGender').val(button.data('gender'));
        });

        document.getElementById('changePasswordForm').addEventListener('submit', function (event) {
            event.preventDefault();

            // Lấy giá trị từ các trường
            const oldPassword = document.getElementById('oldPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (newPassword !== confirmPassword) {
                alert("New Password and Confirm Password do not match!");
                return;
            }
            this.submit();
        });
        if (status && message) {
            // Cập nhật nội dung của toast
            const toastBody = document.getElementById('toastBody');
            toastBody.textContent = message;

            // Thay đổi màu sắc toast dựa trên trạng thái
            const toastElement = document.getElementById('toastMessage');
            if (status === 'success') {
                toastElement.classList.add('text-bg-success');
                toastElement.classList.remove('text-bg-primary');
            } else if (status === 'error') {
                toastElement.classList.add('text-bg-danger');
                toastElement.classList.remove('text-bg-primary');
            }

            // Hiển thị toast
            const toast = new bootstrap.Toast(toastElement);
            toastElement.style.display = 'block';
            toast.show();
        }
    };
</script>


</body>
</html>
