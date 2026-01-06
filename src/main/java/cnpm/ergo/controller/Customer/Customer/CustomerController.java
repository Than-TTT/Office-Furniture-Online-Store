package cnpm.ergo.controller.Customer.Customer;

import cnpm.ergo.entity.User;
import cnpm.ergo.service.implement.CustomerServiceImpl;
import cnpm.ergo.service.interfaces.IUserService;
import cnpm.ergo.service.implement.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CustomerController1", value = "/customer/info")
public class CustomerController extends HttpServlet {
    
    private CustomerServiceImpl customerService = new CustomerServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Lấy user từ session nếu đã đăng nhập
        User user = (User) session.getAttribute("user");
        
        // Nếu chưa có user trong session, lấy từ database (test mode)
        if (user == null) {
            int id_user = 1; // ID mặc định cho test
            user = customerService.getUserById(id_user);
            
            if (user != null) {
                session.setAttribute("user", user);
            }
        }

        System.out.println("User Info: " + user);
        
        if (user == null) {
            request.setAttribute("error", "User not found");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        request.getRequestDispatcher("/customer/views/info_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý cập nhật thông tin user
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if ("update".equals(action)) {
            updateUserInfo(request, response);
        } else if ("changePassword".equals(action)) {
            changePassword(request, response);
        }
    }
    
    private void updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            // Lấy thông tin từ form
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            
            // Cập nhật thông tin
            currentUser.setName(name);
            currentUser.setEmail(email);
            currentUser.setPhone(phone);
            currentUser.setAddress(address);
            currentUser.setGender(gender);
            
            // Lưu vào database
            IUserService userService = new UserServiceImpl();
            boolean updated = userService.updateUser(currentUser);
            
            if (updated) {
                // session.setAttribute("user", currentUser);
                User refreshedUser = userService.getUserById(currentUser.getUserId());
                if (refreshedUser != null) {
                    session.setAttribute("user", refreshedUser);
                }
                response.sendRedirect(request.getContextPath() + "/customer/info?status=success&message=Profile updated successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=Failed to update profile");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=An error occurred");
        }
    }
    
    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            
            // Kiểm tra mật khẩu cũ
            if (!currentUser.getPassword().equals(oldPassword)) {
                response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=Old password is incorrect");
                return;
            }
            
            // Kiểm tra mật khẩu mới khớp
            if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=New passwords do not match");
                return;
            }
            
            // Cập nhật mật khẩu
            IUserService userService = new UserServiceImpl();
            boolean updated = userService.updateCustomerPassword(currentUser.getEmail(), newPassword);
            
            if (updated) {
                currentUser.setPassword(newPassword);
                session.setAttribute("user", currentUser);
                response.sendRedirect(request.getContextPath() + "/customer/info?status=success&message=Password changed successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=Failed to change password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/customer/info?status=error&message=An error occurred");
        }
    }
}
