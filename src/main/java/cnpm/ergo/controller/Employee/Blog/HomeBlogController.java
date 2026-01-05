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

@WebServlet(name = "HomeBlogController", value = "/employee/blog")
public class HomeBlogController extends HttpServlet {
    private IBlogService blogService = new BlogServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("employee") == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        List<Blog> blogs = blogService.getAllBlogs();
        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("/employee/views/blog.jsp").forward(request, response);
        
    }
}
