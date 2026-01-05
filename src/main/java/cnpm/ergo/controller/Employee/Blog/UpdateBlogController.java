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

@WebServlet(name = "UpdateBlogController", value = "/employee/update-blog")
public class UpdateBlogController extends HttpServlet {
    private IBlogService blogService = new BlogServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("employee") == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        String blogIdParam = request.getParameter("blogId");
        if (blogIdParam != null && !blogIdParam.trim().isEmpty()) {
            try {
                int blogId = Integer.parseInt(blogIdParam);
                Blog blog = blogService.getBlogById(blogId);
                if (blog != null) {
                    request.setAttribute("blog", blog);
                    request.getRequestDispatcher("/employee/views/edit-blog.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/employee/blog?error=Blog not found");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/employee/blog?error=Invalid blog ID");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/employee/blog?error=Blog ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("employee") == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        String blogIdParam = request.getParameter("blogId");
        String blogTitle = request.getParameter("blogTitle");
        String content = request.getParameter("content");

        if (blogIdParam == null || blogTitle == null || content == null) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/employee/views/edit-blog.jsp").forward(request, response);
            return;
        }

        try {
            int blogId = Integer.parseInt(blogIdParam);
            Blog blog = blogService.getBlogById(blogId);
            if (blog != null) {
                blog.setBlogTitle(blogTitle);
                blog.setContent(content);
                blogService.updateBlog(blog);
                response.sendRedirect(request.getContextPath() + "/employee/blog");
            } else {
                request.setAttribute("error", "Blog not found");
                request.getRequestDispatcher("/employee/views/edit-blog.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid blog ID");
            request.getRequestDispatcher("/employee/views/edit-blog.jsp").forward(request, response);
        }
    }
}
