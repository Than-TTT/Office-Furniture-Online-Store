package cnpm.ergo.controller.Customer.Customer;

import cnpm.ergo.entity.Customer;
import cnpm.ergo.service.implement.CustomerServiceImpl;
import cnpm.ergo.service.interfaces.ICustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ChangePasswordController", value = "/customer/change-password")
public class ChangePasswordController extends HttpServlet {
    private ICustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Kiểm tra user đã đăng nhập
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/customer/login");
                return;
            }

            // Lấy dữ liệu từ form
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");

            // Kiểm tra mật khẩu cũ
            if (!customer.getPassword().equals(oldPassword)) {
                // Thông báo lỗi
                response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=Old password is incorrect.");
                return;
            }

            // Cập nhật mật khẩu
            customer.setPassword(newPassword);
            customerService.update(customer);

            // Cập nhật session
            request.getSession().setAttribute("customer", customer);

            // Thông báo thành công
            response.sendRedirect(request.getContextPath() + "/customer/info?status=success&message=Password changed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            // Thông báo lỗi
            response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=Failed to change password.");
        }
    }
}
