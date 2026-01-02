package controller;

import dao.IOrderDao;
import dao.OrderDaoImpl;
import model.Order;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/order-history")
public class OrderHistoryController extends HttpServlet {

    private IOrderDao orderDao = new OrderDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy filter status từ parameter
        String statusFilter = request.getParameter("status");
        
        List<Order> orders;
        if (statusFilter != null && !statusFilter.isEmpty()) {
            orders = orderDao.getOrdersByCustomerIdAndStatus(customer.getUserId(), statusFilter);
        } else {
            orders = orderDao.getOrdersByCustomerId(customer.getUserId());
        }

        request.setAttribute("orders", orders);
        request.setAttribute("statusFilter", statusFilter);
        request.getRequestDispatcher("/History_order.jsp").forward(request, response);
    }
}