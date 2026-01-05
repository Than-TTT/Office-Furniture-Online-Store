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

@WebServlet(name = "AddEmployeeController", value = "/admin/employee/add")
public class AddEmployeeController extends HttpServlet {
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
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String password = request.getParameter("password");

            // Validate input fields (optional, add your validation logic here)

            // Create and populate the Employee object
            Employee employee = new Employee();
            employee.setName(name);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setAddress(address);
            employee.setGender(gender);
            employee.setPassword(password);
            employee.setStatus("Active"); // Default status
            employee.setIsDelete(false); // Mark as not deleted
            // Call the service to save the employee
            employeeService.insert(employee);

            // Redirect to the employee management page upon success
            response.sendRedirect(request.getContextPath() + "/admin/employee");

        } catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to add the employee. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
