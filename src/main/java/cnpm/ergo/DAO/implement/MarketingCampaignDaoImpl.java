package cnpm.ergo.DAO.implement;

import java.util.List;
import cnpm.ergo.DAO.interfaces.IMarketingCampaignDao;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.MarketingCampaign;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MarketingCampaignDaoImpl implements IMarketingCampaignDao {

    @Override
    public MarketingCampaign findById(Long id) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.find(MarketingCampaign.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void insert(MarketingCampaign campaignEntity) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(campaignEntity); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Không thêm được MarketingCampaign: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(MarketingCampaign campaignEntity) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(campaignEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cập nhật MarketingCampaign thất bại: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Long id) { // Đã sửa thành Long id và khớp @Override
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            MarketingCampaign campaign = entityManager.find(MarketingCampaign.class, id);
            if (campaign != null) {
                // Thực hiện Soft Delete
                campaign.setIsDelete(true);
                entityManager.merge(campaign);
            } else {
                throw new RuntimeException("Không tìm thấy MarketingCampaign với ID: " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Lỗi khi xóa MarketingCampaign: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<MarketingCampaign> findAll() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Chỉ lấy những campaign chưa bị xóa (isDelete = false)
            return entityManager.createQuery("SELECT m FROM MarketingCampaign m WHERE m.isDelete = false", MarketingCampaign.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn danh sách MarketingCampaign: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public MarketingCampaign getLatestCampaign() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM MarketingCampaign c ORDER BY c.campaignId DESC", MarketingCampaign.class)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            // Trả về null nếu không có bản ghi nào thay vì quăng lỗi để tránh crash app
            return null; 
        } finally {
            entityManager.close();
        }
    }
}