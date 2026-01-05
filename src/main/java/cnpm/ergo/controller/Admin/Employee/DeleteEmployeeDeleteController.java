package cnpm.ergo.controller.Admin.Employee;

import cnpm.ergo.service.interfaces.IEmployeeService;
import cnpm.ergo.service.implement.EmployeeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeleteEmployeeController", value = "/admin/employee/delete")
public class DeleteEmployeeDeleteController extends HttpServlet {
    private IEmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        employeeService = new EmployeeServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            // Retrieve the employeeId from the request
            int userId = Integer.parseInt(request.getParameter("userId"));

            // Call the service to delete the employee
            employeeService.delete(userId);

            // Redirect to the employee management page upon success
            response.sendRedirect(request.getContextPath() + "/admin/employee");

        } catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to delete the employee. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
