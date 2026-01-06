package cnpm.ergo.controller.Employee.Blog;

import cnpm.ergo.entity.Blog;
import cnpm.ergo.service.implement.BlogServiceImpl;
import cnpm.ergo.service.interfaces.IBlogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteBlogController", value = "/employee/delete-blog")
public class DeleteBlogController extends HttpServlet {
    private IBlogService blogService = new BlogServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("employee") == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        String blogIdParam = request.getParameter("blogId");
        if (blogIdParam != null && !blogIdParam.trim().isEmpty()) {
            try {
                int blogId = Integer.parseInt(blogIdParam);
                blogService.deleteBlog(blogId);
                request.setAttribute("successMessage", "Blog deleted successfully");
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid blog ID");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error deleting blog");
            }
        } else {
            request.setAttribute("errorMessage", "Blog ID is required");
        }
        // Quay lại trang blog.jsp
        List<Blog> blogs = blogService.getAllBlogs();
        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("/employee/views/blog.jsp").forward(request, response);
    }
}
