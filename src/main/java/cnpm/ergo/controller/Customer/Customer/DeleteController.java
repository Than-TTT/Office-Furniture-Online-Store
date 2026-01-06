package cnpm.ergo.controller.Customer.Customer;

import cnpm.ergo.service.implement.CustomerServiceImpl;
import cnpm.ergo.service.interfaces.ICustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeleteController", value = "/DeleteController")
public class DeleteController extends HttpServlet {
    private ICustomerService customerService;

    @Override
    public void init() throws ServletException {
        // Initialize the service implementation
         customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        try {
            // Retrieve the userId from the request
            int userId = Integer.parseInt(request.getParameter("userId"));

            // Call the service to delete the customer
            customerService.delete(userId);

            // Redirect to the customer management page upon success
            response.sendRedirect(request.getContextPath() + "/admin/customer");

        } catch (Exception e) {
            e.printStackTrace();
            // Forward the error details to an error page
            request.setAttribute("errorMessage", "Failed to delete the customer. Please try again.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}