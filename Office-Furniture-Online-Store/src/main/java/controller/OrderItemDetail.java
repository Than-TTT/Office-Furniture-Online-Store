package controller;

import dao.IOrderDao;
import dao.OrderDaoImpl;
import model.Order;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/customer/order-detail")
public class OrderItemDetail extends HttpServlet {

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

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = orderDao.getOrderById(orderId);

            // Kiểm tra order có thuộc về customer này không
            if (order == null || order.getCustomerId() != customer.getUserId()) {
                response.sendRedirect(request.getContextPath() + "/customer/order-history");
                return;
            }

            request.setAttribute("order", order);
            request.getRequestDispatcher("/OrderDetail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/customer/order-history");
        }
    }
}