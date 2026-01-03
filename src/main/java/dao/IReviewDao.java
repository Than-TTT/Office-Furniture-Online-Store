package dao;

import model.Review;
import java.util.List;

public interface IReviewDao {
    
    /**
     * Thêm đánh giá mới
     */
    void addReview(Review review);
    
    /**
     * Lấy tất cả đánh giá của sản phẩm
     */
    List<Review> getReviewsByProductId(int productId);
    
    /**
     * Lấy đánh giá của khách hàng cho sản phẩm
     */
    Review getReviewByCustomerAndProduct(int customerId, int productId);
    
    /**
     * Kiểm tra khách hàng đã mua sản phẩm chưa
     */
    boolean hasCustomerPurchasedProduct(int customerId, int productId);
    
    /**
     * Cập nhật đánh giá
     */
    void updateReview(Review review);
    
    /**
     * Xóa đánh giá
     */
    void deleteReview(int reviewId);
}