package cnpm.ergo.controller.Admin.Employee;

import cnpm.ergo.entity.Employee;
import cnpm.ergo.service.interfaces.IEmployeeService;
import cnpm.ergo.service.implement.EmployeeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateEmployeeController", value = "/admin/employee/update")
public class UpdateEmployeeController extends HttpServlet {
    private IEmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        employeeService = new EmployeeServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            // Retrieve form data from the request
            int userId = Integer.parseInt(request.getParameter("userId"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String status = request.getParameter("status");
            String password = request.getParameter("password");

            // Validate input fields (optional, add your validation logic here)

            // Retrieve the existing Employee object
            Employee employee = employeeService.findById(userId);
            if (employee != null) {
                // Update the Employee object with new values
                employee.setName(name);
                employee.setEmail(email);
                employee.setPhone(phone);
                employee.setAddress(address);
                employee.setGender(gender);
                employee.setStatus(status);
                employee.setPassword(password); // Update password

                // Call the service to update the employee
                employeeService.update(employee);
            }

            // Redirect to the employee management page upon success
            response.sendRedirect(request.getContextPath() + "/admin/employee");

        } catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to update the employee. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
