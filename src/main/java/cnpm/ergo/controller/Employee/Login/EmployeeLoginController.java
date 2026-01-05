package cnpm.ergo.controller.Employee.Login;

import cnpm.ergo.service.implement.EmployeeServiceImpl;
import cnpm.ergo.service.interfaces.IEmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "EmployeeLoginController", value = "/employee/login")
public class EmployeeLoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("employee") != null) {
            response.sendRedirect(request.getContextPath() + "/employee/blog");
        } else {
            request.getRequestDispatcher("/employee/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null) {
            request.setAttribute("error", "Email or password is incorrect.");
            request.getRequestDispatcher("/employee/views/login.jsp").forward(request, response);
            return;
        }

        IEmployeeService employeeService = new EmployeeServiceImpl();
        if (employeeService.login(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("employee", employeeService.getEmployee(email));
            response.sendRedirect(request.getContextPath() + "/employee/blog");
        } else {
            request.setAttribute("error", "Email or password is incorrect.");
            request.getRequestDispatcher("/employee/views/login.jsp").forward(request, response);
        }
    }
}
