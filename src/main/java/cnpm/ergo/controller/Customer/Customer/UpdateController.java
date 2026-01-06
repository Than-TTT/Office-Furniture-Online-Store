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

@WebServlet(name = "UpdateController1", value = "/customer/update")
public class UpdateController extends HttpServlet {
    private ICustomerService customerService;

    public void init() throws ServletException {
        customerService = new CustomerServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Kiểm tra user đã đăng nhập chưa
            if (request.getSession().getAttribute("customer") == null) {
                response.sendRedirect(request.getContextPath() + "/customer/login");
                return;
            }

            // Lấy thông tin user từ session
            Customer currentCustomer = (Customer) request.getSession().getAttribute("customer");
//            int id_user = 94;
//
//            Customer customer = customerService.getCustomerById(id_user);
//            currentCustomer = customer;
            // Lấy dữ liệu từ form
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String password = request.getParameter("password");

            // Cập nhật thông tin user
            currentCustomer.setName(name);
            currentCustomer.setEmail(email);
            currentCustomer.setPhone(phone);
            currentCustomer.setAddress(address);
            currentCustomer.setGender(gender);

            if (password != null && !password.isEmpty()) {
                currentCustomer.setPassword(password); // Chỉ cập nhật password nếu được nhập
            }

            // Gọi service để lưu thông tin
            customerService.update(currentCustomer);

            // Cập nhật lại thông tin user trong session
            request.getSession().setAttribute("customer", currentCustomer);

            // Redirect về trang profile
            response.sendRedirect(request.getContextPath() + "/customer/info");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to update your profile. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}