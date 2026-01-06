package cnpm.ergo.controller;

import java.io.IOException;
import java.util.List;

import cnpm.ergo.entity.Blog;
import cnpm.ergo.service.implement.BlogServiceImpl;
import cnpm.ergo.service.interfaces.IBlogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns = {"/customer/blogs","/customer/blog/details","/customer/blog/search"})
public class BlogController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public IBlogService blogService = new BlogServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if(url.contains("/customer/blogs"))
		{
			List<Blog> list = blogService.getAllBlogs();

			req.setAttribute("listblog", list);

			req.getRequestDispatcher("/customer/views/blog-list.jsp").forward(req, resp);
		}
		else if (url.contains("/customer/blog/details")) {

			int id = Integer.parseInt(req.getParameter("id"));

			Blog blog = blogService.getBlogById(id);

			req.setAttribute("blog", blog);

			req.getRequestDispatcher("/customer/views/blog-details.jsp").forward(req, resp);

		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if(url.contains("/customer/blog/search"))
		{
			String title = req.getParameter("title");
			
			List<Blog>list=blogService.searchBlogsByTitle(title);
			
			req.setAttribute("listblog", list);
			
			req.getRequestDispatcher("/customer/views/blog-list.jsp").forward(req, resp);
		}
	}
}
