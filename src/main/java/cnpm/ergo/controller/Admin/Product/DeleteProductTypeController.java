package cnpm.ergo.controller.Admin.Product;

import cnpm.ergo.service.implement.ProductTypeServiceImpl;
import cnpm.ergo.service.interfaces.IProductTypeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/deletetype")
public class DeleteProductTypeController extends HttpServlet {
    IProductTypeService productTypeService = new ProductTypeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Kiểm tra quyền admin
        if (req.getSession().getAttribute("admin") == null) {
            res.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        String productId = req.getParameter("productId");
        String referer = req.getHeader("Referer");

        try {
            int typeId = Integer.parseInt(req.getParameter("id"));
            productTypeService.deleteProductType(typeId);
            
            // Quay lại và đính kèm fragment để JS mở modal
            res.sendRedirect(referer + "#edit-" + productId);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.getSession().setAttribute("error", "Lỗi: " + ex.getMessage());
            res.sendRedirect(referer + "#edit-" + productId);
        }
    }
}