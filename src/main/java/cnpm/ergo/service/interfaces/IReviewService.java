package cnpm.ergo.service.interfaces;

import java.util.List;

import cnpm.ergo.entity.Review;

public interface IReviewService {
	List<Review> getReviewsAll(int productId);
    void insert(Review review);
    void update(Review review);
    void delete(int reviewId);
    Review findById(int reviewId);
    List<Review> findAll();
    List<Review> findByProductId(int productId);
}
