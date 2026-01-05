package cnpm.ergo.controller.Admin.Product;


import cnpm.ergo.entity.ProductType;
import cnpm.ergo.service.implement.ProductTypeServiceImpl;
import cnpm.ergo.service.interfaces.IProductTypeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/producttype")
public class ProductTypeController extends HttpServlet {
        IProductTypeService productTypeService = new ProductTypeServiceImpl();
        private final Integer PRODUCTTYPE_PER_PAGE = 10;
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                if (req.getSession().getAttribute("admin") == null) {
                        res.sendRedirect("/admin/login");
                        return;
                }
                try {
                        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
                        List<ProductType> listProductType = productTypeService.getAllProductTypesByPage((page - 1) * PRODUCTTYPE_PER_PAGE, PRODUCTTYPE_PER_PAGE);
                        int currentPage = page;
                        int totalPages = (productTypeService.getProductTypeCount() + PRODUCTTYPE_PER_PAGE - 1) / PRODUCTTYPE_PER_PAGE;
                        req.setAttribute("currentPage", currentPage);
                        req.setAttribute("totalPages", totalPages);
                        req.setAttribute("listProductType", listProductType);
                        req.getRequestDispatcher("/admin/views/producttype.jsp").forward(req, res);
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                        // Forward the error details to an error page
                        req.setAttribute("errorMessage", "Failed to get the order types information. Please try again.");
                        req.getRequestDispatcher("/errorPage.jsp").forward(req, res);
                }
        }


}
