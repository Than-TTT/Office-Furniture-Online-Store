package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/customer/update")
public class UpdateProfileController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userDAO.getUserById(userId);

        if (user != null) {
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            user.setPhone(request.getParameter("phone"));
            user.setAddress(request.getParameter("address"));
            user.setGender(request.getParameter("gender"));

            userDAO.updateUser(user);

            HttpSession session = request.getSession();
            session.setAttribute("customer", user);

            response.sendRedirect(request.getContextPath() + "/customer/profile");
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
