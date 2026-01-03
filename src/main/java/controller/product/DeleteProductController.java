package controller.product;

import dao.ProductDaoImpl;
import dao.IProductDao;
import service.ProductServiceImpl;
import service.IProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/product/deleteproduct")
public class DeleteProductController extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                int productId = Integer.parseInt(req.getParameter("productId"));
                IProductService productService = new ProductServiceImpl();

                // Xóa sản phẩm từ database
                productService.deleteProduct(productId);

                res.sendRedirect(req.getContextPath() + "/admin/product");
        }
}
