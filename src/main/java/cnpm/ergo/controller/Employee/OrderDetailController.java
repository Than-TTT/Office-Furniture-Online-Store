package cnpm.ergo.controller.Employee;

import cnpm.ergo.entity.Order;
import cnpm.ergo.entity.OrderItem;
import cnpm.ergo.service.implement.OrderItemServiceImpl;
import cnpm.ergo.service.implement.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/employee/order-detail")
public class OrderDetailController extends HttpServlet {
    private OrderServiceImpl orderService = new OrderServiceImpl();
    private OrderItemServiceImpl orderItemService = new OrderItemServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") == null) {
            resp.sendRedirect(req.getContextPath() + "/employee/login");
            return;
        }
        int id = Integer.parseInt(req.getParameter("orderId"));
        List<OrderItem> orderItems = orderItemService.findAll(id);
        Order order = orderService.findById(id);
        req.setAttribute("order", order);
        double totalAmount = 0;
        for (OrderItem item : orderItems) {
            totalAmount += item.getQuantity() * item.getPrice();
        }
        req.setAttribute("orderItems", orderItems);
        req.setAttribute("totalAmount", totalAmount);
        req.setAttribute("orderId", id);
        req.getRequestDispatcher("/employee/views/chitietdon.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") == null) {
            resp.sendRedirect(req.getContextPath() + "/employee/login");
            return;
        }
        String orderIdStr = req.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdStr);
        Order order = orderService.findById(orderId);
        order.setStatus("Đã xác nhận");
        orderService.update(order);
        doGet(req, resp);
    }
}
