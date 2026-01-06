package cnpm.ergo.DAO.implement;

import java.util.List;

import cnpm.ergo.DAO.interfaces.IReviewDao;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ReviewDaoImpl implements IReviewDao {

    @Override
    public void insert(Review review) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(review);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Review review) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(review);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int reviewId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Review review = em.find(Review.class, reviewId);
            if (review != null) {
                em.remove(review);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Review findById(int reviewId) {
        EntityManager em = JPAConfig.getEntityManager();
        return em.find(Review.class, reviewId);
    }

    @Override
    public List<Review> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        String jpql = "SELECT r FROM Review r";
        TypedQuery<Review> query = em.createQuery(jpql, Review.class);
        return query.getResultList();
    }

    @Override
    public List<Review> findByProductId(int productId) {
        EntityManager em = JPAConfig.getEntityManager();
        String jpql = "SELECT r FROM Review r WHERE r.product.productId = :productId";
        TypedQuery<Review> query = em.createQuery(jpql, Review.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

	@Override
	public List<Review> findReviewsAll(int productId) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        String jpql = "SELECT r FROM Review r WHERE r.product.productId = :productId";
	        return em.createQuery(jpql, Review.class)
	                .setParameter("productId", productId)
	                .getResultList();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        throw e; 
	    } finally {
	        em.close(); 
	    }
	}

	 public static void main(String[] args) {
	        ReviewDaoImpl reviewDao = new ReviewDaoImpl();
	        int productId = 1; 

	        try {
	            List<Review> reviews = reviewDao.findReviewsAll(productId);
	            System.out.println("Reviews for Product ID: " + productId);
	            for (Review review : reviews) {
	                System.out.println("Review ID: " + review.getReviewId());
	                System.out.println("Content: " + review.getContent());
	                System.out.println("Rating: " + review.getRating());
	                System.out.println("Created At: " + review.getCreateAt());
	                System.out.println("----------------------------------");
	            }

	            if (reviews.isEmpty()) {
	                System.out.println("No reviews found for Product ID: " + productId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("Error retrieving reviews: " + e.getMessage());
	    }
	 }
}

    
