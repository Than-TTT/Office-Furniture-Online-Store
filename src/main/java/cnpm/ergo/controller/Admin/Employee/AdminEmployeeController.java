package cnpm.ergo.controller.Admin.Employee;

import cnpm.ergo.entity.Employee;
import cnpm.ergo.service.implement.EmployeeServiceImpl;
import cnpm.ergo.service.interfaces.IEmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminEmployeeController", value = "/admin/employee")
public class AdminEmployeeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if admin is logged in
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        int pageNo = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            pageNo = Integer.parseInt(request.getParameter("page"));
        }
        IEmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = employeeService.findAll(pageNo, pageSize);
        long totalEmployees = employeeService.count();
        int totalPages = (int) Math.ceil((double) totalEmployees / pageSize);

        request.setAttribute("employeeList", employeeList);
        request.setAttribute("currentPage", pageNo);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/admin/views/employee.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests if necessary
    }
}

