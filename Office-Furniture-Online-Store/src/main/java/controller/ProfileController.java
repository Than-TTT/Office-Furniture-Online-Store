package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/customer/profile")
public class ProfileController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");

        if (customer == null) {
            // Nếu chưa đăng nhập, chuyển về trang login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin mới nhất từ database
        User updatedCustomer = userDAO.getUserById(customer.getUserId());
        session.setAttribute("customer", updatedCustomer);

        request.getRequestDispatcher("/UserInfo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý cập nhật thông tin
        doGet(request, response);
    }
}

// @WebServlet("/customer/update")
// public class UpdateProfileController extends HttpServlet {
    
//     private UserDAO userDAO = new UserDAO();

//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
        
//         request.setCharacterEncoding("UTF-8");
        
//         try {
//             int userId = Integer.parseInt(request.getParameter("userId"));
            
//             User user = userDAO.getUserById(userId);
//             if (user != null) {
//                 user.setName(request.getParameter("name"));
//                 user.setEmail(request.getParameter("email"));
//                 user.setPhone(request.getParameter("phone"));
//                 user.setAddress(request.getParameter("address"));
//                 user.setGender(request.getParameter("gender"));
                
//                 userDAO.updateUser(user);
                
//                 // Cập nhật session
//                 HttpSession session = request.getSession();
//                 session.setAttribute("customer", user);
                
//                 response.sendRedirect(request.getContextPath() + "/customer/profile");
//             } else {
//                 response.sendRedirect(request.getContextPath() + "/login");
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             response.sendRedirect(request.getContextPath() + "/customer/profile?error=update");
//         }
//     }
// }

// @WebServlet("/customer/change-password")
// public class ChangePasswordController extends HttpServlet {
    
//     private UserDAO userDAO = new UserDAO();

//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
        
//         HttpSession session = request.getSession();
//         User customer = (User) session.getAttribute("customer");
        
//         if (customer == null) {
//             response.sendRedirect(request.getContextPath() + "/login");
//             return;
//         }
        
//         String oldPassword = request.getParameter("oldPassword");
//         String newPassword = request.getParameter("newPassword");
//         String confirmPassword = request.getParameter("confirmPassword");
        
//         if (!newPassword.equals(confirmPassword)) {
//             response.sendRedirect(request.getContextPath() + 
//                 "/customer/profile?error=password_mismatch");
//             return;
//         }
        
//         boolean success = userDAO.updatePassword(
//             customer.getUserId(), oldPassword, newPassword);
        
//         if (success) {
//             response.sendRedirect(request.getContextPath() + 
//                 "/customer/profile?success=password_changed");
//         } else {
//             response.sendRedirect(request.getContextPath() + 
//                 "/customer/profile?error=wrong_password");
//         }
//     }
// }