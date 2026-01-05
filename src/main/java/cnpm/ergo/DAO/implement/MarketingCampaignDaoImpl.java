package cnpm.ergo.DAO.implement;

import java.util.List;

import cnpm.ergo.DAO.interfaces.IMarketingCampaignDao;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.CampaignImage;
import cnpm.ergo.entity.MarketingCampaign;
import cnpm.ergo.entity.VoucherByPrice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class MarketingCampaignDaoImpl implements IMarketingCampaignDao {

	@Override
	public MarketingCampaign findById(Long id) {
		EntityManager entityManager = JPAConfig.getEntityManager();
		return entityManager.find(MarketingCampaign.class, id);
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
            throw new RuntimeException("Không thêm được MarketingCampaignEntity này: " + e.getMessage(), e);
        }finally {
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
			throw new RuntimeException("Cập nhật MarketingCampaign không thành công: " + e.getMessage(), e);
		}
	}
	@Override
	public void delete(MarketingCampaign campaignEntity) {
		EntityManager entityManager = JPAConfig.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			campaignEntity.setIsDelete(true);
			entityManager.merge(campaignEntity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw new RuntimeException("Cập nhật MarketingCampaign không thành công: " + e.getMessage(), e);
		}
	}

//	@Override
//	public void delete(Long campaignId) {
//		EntityTransaction transaction = entityManager.getTransaction();
//		try {
//			transaction.begin();
//			MarketingCampaign campaign = entityManager.find(MarketingCampaign.class, campaignId);
//			if (campaign != null) {
//				campaign.setIsDelete(true);
//				entityManager.merge(campaign);
//			} else {
//				throw new RuntimeException("Không tìm thấy MarketingCampaign với ID: " + campaignId);
//			}
//
//			transaction.commit();
//		} catch (Exception e) {
//			if (transaction.isActive()) {
//				transaction.rollback();
//			}
//			throw new RuntimeException("Xóa lỗi: " + e.getMessage(), e);
//		}
//	}

	@Override
	public List<MarketingCampaign> findAll() {
		EntityManager entityManager = JPAConfig.getEntityManager();
		try {
			return entityManager.createQuery("SELECT m FROM MarketingCampaign m where m.isDelete = false ", MarketingCampaign.class)
					.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Lỗi truy vấn: " + e.getMessage(), e);
		}
	}
	@Override
	public MarketingCampaign getLatestCampaign() {
		EntityManager entityManager = JPAConfig.getEntityManager();
		try {
			return entityManager.createQuery("SELECT c FROM MarketingCampaign c ORDER BY c.campaignId DESC", MarketingCampaign.class)
					.setMaxResults(1)
					.getSingleResult();
		}catch (Exception e) {
			throw new RuntimeException("Lỗi truy vấn: " + e.getMessage(), e);
		}
	}
}
