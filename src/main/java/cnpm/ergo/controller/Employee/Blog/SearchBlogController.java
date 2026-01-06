package cnpm.ergo.controller.Employee.Blog;

import cnpm.ergo.entity.Blog;
import cnpm.ergo.service.implement.BlogServiceImpl;
import cnpm.ergo.service.interfaces.IBlogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchBlogController", value = "/employee/search-blog")
public class SearchBlogController extends HttpServlet {
    private IBlogService blogService = new BlogServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String dateParam = request.getParameter("date");
        List<Blog> blogs;

        if (title != null && !title.isEmpty()) {
            blogs = blogService.searchBlogsByTitle(title);
        } else if (dateParam != null && !dateParam.isEmpty()) {
            blogs = blogService.searchByDate(dateParam);
        } else {
            blogs = blogService.getAllBlogs();
        }

        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("/employee/views/blog.jsp").forward(request, response);
    }
}
