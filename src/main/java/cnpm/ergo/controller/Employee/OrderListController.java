package cnpm.ergo.controller.Employee;

import java.io.IOException;
import java.util.List;

import cnpm.ergo.entity.Order;
import cnpm.ergo.service.implement.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/employee/order-list")
public class OrderListController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private OrderServiceImpl orderService = new OrderServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("employee") == null) {
			resp.sendRedirect(req.getContextPath() + "/employee/login");
			return;
		}
		List<Order> orders = orderService.findAll();
        req.setAttribute("orders", orders);
		req.getRequestDispatcher("/employee/views/danh-sach-don.jsp").forward(req, resp);
	}
}
