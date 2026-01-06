<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>User Profile - Ergo</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet" />
    
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px 0;
        }

        .profile-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .page-title {
            color: white;
            font-size: 2.5rem;
            font-weight: 700;
            text-align: center;
            margin-bottom: 40px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
        }

        /* Profile Card */
        .profile-card {
            background: white;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.3s ease;
        }

        .profile-card:hover {
            transform: translateY(-5px);
        }

        /* Avatar Section */
        .avatar-section {
            text-align: center;
            padding: 40px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            position: relative;
        }

        .avatar-wrapper {
            position: relative;
            display: inline-block;
            margin-bottom: 20px;
        }

        .avatar-img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            border: 5px solid white;
            object-fit: cover;
            box-shadow: 0 5px 20px rgba(0,0,0,0.2);
        }

        .avatar-badge {
            position: absolute;
            bottom: 10px;
            right: 10px;
            background: #4CAF50;
            width: 25px;
            height: 25px;
            border-radius: 50%;
            border: 3px solid white;
        }

        .user-name {
            font-size: 1.8rem;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .user-role {
            font-size: 0.9rem;
            opacity: 0.9;
            margin-bottom: 20px;
        }

        .profile-actions {
            display: flex;
            gap: 10px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn-profile {
            padding: 10px 25px;
            border-radius: 25px;
            font-weight: 500;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            text-decoration: none;
        }

        .btn-primary-custom {
            background: white;
            color: #667eea;
        }

        .btn-primary-custom:hover {
            background: #f8f9fa;
            transform: scale(1.05);
            color: #667eea;
        }

        .btn-secondary-custom {
            background: rgba(255,255,255,0.2);
            color: white;
            border: 2px solid white;
        }

        .btn-secondary-custom:hover {
            background: white;
            color: #667eea;
        }

        /* Info Section */
        .info-section {
            padding: 40px;
        }

        .section-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: #333;
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 3px solid #667eea;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 25px;
        }

        .info-item {
            display: flex;
            align-items: flex-start;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 15px;
            transition: all 0.3s ease;
        }

        .info-item:hover {
            background: #e9ecef;
            transform: translateX(5px);
        }

        .info-icon {
            width: 45px;
            height: 45px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.2rem;
            margin-right: 15px;
            flex-shrink: 0;
        }

        .info-content {
            flex: 1;
        }

        .info-label {
            font-size: 0.85rem;
            color: #6c757d;
            margin-bottom: 5px;
            font-weight: 500;
        }

        .info-value {
            font-size: 1rem;
            color: #333;
            font-weight: 500;
            word-break: break-word;
        }

        .password-field {
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
        }

        /* Modal Styling */
        .modal-content {
            border-radius: 20px;
            border: none;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }

        .modal-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 20px 20px 0 0;
            padding: 20px 30px;
            border: none;
        }

        .modal-title {
            font-weight: 600;
        }

        .btn-close {
            filter: brightness(0) invert(1);
        }

        .modal-body {
            padding: 30px;
        }

        .form-label {
            font-weight: 500;
            color: #333;
            margin-bottom: 8px;
        }

        .form-control {
            border-radius: 10px;
            border: 2px solid #e9ecef;
            padding: 12px 15px;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }

        .modal-footer {
            border: none;
            padding: 20px 30px;
        }

        /* Toast Notification */
        .toast {
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.2);
        }

        /* Responsive */
        @media (max-width: 768px) {
            .page-title {
                font-size: 2rem;
            }

            .info-grid {
                grid-template-columns: 1fr;
            }

            .avatar-img {
                width: 120px;
                height: 120px;
            }

            .info-section {
                padding: 20px;
            }
        }

        /* Loading Animation */
        .loading {
            pointer-events: none;
            opacity: 0.6;
        }

        .spinner-border-sm {
            width: 1rem;
            height: 1rem;
            border-width: 0.2em;
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <h1 class="page-title">
            <i class="fas fa-user-circle"></i> User Profile
        </h1>

        <c:if test="${not empty sessionScope.user}">
            <div class="row g-4">
                <!-- Left Column - Profile Card -->
                <div class="col-lg-4">
                    <div class="profile-card">
                        <div class="avatar-section">
                            <div class="avatar-wrapper">
                                <img src="https://ui-avatars.com/api/?name=${sessionScope.user.name}&size=150&background=667eea&color=fff&bold=true" 
                                     alt="Avatar" 
                                     class="avatar-img"
                                     onerror="this.src='data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%22150%22 height=%22150%22%3E%3Crect fill=%22%23667eea%22 width=%22150%22 height=%22150%22/%3E%3Ctext fill=%22%23fff%22 x=%2250%25%22 y=%2250%25%22 dy=%22.3em%22 text-anchor=%22middle%22 font-size=%2260%22 font-weight=%22bold%22%3E${sessionScope.user.name.substring(0,1)}%3C/text%3E%3C/svg%3E'" />
                                <div class="avatar-badge"></div>
                            </div>
                            <h2 class="user-name">${sessionScope.user.name}</h2>
                            <p class="user-role">
                                <i class="fas fa-shield-alt"></i> 
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user.role}">
                                        ${sessionScope.user.role.roleName}
                                    </c:when>
                                    <c:otherwise>
                                        User
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            
                            <div class="profile-actions">
                                <button class="btn btn-profile btn-primary-custom" 
                                        data-bs-toggle="modal" 
                                        data-bs-target="#editUserModal">
                                    <i class="fas fa-edit"></i> Edit Profile
                                </button>
                                <a href="${pageContext.request.contextPath}/customer/order-history" 
                                   class="btn btn-profile btn-secondary-custom">
                                    <i class="fas fa-history"></i> Order History
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Right Column - Info Details -->
                <div class="col-lg-8">
                    <div class="profile-card">
                        <div class="info-section">
                            <h3 class="section-title">
                                <i class="fas fa-info-circle"></i>
                                Personal Information
                            </h3>
                            
                            <div class="info-grid">
                                <div class="info-item">
                                    <div class="info-icon">
                                        <i class="fas fa-user"></i>
                                    </div>
                                    <div class="info-content">
                                        <div class="info-label">Full Name</div>
                                        <div class="info-value">${sessionScope.user.name}</div>
                                    </div>
                                </div>

                                <div class="info-item">
                                    <div class="info-icon">
                                        <i class="fas fa-envelope"></i>
                                    </div>
                                    <div class="info-content">
                                        <div class="info-label">Email Address</div>
                                        <div class="info-value">${sessionScope.user.email}</div>
                                    </div>
                                </div>

                                <div class="info-item">
                                    <div class="info-icon">
                                        <i class="fas fa-phone"></i>
                                    </div>
                                    <div class="info-content">
                                        <div class="info-label">Phone Number</div>
                                        <div class="info-value">
                                            <c:choose>
                                                <c:when test="${not empty sessionScope.user.phone}">
                                                    ${sessionScope.user.phone}
                                                </c:when>
                                                <c:otherwise>
                                                    Not provided
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <div class="info-item">
                                    <div class="info-icon">
                                        <i class="fas fa-venus-mars"></i>
                                    </div>
                                    <div class="info-content">
                                        <div class="info-label">Gender</div>
                                        <div class="info-value">
                                            <c:choose>
                                                <c:when test="${not empty sessionScope.user.gender}">
                                                    ${sessionScope.user.gender}
                                                </c:when>
                                                <c:otherwise>
                                                    Not specified
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <div class="info-item">
                                    <div class="info-icon">
                                        <i class="fas fa-map-marker-alt"></i>
                                    </div>
                                    <div class="info-content">
                                        <div class="info-label">Address</div>
                                        <div class="info-value">
                                            <c:choose>
                                                <c:when test="${not empty sessionScope.user.address}">
                                                    ${sessionScope.user.address}
                                                </c:when>
                                                <c:otherwise>
                                                    Not provided
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                                <div class="info-item">
                                    <div class="info-icon">
                                        <i class="fas fa-lock"></i>
                                    </div>
                                    <div class="info-content">
                                        <div class="info-label">Password</div>
                                        <div class="password-field">
                                            <div class="info-value">********</div>
                                            <button class="btn btn-sm btn-outline-primary" 
                                                    data-bs-toggle="modal" 
                                                    data-bs-target="#changePasswordModal">
                                                <i class="fas fa-key"></i> Change
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${empty sessionScope.user}">
            <div class="alert alert-warning text-center" role="alert">
                <i class="fas fa-exclamation-triangle"></i> 
                No user information available. Please login first.
            </div>
        </c:if>
    </div>

    <!-- Edit User Modal -->
    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editUserModalLabel">
                        <i class="fas fa-user-edit"></i> Edit Profile
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="${pageContext.request.contextPath}/customer/update" method="post" id="editUserForm">
                    <input type="hidden" name="action" value="update" />
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="editName" class="form-label">
                                <i class="fas fa-user"></i> Full Name
                            </label>
                            <input type="text" class="form-control" id="editName" name="name" 
                                   value="${sessionScope.user.name}" required />
                        </div>
                        
                        <div class="mb-3">
                            <label for="editEmail" class="form-label">
                                <i class="fas fa-envelope"></i> Email Address
                            </label>
                            <input type="email" class="form-control" id="editEmail" name="email" 
                                   value="${sessionScope.user.email}" required />
                        </div>
                        
                        <div class="mb-3">
                            <label for="editPhone" class="form-label">
                                <i class="fas fa-phone"></i> Phone Number
                            </label>
                            <input type="tel" class="form-control" id="editPhone" name="phone" 
                                   value="${sessionScope.user.phone}" />
                        </div>
                        
                        <div class="mb-3">
                            <label for="editAddress" class="form-label">
                                <i class="fas fa-map-marker-alt"></i> Address
                            </label>
                            <textarea class="form-control" id="editAddress" name="address" rows="2">${sessionScope.user.address}</textarea>
                        </div>
                        
                        <div class="mb-3">
                            <label for="editGender" class="form-label">
                                <i class="fas fa-venus-mars"></i> Gender
                            </label>
                            <select class="form-control" id="editGender" name="gender">
                                <option value="Male" ${sessionScope.user.gender == 'Male' ? 'selected' : ''}>Male</option>
                                <option value="Female" ${sessionScope.user.gender == 'Female' ? 'selected' : ''}>Female</option>
                                <option value="Other" ${sessionScope.user.gender == 'Other' ? 'selected' : ''}>Other</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times"></i> Cancel
                        </button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Save Changes
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Change Password Modal -->
    <div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="changePasswordModalLabel">
                        <i class="fas fa-key"></i> Change Password
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="${pageContext.request.contextPath}/customer/change-password" method="post" id="changePasswordForm">
                    <input type="hidden" name="action" value="changePassword" />
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="oldPassword" class="form-label">
                                <i class="fas fa-lock"></i> Current Password
                            </label>
                            <input type="password" class="form-control" id="oldPassword" name="oldPassword" required />
                        </div>
                        
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">
                                <i class="fas fa-lock"></i> New Password
                            </label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required />
                        </div>
                        
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">
                                <i class="fas fa-lock"></i> Confirm New Password
                            </label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required />
                        </div>
                        
                        <div class="alert alert-info" role="alert">
                            <i class="fas fa-info-circle"></i> Password must be at least 6 characters long.
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times"></i> Cancel
                        </button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-check"></i> Change Password
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Toast Notification -->
    <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
        <div id="toastMessage" class="toast hide" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
                <i class="fas fa-check-circle text-success me-2" id="toastIcon"></i>
                <strong class="me-auto" id="toastTitle">Notification</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body" id="toastBody">
                Success message here
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Toast notification handler
        window.addEventListener('DOMContentLoaded', function() {
            const urlParams = new URLSearchParams(window.location.search);
            const status = urlParams.get('status');
            const message = urlParams.get('message');
            
            if (status && message) {
                showToast(status, message);
                
                // Remove parameters from URL
                const url = new URL(window.location);
                url.searchParams.delete('status');
                url.searchParams.delete('message');
                window.history.replaceState({}, document.title, url);
            }
        });

        function showToast(status, message) {
            const toast = document.getElementById('toastMessage');
            const toastTitle = document.getElementById('toastTitle');
            const toastBody = document.getElementById('toastBody');
            const toastIcon = document.getElementById('toastIcon');
            const toastHeader = toast.querySelector('.toast-header');
            
            toastBody.textContent = message;
            
            if (status === 'success') {
                toastTitle.textContent = 'Success';
                toastIcon.className = 'fas fa-check-circle text-success me-2';
                toastHeader.classList.remove('bg-danger');
                toastHeader.classList.add('bg-success', 'text-white');
            } else {
                toastTitle.textContent = 'Error';
                toastIcon.className = 'fas fa-exclamation-circle text-danger me-2';
                toastHeader.classList.remove('bg-success');
                toastHeader.classList.add('bg-danger', 'text-white');
            }
            
            const bsToast = new bootstrap.Toast(toast);
            bsToast.show();
        }

        // Form validation
        document.getElementById('changePasswordForm').addEventListener('submit', function(e) {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (newPassword !== confirmPassword) {
                e.preventDefault();
                showToast('error', 'New passwords do not match!');
                return false;
            }
            
            if (newPassword.length < 6) {
                e.preventDefault();
                showToast('error', 'Password must be at least 6 characters long!');
                return false;
            }
        });

        // Add loading state to forms
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function(e) {
                const submitBtn = this.querySelector('button[type="submit"]');
                if (submitBtn && !e.defaultPrevented) {
                    submitBtn.disabled = true;
                    const originalText = submitBtn.innerHTML;
                    submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Loading...';
                    
                    // Re-enable after 5 seconds in case of error
                    setTimeout(function() {
                        submitBtn.disabled = false;
                        submitBtn.innerHTML = originalText;
                    }, 5000);
                }
            });
        });
    </script>
</body>
</html>
