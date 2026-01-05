package cnpm.ergo.controller.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/home")
public class HomeController extends HttpServlet {
        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                req.getRequestDispatcher("/admin/views/index.jsp").forward(req, res);
        }
}
