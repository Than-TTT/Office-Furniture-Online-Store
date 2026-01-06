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

@WebServlet(urlPatterns = "/admin/blog/approve")
public class ApproveBlogController extends HttpServlet {

        IBlogService blogService = new BlogServiceImpl();
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
                if (req.getSession().getAttribute("admin") == null) {
                        res.sendRedirect("/admin/login");
                        return;
                }
                try {
                        Integer blogId = Integer.parseInt(req.getParameter("blogId"));
                        Blog blog = blogService.getBlogById(blogId);
                        blog.setApproval(true);
                        blogService.updateBlog(blog);
                        res.sendRedirect(req.getHeader("Referer"));
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                        // Forward the error details to an error page
                        req.setAttribute("errorMessage", "Failed to update the employee. Please try again.");
                        req.getRequestDispatcher("/errorPage.jsp").forward(req, res);
                }
        }
}
