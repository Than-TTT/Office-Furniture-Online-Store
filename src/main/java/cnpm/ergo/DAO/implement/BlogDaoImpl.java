package cnpm.ergo.DAO.implement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

import cnpm.ergo.DAO.interfaces.IBlogDao;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Blog;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BlogDaoImpl implements IBlogDao {

	@Override
	public void insert(Blog blog) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			em.persist(blog); // Thêm mới blog
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Blog blog) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			em.merge(blog); // Cập nhật blog
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(int blogId) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			Blog blog = em.find(Blog.class, blogId);
			if (blog != null) {
				em.remove(blog); // Xóa blog
			}
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public Blog findById(int blogId) {
		EntityManager em = JPAConfig.getEntityManager();
		return em.find(Blog.class, blogId); // Tìm blog theo ID
	}

	@Override
	public List<Blog> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT b FROM Blog b";
		TypedQuery<Blog> query = em.createQuery(jpql, Blog.class);
		return query.getResultList(); // Lấy danh sách tất cả blog
	}

	@Override
	public List<Blog> searchByTitle(String title) {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT b FROM Blog b WHERE b.blogTitle LIKE :title";
		TypedQuery<Blog> query = em.createQuery(jpql, Blog.class);
		query.setParameter("title", "%" + title + "%");
		return query.getResultList(); // Tìm blog theo tiêu đề
	}

	@Override
	public int count() {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT COUNT(b) FROM Blog b";
		Query query = em.createQuery(jpql);
		return ((Long) query.getSingleResult()).intValue(); // Đếm tổng số blog
	}

	@Override
	public Integer findIdByTitle(String title) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT b.blogId FROM Blog b WHERE b.blogTitle = :title";
			TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
			query.setParameter("title", title);
			return query.getSingleResult(); // Lấy blogId theo tiêu đề
		} catch (Exception e) {
			return null; // Trường hợp không tìm thấy blog
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Blog> findWaitBlogByPage(int offset, int limit) {
	    EntityManager em = JPAConfig.getEntityManager();
	    String jpql = "SELECT b FROM Blog b WHERE b.status = :status ORDER BY b.blogId DESC";
	    TypedQuery<Blog> query = em.createQuery(jpql, Blog.class);
	    query.setParameter("status", "WAIT");
	    query.setFirstResult(offset);
	    query.setMaxResults(limit);
	    return query.getResultList();
	}
	
	@Override
	public int waitBlogCount() {
	    EntityManager em = JPAConfig.getEntityManager();
	    String jpql = "SELECT COUNT(b) FROM Blog b WHERE b.status = :status";
	    Query query = em.createQuery(jpql);
	    query.setParameter("status", "WAIT");
	    return ((Long) query.getSingleResult()).intValue();
	}

	@Override
	public List<Blog> searchByDate(String date) { 
		EntityManager em = JPAConfig.getEntityManager();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		String jpql = "SELECT b FROM Blog b WHERE b.postingDate = :date";
		TypedQuery<Blog> query = em.createQuery(jpql, Blog.class);
		query.setParameter("date", localDate);
		return query.getResultList(); // Tìm blog theo ngày đăng 
	}
	

	public static void main(String[] args) {
		BlogDaoImpl blogDaoImpl = new BlogDaoImpl();
		// count
		System.out.println(blogDaoImpl.count());
		// findIdByTitle
		System.out.println(blogDaoImpl.findIdByTitle("Sample Title"));
	}
}
