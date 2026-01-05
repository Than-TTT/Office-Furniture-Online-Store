package cnpm.ergo.controller.Admin.Login;

import cnpm.ergo.service.implement.AdministratorServiceImpl;
import cnpm.ergo.service.interfaces.IAdministratorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import java.io.IOException;

@WebServlet(name = "AdminLoginController", value = "/admin/login")
public class AdminLoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("admin") != null) {
            response.sendRedirect(request.getContextPath() + "/admin/employee");
        } else {
            request.getRequestDispatcher("/admin/views/login.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null) {
            request.setAttribute("error", "Email or password is incorrect.");
            request.getRequestDispatcher("/admin/views/login.jsp").forward(request, response);
            return;
        }

        IAdministratorService administratorService = new AdministratorServiceImpl();
        if (administratorService.login(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", administratorService.getAdministrator(email));
            response.sendRedirect(request.getContextPath() + "/admin/employee");
        } else {
            request.setAttribute("error", "Email or password is incorrect.");
            request.getRequestDispatcher("/admin/views/login.jsp").forward(request, response);
        }
    }
}