package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.ReviewDaoImpl;
import cnpm.ergo.DAO.interfaces.IReviewDao;
import cnpm.ergo.entity.Review;
import cnpm.ergo.service.interfaces.IReviewService;

import java.util.List;

public class ReviewServiceImpl implements IReviewService {

    private final IReviewDao reviewDao = new ReviewDaoImpl();

    @Override
    public void insert(Review review) {
        reviewDao.insert(review);
    }

    @Override
    public void update(Review review) {
        reviewDao.update(review);
    }

    @Override
    public void delete(int reviewId) {
        reviewDao.delete(reviewId);
    }

    @Override
    public Review findById(int reviewId) {
        return reviewDao.findById(reviewId);
    }

    @Override
    public List<Review> findAll() {
        return reviewDao.findAll();
    }

    @Override
    public List<Review> findByProductId(int productId) {
        return reviewDao.findByProductId(productId);
    }
    @Override
    public List<Review> getReviewsAll(int productId) {
        return reviewDao.findReviewsAll(productId);
    }
}
