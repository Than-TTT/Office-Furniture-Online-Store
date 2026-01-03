package controller.product;

import model.ProductImage;
import service.ProductImageServiceImpl;
import service.ProductServiceImpl;
import service.IProductImageService;
import service.IProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(urlPatterns = "/admin/product/deleteimage")
public class DeleteImageController extends HttpServlet {
        IProductImageService productImageService = new ProductImageServiceImpl();
        IProductService productService = new ProductServiceImpl();

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

                String productImageStr = req.getParameter("productImageName");
                Integer productImageId = Integer.parseInt(req.getParameter("productImageId"));
                Integer productId = Integer.parseInt(req.getParameter("productId"));
                System.out.println(productImageStr + " " + productImageId + " " + productId);

                ProductImage productImage = new ProductImage(productImageId, productImageStr, productService.getProductById(productId));
                Path imagePath = Path.of(getServletContext().getRealPath("/") + "product-images/" + productImageStr);

                productImageService.deleteProductImage(productImage);
                if (Files.exists(imagePath)) Files.delete(imagePath);


                res.sendRedirect(req.getHeader("Referer"));
        }
}
