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
import java.time.LocalDate;

@WebServlet(name = "AddBlogController", value = "/employee/add-blog")
public class AddBlogController extends HttpServlet {
    private IBlogService blogService = new BlogServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/employee/views/add-blog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("employee") == null) {
            response.sendRedirect(request.getContextPath() + "/employee/login");
            return;
        }
        String blogTitle = request.getParameter("blogTitle");
        String content = request.getParameter("content");

        if (blogTitle == null || content == null) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/employee/views/add-blog.jsp").forward(request, response);
            return;
        }

        Blog newBlog = new Blog();
        newBlog.setBlogTitle(blogTitle);
        newBlog.setContent(content);
        newBlog.setPostingDate(LocalDate.now()); // Đặt ngày đăng là ngày hiện tại

        blogService.addBlog(newBlog);
        response.sendRedirect(request.getContextPath() + "/employee/blog");
    }
}
