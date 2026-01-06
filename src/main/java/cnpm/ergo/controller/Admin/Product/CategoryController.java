package cnpm.ergo.controller.Admin.Product;

import cnpm.ergo.entity.Category;
import cnpm.ergo.service.implement.CategoryServiceImpl;
import cnpm.ergo.service.interfaces.ICategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {
    "/admin/category",
    "/admin/category/add",
    "/admin/category/update",
    "/admin/category/delete"
})
public class CategoryController extends HttpServlet {

    private final ICategoryService categoryService = new CategoryServiceImpl();

    // ===================== GET =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.contains("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            categoryService.deleteCategory(id);

            response.sendRedirect(request.getContextPath() + "/admin/product");
        }
    }

    // ===================== POST =====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        String categoryName = request.getParameter("categoryName");

        if (path.contains("/add")) {
            Category cat = new Category();
            cat.setCategoryName(categoryName);
            categoryService.addCategory(cat);
        }
        else if (path.contains("/update")) {
            int id = Integer.parseInt(request.getParameter("categoryId"));
            Category cat = categoryService.getCategoryById(id);
            if (cat != null) {
                cat.setCategoryName(categoryName);
                categoryService.updateCategory(cat);
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/product");
    }
}
