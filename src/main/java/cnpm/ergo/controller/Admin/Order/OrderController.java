package cnpm.ergo.controller.Admin.Order;


import cnpm.ergo.entity.Order;
import cnpm.ergo.service.implement.OrderServiceImpl;
import cnpm.ergo.service.interfaces.IOrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/order")
public class OrderController extends HttpServlet {
        IOrderService orderService = new OrderServiceImpl();
        private static final int ORDERS_PER_PAGE = 10;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                if (req.getSession().getAttribute("admin") == null) {
                        res.sendRedirect(req.getContextPath() + "/admin/login");
                        return;
                }
                try {
                        int page = 1;
                        if (req.getParameter("page") != null) {
                                page = Integer.parseInt(req.getParameter("page"));
                        }

                        List<Order> listOrder = orderService.findByPage(ORDERS_PER_PAGE * (page - 1), ORDERS_PER_PAGE);
                        int totalProducts = orderService.count();
                        int totalPages = (int) Math.ceil((double) totalProducts / ORDERS_PER_PAGE);
                        System.out.println(totalPages);
                        System.out.println(page);

                        req.setAttribute("listOrder", listOrder);
                        req.setAttribute("currentPage", page);
                        req.setAttribute("totalPages", totalPages);

                        req.getRequestDispatcher("/admin/views/order.jsp").forward(req, res);

                }
                catch (Exception ex) {
                        ex.printStackTrace();
                        // Forward the error details to an error page
                        req.setAttribute("errorMessage", "Failed to get the orders information. Please try again.");
                        req.getRequestDispatcher("/errorPage.jsp").forward(req, res);
                }
        }


}
