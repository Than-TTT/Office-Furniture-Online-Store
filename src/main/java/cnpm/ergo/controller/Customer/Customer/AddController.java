package cnpm.ergo.controller.Customer.Customer;

import cnpm.ergo.entity.Customer;
import cnpm.ergo.service.implement.CustomerServiceImpl;
import cnpm.ergo.service.interfaces.ICustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AddController", value = "/admin/customer/add")
public class AddController extends HttpServlet {
    private ICustomerService customerService;
    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
        customerService = new CustomerServiceImpl();
}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            // Create and populate the Customer object
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAddress(address);
            customer.setGender(gender);
            customer.setPassword(password);
            customer.setStatus("Active"); // Default status
            customer.setIsDelete(false); // Mark as not deleted

            customerService.insert(customer);
            response.sendRedirect(request.getContextPath() + "/admin/customer");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to add the customer. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}