package cnpm.ergo.service.interfaces;

import java.util.List;
import cnpm.ergo.entity.Blog;

public interface IBlogService {
    void addBlog(Blog blog);
    void updateBlog(Blog blog);
    void deleteBlog(int blogId);
    Blog getBlogById(int blogId);
    List<Blog> getAllBlogs();
    List<Blog> getWaitBlogsByPage(int offset, int limit);
    List<Blog> searchBlogsByTitle(String title);
    int getBlogCount();
    int getWaitBlogCount();
	Integer findIdByTitle(String title);
	List<Blog> searchByDate(String date);
}
