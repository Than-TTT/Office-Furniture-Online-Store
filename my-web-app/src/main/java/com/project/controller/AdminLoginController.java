package com.project.controller;

import com.project.entity.Administrator;
import com.project.service.IAdministratorService;
import com.project.service.impl.AdministratorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Đường dẫn truy cập sẽ là http://localhost:8080/TenProject/login
@WebServlet(urlPatterns = {"/login"}) 
public class AdminLoginController extends HttpServlet {

    private IAdministratorService adminService = new AdministratorServiceImpl();

    // Dùng GET để hiển thị trang login
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    // Dùng POST để xử lý form đăng nhập
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        Administrator admin = adminService.login(u, p);

        if (admin != null) {
            // Đăng nhập thành công -> Lưu vào Session
            HttpSession session = req.getSession();
            session.setAttribute("adminAcc", admin);
            
            // Chuyển hướng sang trang quản lý nhân viên (sẽ làm sau)
            // Tạm thời chuyển về trang báo thành công
            req.setAttribute("message", "Xin chào Admin: " + admin.getUsername());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            // Thất bại
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
