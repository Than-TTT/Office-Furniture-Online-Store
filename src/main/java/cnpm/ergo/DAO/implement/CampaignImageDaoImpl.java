package cnpm.ergo.DAO.implement;

import java.util.List;
import cnpm.ergo.DAO.interfaces.ICampaignImageDao;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.CampaignImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CampaignImageDaoImpl implements ICampaignImageDao {

    @Override
    public void addImage(CampaignImage campaignImage) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(campaignImage);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Lỗi khi thêm CampaignImage: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<CampaignImage> findImagesByCampaignId(Long campaignId) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Truy vấn thông qua đối tượng marketingCampaign
            return entityManager.createQuery(
                "SELECT ci FROM CampaignImage ci WHERE ci.marketingCampaign.campaignId = :campaignId", 
                CampaignImage.class)
                .setParameter("campaignId", campaignId)
                .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi truy vấn CampaignImage: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteByCampaignId(Long campaignId) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            // SỬA LỖI TẠI ĐÂY: Trỏ vào thuộc tính ID của đối tượng marketingCampaign
            entityManager.createQuery("DELETE FROM CampaignImage ci WHERE ci.marketingCampaign.campaignId = :campaignId")
                    .setParameter("campaignId", campaignId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Lỗi khi xóa hình ảnh theo CampaignId: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(CampaignImage image) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(image);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw new RuntimeException("Lỗi khi cập nhật CampaignImage: " + e.getMessage(), e);
        } finally {
            enma.close();
        }
    }

    @Override
    public CampaignImage finByPath(String path) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.createQuery(
                "SELECT ci FROM CampaignImage ci WHERE ci.imagePath = :imagePath", 
                CampaignImage.class)
                .setParameter("imagePath", path)
                .getResultStream()
                .findFirst()
                .orElse(null);
        } finally {
            entityManager.close();
        }
    }
}