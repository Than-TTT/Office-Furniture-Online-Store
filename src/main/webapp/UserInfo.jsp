<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Customer Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap 5 -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />

    <style>
      body {
        background-color: #f4f6f9;
      }
      .profile-avatar {
        width: 120px;
        height: 120px;
        object-fit: cover;
      }
    </style>
  </head>
  <body>
    <div class="container mt-5">
      <h2 class="mb-4">Customer Profile</h2>

      <div class="row">
        <!-- LEFT PROFILE -->
        <div class="col-md-4">
          <div class="card text-center p-4 shadow-sm">
            <img
              src="https://via.placeholder.com/120"
              class="rounded-circle mx-auto mb-3 profile-avatar"
            />

            <h4>${sessionScope.customer.name}</h4>

            <button
              class="btn btn-dark btn-sm mt-2"
              data-bs-toggle="modal"
              data-bs-target="#editCustomerModal"
              data-id="${sessionScope.customer.userId}"
              data-name="${sessionScope.customer.name}"
              data-email="${sessionScope.customer.email}"
              data-phone="${sessionScope.customer.phone}"
              data-address="${sessionScope.customer.address}"
              data-gender="${sessionScope.customer.gender}"
            >
              Edit Profile
            </button>

            <a
              href="${pageContext.request.contextPath}/customer/order-history"
              class="btn btn-outline-primary btn-sm mt-2"
            >
              Order History
            </a>
          </div>
        </div>

        <!-- RIGHT DETAIL -->
        <div class="col-md-8">
          <div class="card p-4 shadow-sm">
            <h5>Detail Information</h5>
            <hr />
            <p><strong>Name:</strong> ${sessionScope.customer.name}</p>
            <p><strong>Email:</strong> ${sessionScope.customer.email}</p>
            <p><strong>Phone:</strong> ${sessionScope.customer.phone}</p>
            <p>
              <strong>Password:</strong> ******
              <button
                class="btn btn-sm btn-secondary ms-2"
                data-bs-toggle="modal"
                data-bs-target="#changePasswordModal"
              >
                Change
              </button>
            </p>
            <p><strong>Gender:</strong> ${sessionScope.customer.gender}</p>
            <p><strong>Address:</strong> ${sessionScope.customer.address}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- ================= EDIT PROFILE MODAL ================= -->
    <div class="modal fade" id="editCustomerModal" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Edit Profile</h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <form
            action="${pageContext.request.contextPath}/customer/update"
            method="post"
          >
            <div class="modal-body">
              <input type="hidden" name="userId" id="editUserId" />

              <div class="mb-3">
                <label>Name</label>
                <input
                  class="form-control"
                  name="name"
                  id="editName"
                  required
                />
              </div>

              <div class="mb-3">
                <label>Email</label>
                <input
                  type="email"
                  class="form-control"
                  name="email"
                  id="editEmail"
                  required
                />
              </div>

              <div class="mb-3">
                <label>Phone</label>
                <input
                  class="form-control"
                  name="phone"
                  id="editPhone"
                  required
                />
              </div>

              <div class="mb-3">
                <label>Address</label>
                <input
                  class="form-control"
                  name="address"
                  id="editAddress"
                  required
                />
              </div>

              <div class="mb-3">
                <label>Gender</label>
                <select class="form-select" name="gender" id="editGender">
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
                  <option value="Other">Other</option>
                </select>
              </div>
            </div>

            <div class="modal-footer">
              <button class="btn btn-secondary" data-bs-dismiss="modal">
                Cancel
              </button>
              <button class="btn btn-primary">Save</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- ================= CHANGE PASSWORD MODAL ================= -->
    <div class="modal fade" id="changePasswordModal" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Change Password</h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <form
            action="${pageContext.request.contextPath}/customer/change-password"
            method="post"
          >
            <div class="modal-body">
              <input
                type="password"
                class="form-control mb-3"
                name="oldPassword"
                placeholder="Old password"
                required
              />
              <input
                type="password"
                class="form-control mb-3"
                name="newPassword"
                placeholder="New password"
                required
              />
              <input
                type="password"
                class="form-control"
                name="confirmPassword"
                placeholder="Confirm password"
                required
              />
            </div>

            <div class="modal-footer">
              <button class="btn btn-secondary" data-bs-dismiss="modal">
                Cancel
              </button>
              <button class="btn btn-primary">Update</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
      const editModal = document.getElementById("editCustomerModal");

      editModal.addEventListener("show.bs.modal", function (event) {
        const btn = event.relatedTarget;

        document.getElementById("editUserId").value = btn.dataset.id;
        document.getElementById("editName").value = btn.dataset.name;
        document.getElementById("editEmail").value = btn.dataset.email;
        document.getElementById("editPhone").value = btn.dataset.phone;
        document.getElementById("editAddress").value = btn.dataset.address;
        document.getElementById("editGender").value = btn.dataset.gender;
      });
    </script>
  </body>
</html>
