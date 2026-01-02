package service;

import dao.IReviewDao;
import dao.ReviewDaoImpl;
import model.Review;
import java.util.List;

public class ReviewServiceImpl implements IReviewService {
    
    private IReviewDao reviewDao = new ReviewDaoImpl();

    @Override
    public boolean addReview(int customerId, int productId, int rating, String comment) {
        // Validate
        if (rating < 1 || rating > 5) {
            return false;
        }
        
        // Kiểm tra đã đánh giá chưa
        Review existingReview = reviewDao.getReviewByCustomerAndProduct(customerId, productId);
        if (existingReview != null) {
            return false; // Đã đánh giá rồi
        }
        
        // Kiểm tra đã mua hàng chưa
        if (!reviewDao.hasCustomerPurchasedProduct(customerId, productId)) {
            return false; // Chưa mua sản phẩm
        }
        
        try {
            Review review = new Review();
            review.setCustomerId(customerId);
            review.setProductId(productId);
            review.setRating(rating);
            review.setComment(comment);
            
            reviewDao.addReview(review);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Review> getProductReviews(int productId) {
        return reviewDao.getReviewsByProductId(productId);
    }

    @Override
    public boolean canCustomerReviewProduct(int customerId, int productId) {
        // Kiểm tra đã mua hàng chưa
        if (!reviewDao.hasCustomerPurchasedProduct(customerId, productId)) {
            return false;
        }
        
        // Kiểm tra đã đánh giá chưa
        Review existingReview = reviewDao.getReviewByCustomerAndProduct(customerId, productId);
        return existingReview == null;
    }

    @Override
    public Review getCustomerReview(int customerId, int productId) {
        return reviewDao.getReviewByCustomerAndProduct(customerId, productId);
    }

    @Override
    public boolean updateReview(int reviewId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        
        try {
            Review review = new Review();
            review.setReviewId(reviewId);
            review.setRating(rating);
            review.setComment(comment);
            
            reviewDao.updateReview(review);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteReview(int reviewId, int customerId) {
        try {
            // Có thể thêm logic kiểm tra quyền sở hữu ở đây
            reviewDao.deleteReview(reviewId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}