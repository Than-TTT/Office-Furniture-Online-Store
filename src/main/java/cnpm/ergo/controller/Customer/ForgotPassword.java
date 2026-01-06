package cnpm.ergo.controller.Customer;

import cnpm.ergo.DAO.interfaces.ICustomerDAO;
import cnpm.ergo.service.implement.CustomerServiceImpl;
import cnpm.ergo.service.implement.OTPService;
import cnpm.ergo.service.implement.UserServiceImpl;
import cnpm.ergo.service.interfaces.ICustomerService;
import cnpm.ergo.service.interfaces.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {
        "/customer/forgot-password",
        "/customer/verify-otp",
        "/customer/reset-password",
        "/customer/resend-otp"
})
public class ForgotPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int OTP_EXPIRY_SECONDS = 90; // OTP hết hạn sau 90 giây

    private final OTPService otpService = new OTPService(); // OTP service
    private final IUserService userService = new UserServiceImpl(); // User service
    private final ICustomerService customerService = new CustomerServiceImpl(); // Customer service


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/customer/forgot-password":
                request.getRequestDispatcher("/customer/views/forgot-password.jsp").forward(request, response);
                break;
            case "/customer/verify-otp":
                request.getRequestDispatcher("/customer/views/verify-otp.jsp").forward(request, response);
                break;
            case "/customer/reset-password":
                request.getRequestDispatcher("/customer/views/reset-password.jsp").forward(request, response);
                break;
            case "/customer/resend-otp":
                handleResendOTP(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/customer/forgot-password":
                handleForgotPassword(request, response);
                break;
            case "/customer/verify-otp":
                handleVerifyOTP(request, response);
                break;
            case "/customer/reset-password":
                handleResetPassword(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void handleForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("email");

        // Kiểm tra email rỗng
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập email");
            request.getRequestDispatcher("/customer/views/forgot-password.jsp").forward(request, response);
            return;
        }

        // 4.1 Kiểm tra email tồn tại
        if (customerService.getCustomerByEmail(email) != null) {
            try {
                // Tạo và gửi OTP
                String otp = otpService.generateOTP();
                otpService.sendOTPEmail(email, otp);

                // Lưu OTP, email và thời gian tạo vào session
                HttpSession session = request.getSession();
                session.setAttribute("otp", otp);
                session.setAttribute("email", email);
                session.setAttribute("otpCreatedTime", System.currentTimeMillis());

                response.sendRedirect(request.getContextPath() + "/customer/verify-otp");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Không thể gửi mã OTP. Vui lòng thử lại.");
                request.getRequestDispatcher("/customer/views/forgot-password.jsp").forward(request, response);
            }
        } else {
            // Email không tồn tại
            request.setAttribute("error", "Email không tồn tại");
            request.getRequestDispatcher("/customer/views/forgot-password.jsp").forward(request, response);
        }
    }

    private void handleResendOTP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/customer/forgot-password");
            return;
        }
        
        try {
            // Tạo OTP mới
            String otp = otpService.generateOTP();
            otpService.sendOTPEmail(email, otp);
            
            // Cập nhật session
            session.setAttribute("otp", otp);
            session.setAttribute("otpCreatedTime", System.currentTimeMillis());
            
            request.setAttribute("success", "Mã xác nhận mới đã được gửi!");
            request.getRequestDispatcher("/customer/views/verify-otp.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể gửi lại mã. Vui lòng thử lại.");
            request.getRequestDispatcher("/customer/views/verify-otp.jsp").forward(request, response);
        }
    }

    private void handleVerifyOTP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enteredOTP = request.getParameter("otp");
        HttpSession session = request.getSession();
        String sessionOTP = (String) session.getAttribute("otp");
        Long otpCreatedTime = (Long) session.getAttribute("otpCreatedTime");
        
        // Kiểm tra OTP rỗng
        if (enteredOTP == null || enteredOTP.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập mã xác nhận");
            request.getRequestDispatcher("/customer/views/verify-otp.jsp").forward(request, response);
            return;
        }
        
        // 5.2 Kiểm tra OTP hết hạn
        if (otpCreatedTime != null) {
            long elapsedSeconds = (System.currentTimeMillis() - otpCreatedTime) / 1000;
            if (elapsedSeconds > OTP_EXPIRY_SECONDS) {
                request.setAttribute("error", "Mã đã hết hạn");
                request.setAttribute("showResend", true);
                request.getRequestDispatcher("/customer/views/verify-otp.jsp").forward(request, response);
                return;
            }
        }

        // 5.1 Kiểm tra OTP đúng không
        if (enteredOTP.equals(sessionOTP)) {
            session.setAttribute("otpVerified", true);
            response.sendRedirect(request.getContextPath() + "/customer/reset-password");
        } else {
            request.setAttribute("error", "Mã xác nhận không đúng");
            request.getRequestDispatcher("/customer/views/verify-otp.jsp").forward(request, response);
        }
    }

    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");
        
        // Kiểm tra đã xác thực OTP chưa
        if (otpVerified == null || !otpVerified) {
            response.sendRedirect(request.getContextPath() + "/customer/forgot-password");
            return;
        }
        
        // Kiểm tra password rỗng
        if (newPassword == null || newPassword.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập mật khẩu mới");
            request.getRequestDispatcher("/customer/views/reset-password.jsp").forward(request, response);
            return;
        }
        
        // 7.2 Kiểm tra mật khẩu >= 6 ký tự
        if (newPassword.length() < 6) {
            request.setAttribute("error", "Mật khẩu tối thiểu 6 ký tự");
            request.getRequestDispatcher("/customer/views/reset-password.jsp").forward(request, response);
            return;
        }

        // 7.1 Kiểm tra xác nhận mật khẩu
        if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp");
            request.getRequestDispatcher("/customer/views/reset-password.jsp").forward(request, response);
            return;
        }

        try {
            boolean result = customerService.updateCustomerPassword(email, newPassword);

            if (result) {
                session.invalidate();
                request.getSession().setAttribute("successMessage", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập.");
                response.sendRedirect(request.getContextPath() + "/customer/login");
            } else {
                request.setAttribute("error", "Không thể đặt lại mật khẩu. Vui lòng thử lại.");
                request.getRequestDispatcher("/customer/views/reset-password.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại.");
            request.getRequestDispatcher("/customer/views/reset-password.jsp").forward(request, response);
        }
    }
}
