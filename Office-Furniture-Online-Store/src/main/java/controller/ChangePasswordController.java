package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/customer/change-password")
public class ChangePasswordController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath()
                    + "/customer/profile?error=password_mismatch");
            return;
        }

        boolean success = userDAO.updatePassword(
                customer.getUserId(), oldPassword, newPassword);

        if (success) {
            response.sendRedirect(request.getContextPath()
                    + "/customer/profile?success=password_changed");
        } else {
            response.sendRedirect(request.getContextPath()
                    + "/customer/profile?error=wrong_password");
        }
    }
}
