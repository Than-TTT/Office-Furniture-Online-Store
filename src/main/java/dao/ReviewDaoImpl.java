package dao;

import jakarta.persistence.*;
import model.Review;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewDaoImpl implements IReviewDao {
    
    private static EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("EcommercePU");

    @Override
    public void addReview(Review review) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            review.setReviewDate(LocalDateTime.now());
            em.persist(review);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Review> getReviewsByProductId(int productId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT r FROM Review r WHERE r.productId = :productId ORDER BY r.reviewDate DESC", 
                Review.class)
                .setParameter("productId", productId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Review getReviewByCustomerAndProduct(int customerId, int productId) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Review> reviews = em.createQuery(
                "SELECT r FROM Review r WHERE r.customerId = :customerId AND r.productId = :productId", 
                Review.class)
                .setParameter("customerId", customerId)
                .setParameter("productId", productId)
                .getResultList();
            
            return reviews.isEmpty() ? null : reviews.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean hasCustomerPurchasedProduct(int customerId, int productId) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(oi) FROM OrderItem oi " +
                "JOIN oi.order o " +
                "WHERE o.customerId = :customerId " +
                "AND oi.productId = :productId " +
                "AND o.status = 'Delivered'", 
                Long.class)
                .setParameter("customerId", customerId)
                .setParameter("productId", productId)
                .getSingleResult();
            
            return count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public void updateReview(Review review) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            review.setReviewDate(LocalDateTime.now());
            em.merge(review);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteReview(int reviewId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Review review = em.find(Review.class, reviewId);
            if (review != null) {
                em.remove(review);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}