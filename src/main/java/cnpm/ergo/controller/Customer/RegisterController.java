package cnpm.ergo.controller.Customer;

import cnpm.ergo.entity.Customer;
import cnpm.ergo.service.implement.CustomerServiceImpl;
import cnpm.ergo.service.interfaces.ICustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet(name = "RegisterController", value = "/customer/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ICustomerService customerService = new CustomerServiceImpl();
    
    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0[3|5|7|8|9])+([0-9]{8})$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị form đăng ký
        request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Lấy dữ liệu từ form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phonenumber");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            // 4.1 Kiểm tra các trường bắt buộc
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập trường này");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập trường này");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            if (phone == null || phone.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập trường này");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            if (password == null || password.isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập trường này");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            if (confirmPassword == null || confirmPassword.isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập trường này");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            
            // 4.2 Kiểm tra email hợp lệ
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                request.setAttribute("error", "Email không hợp lệ");
                request.setAttribute("emailError", "Email không hợp lệ");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            
            // 4.3 Kiểm tra email đã tồn tại
            if (customerService.getCustomerByEmail(email) != null) {
                request.setAttribute("error", "Email đã được sử dụng");
                request.setAttribute("emailError", "Email đã được sử dụng");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            
            // 4.4 Kiểm tra SĐT hợp lệ
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                request.setAttribute("error", "Số điện thoại không hợp lệ");
                request.setAttribute("phoneError", "Số điện thoại không hợp lệ");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            
            // 4.5 Kiểm tra SĐT đã tồn tại
            if (customerService.getCustomerByPhone(phone) != null) {
                request.setAttribute("error", "Số điện thoại đã được sử dụng");
                request.setAttribute("phoneError", "Số điện thoại đã được sử dụng");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            
            // 4.6 Kiểm tra mật khẩu >= 6 ký tự
            if (password.length() < 6) {
                request.setAttribute("error", "Mật khẩu tối thiểu 6 ký tự");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }
            
            // 4.7 Kiểm tra xác nhận mật khẩu
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu nhập lại không khớp");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }

            // Tạo đối tượng Customer
            Customer customer = new Customer();
            customer.setName(name.trim());
            customer.setEmail(email.trim());
            customer.setPhone(phone.trim());
            customer.setPassword(password);

            // Đăng ký khách hàng
            if (!customerService.insert(customer)) {
                request.setAttribute("error", "Đã xảy ra lỗi khi tạo tài khoản. Vui lòng thử lại!");
                request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
                return;
            }

            // Đăng ký thành công, chuyển sang trang đăng nhập
            request.getSession().setAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            response.sendRedirect(request.getContextPath() + "/customer/login");
            
        } catch (Exception e) {
            // Xử lý lỗi và quay lại trang đăng ký
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại!");
            request.getRequestDispatcher("/customer/views/register.jsp").forward(request, response);
        }
    }
}
