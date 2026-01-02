package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Kiểm tra nếu đã đăng nhập
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("customer") != null) {
            response.sendRedirect(request.getContextPath() + "/customer/profile");
            return;
        }
        
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate input
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ email và mật khẩu");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra đăng nhập
        User user = userDAO.getUserByEmail(email.trim());
        
        if (user != null && user.getPassword().equals(password)) {
            // Kiểm tra role
            if (!"CUSTOMER".equals(user.getRoleId())) {
                request.setAttribute("error", "Tài khoản này không có quyền truy cập");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Kiểm tra status
            if (!"Active".equals(user.getStatus())) {
                request.setAttribute("error", "Tài khoản đã bị khóa");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("customer", user);
            session.setMaxInactiveInterval(30 * 60); // 30 phút
            
            response.sendRedirect(request.getContextPath() + "/customer/profile");
        } else {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng");
            request.setAttribute("email", email); // Giữ lại email đã nhập
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

@WebServlet("/logout")
class LogoutController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.sendRedirect(request.getContextPath() + "/login");
    }
}