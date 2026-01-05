package cnpm.ergo.DAO.implement;

import java.util.List;

import cnpm.ergo.DAO.interfaces.ICampaignImageDao;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.CampaignImage;
import cnpm.ergo.entity.MarketingCampaign;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class CampaignImageDaoImpl implements ICampaignImageDao{

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
        }
        finally {
            entityManager.close();
        }
    }

    @Override
    public List<CampaignImage> findImagesByCampaignId(Long campaignId) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.createQuery("SELECT ci FROM CampaignImage ci WHERE ci.marketingCampaign.campaignId = :campaignId", CampaignImage.class)
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
            entityManager.createQuery("DELETE FROM CampaignImage ci WHERE ci.campaignId = :campaignId")
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
    public void update (CampaignImage image)
    {
        EntityManager enma   = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(image);
            trans.commit();

        } catch (Exception e )
        {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public CampaignImage finByPath(String path) {
            EntityManager entityManager = JPAConfig.getEntityManager();
            return entityManager.createQuery(
                    "select ci from CampaignImage ci where ci.imagePath = :imagePath", CampaignImage.class)
                    .setParameter("imagePath",path).getResultStream().findFirst().orElse(null);
    }

}