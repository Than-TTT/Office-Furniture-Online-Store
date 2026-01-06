package cnpm.ergo.controller.Admin.Blog;

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

@WebServlet(urlPatterns = "/admin/blog")
public class BlogController extends HttpServlet {

        IBlogService blogService = new BlogServiceImpl();
        private final Integer BLOG_PER_PAGE = 10;
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                if (req.getSession().getAttribute("admin") == null) {
                        res.sendRedirect("/admin/login");
                        return;
                }
                try {
                        int page = 1;
                        if (req.getParameter("page") != null) {
                                page = Integer.parseInt(req.getParameter("page"));
                        }
                        List<Blog> listBlog = blogService.getWaitBlogsByPage((page - 1) * BLOG_PER_PAGE, BLOG_PER_PAGE);
                        int currentPage = page;
                        int totalPages = (blogService.getWaitBlogCount() + BLOG_PER_PAGE - 1) / BLOG_PER_PAGE;

                        req.setAttribute("listBlog", listBlog);
                        req.setAttribute("currentPage", currentPage);
                        req.setAttribute("totalPages", totalPages);

                        req.getRequestDispatcher("/admin/views/blog.jsp").forward(req, res);
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                        // Forward the error details to an error page
                        req.setAttribute("errorMessage", "Failed to update the employee. Please try again.");
                        req.getRequestDispatcher("/errorPage.jsp").forward(req, res);
                }
        }
}
