package service;

import model.Review;
import java.util.List;

public interface IReviewService {
    
    /**
     * Thêm đánh giá (với validation)
     */
    boolean addReview(int customerId, int productId, int rating, String comment);
    
    /**
     * Lấy tất cả đánh giá của sản phẩm
     */
    List<Review> getProductReviews(int productId);
    
    /**
     * Kiểm tra khách hàng có thể đánh giá sản phẩm không
     */
    boolean canCustomerReviewProduct(int customerId, int productId);
    
    /**
     * Lấy đánh giá của khách hàng cho sản phẩm
     */
    Review getCustomerReview(int customerId, int productId);
    
    /**
     * Cập nhật đánh giá
     */
    boolean updateReview(int reviewId, int rating, String comment);
    
    /**
     * Xóa đánh giá
     */
    boolean deleteReview(int reviewId, int customerId);
}