package cnpm.ergo.controller.Admin.Order;

import cnpm.ergo.entity.Order;
import cnpm.ergo.service.implement.OrderServiceImpl;
import cnpm.ergo.service.interfaces.IOrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/order/editstatus")
public class EditOrderController extends HttpServlet {
        IOrderService orderService = new OrderServiceImpl();
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                if (req.getSession().getAttribute("admin") == null) {
                        res.sendRedirect("/admin/login");
                        return;
                }
                try {
                        Integer orderId = Integer.parseInt(req.getParameter("orderId"));
                        String page = req.getParameter("currentPage");
                        String newStatus = req.getParameter("status");
                        Order order= orderService.findById(orderId);
                        order.setStatus(newStatus);
                        orderService.update(order);
                        res.sendRedirect(req.getContextPath()+"/admin/order?page=" + page);
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                        req.setAttribute("errorMessage", "Failed to edit the order information. Please try again.");
                        req.getRequestDispatcher("/errorPage.jsp").forward(req, res);

                }
        }
}
