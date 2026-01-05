package cnpm.ergo.DAO.interfaces;

import cnpm.ergo.entity.Review;

import java.util.List;

public interface IReviewDao {
    List<Review> findReviewsAll(int productId);
    void insert(Review review);
    void update(Review review);
    void delete(int reviewId);
    Review findById(int reviewId);
    List<Review> findAll();
    List<Review> findByProductId(int productId);
}

