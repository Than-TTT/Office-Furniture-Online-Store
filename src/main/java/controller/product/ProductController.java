package controller.product;

import model.Category;
import model.Product;
import model.ProductImage;
import service.CategoryServiceImpl;
import service.ICategoryService;
import service.ProductImageServiceImpl;
import service.ProductServiceImpl;
import service.IProductImageService;
import service.IProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/product")
public class ProductController extends HttpServlet {
        private static final int PRODUCTS_PER_PAGE = 10;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                IProductService productService = new ProductServiceImpl();
                IProductImageService productImageService = new ProductImageServiceImpl();

                int page = 1;
                if (req.getParameter("page") != null) {
                        page = Integer.parseInt(req.getParameter("page"));
                }

                List<Product> listProduct = productService.getProductsAvailableByPage((page - 1) * PRODUCTS_PER_PAGE, PRODUCTS_PER_PAGE);
                int totalProducts = productService.getProductAvailableCount();
                int totalPages = (int) Math.ceil((double) totalProducts / PRODUCTS_PER_PAGE);

                for (Product product : listProduct) {
                        List<ProductImage> images = productImageService.getProductImagesByProductId(product.getProductId());
                        product.setProductImages(images); // Giả sử bạn có phương thức này
                }

                req.setAttribute("listProduct", listProduct);
                req.setAttribute("currentPage", page);
                req.setAttribute("totalPages", totalPages);

                ICategoryService categoryService = new CategoryServiceImpl();
                List<Category> categories = categoryService.getAllCategories();
                req.setAttribute("categories", categories);

                req.getRequestDispatcher("/admin/views/product.jsp").forward(req, resp);
        }
}
