package cnpm.ergo.DAO.interfaces;

import java.time.LocalDate;
import java.util.List;
import cnpm.ergo.entity.Blog;

public interface IBlogDao {
    void insert(Blog blog);
    void update(Blog blog);
    void delete(int blogId);
    Blog findById(int blogId);
    List<Blog> findAll();
    List<Blog> findWaitBlogByPage(int offset, int limit);
    List<Blog> searchByTitle(String title);
    int count();
    int waitBlogCount();
	Integer findIdByTitle(String title);
	List<Blog> searchByDate(String date);
}
