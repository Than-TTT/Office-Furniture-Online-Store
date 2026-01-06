package cnpm.ergo.controller.Admin.Product;

import cnpm.ergo.DAO.interfaces.IProductType;
import cnpm.ergo.entity.ProductType;
import cnpm.ergo.service.implement.ProductServiceImpl;
import cnpm.ergo.service.implement.ProductTypeServiceImpl;
import cnpm.ergo.service.interfaces.IProductService;
import cnpm.ergo.service.interfaces.IProductTypeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/edittype")
public class EditProductTypeController extends HttpServlet {
        IProductTypeService productTypeService = new ProductTypeServiceImpl();
        IProductService productService = new ProductServiceImpl();
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                if (req.getSession().getAttribute("admin") == null) {
                        res.sendRedirect("/admin/login");
                        return;
                }
                try {
                        int typeId = Integer.parseInt(req.getParameter("typeId"));
                        int productId = Integer.parseInt(req.getParameter("productId"));
                        String color = req.getParameter("color");
                        double width = Double.parseDouble(req.getParameter("width"));
                        double height = Double.parseDouble(req.getParameter("height"));
                        double length = Double.parseDouble(req.getParameter("length"));
                        double weight = Double.parseDouble(req.getParameter("weight"));
                        String material = req.getParameter("material");
                        double price = Double.parseDouble(req.getParameter("price"));
                        int quantity = Integer.parseInt(req.getParameter("quantity"));
                        ProductType productType = new ProductType();
                        productType.setTypeId(typeId);
                        productType.setProduct(productService.getProductById(productId));
                        productType.setColor(color);
                        productType.setWidth(width);
                        productType.setHeight(height);
                        productType.setLength(length);
                        productType.setWeight(weight);
                        productType.setMaterial(material);
                        productType.setPrice(price);
                        productType.setQuantity(quantity);
                        productTypeService.updateProductType(productType);
                        res.sendRedirect(req.getHeader("Referer"));
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                        // Forward the error details to an error page
                        req.setAttribute("errorMessage", "Failed to get the orders information. Please try again.");
                        req.getRequestDispatcher("/errorPage.jsp").forward(req, res);
                }
        }
}
