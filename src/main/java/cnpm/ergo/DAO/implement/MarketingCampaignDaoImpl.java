// ...existing code...
package cnpm.ergo.DAO.implement;

import java.util.List;

import cnpm.ergo.DAO.interfaces.IMarketingCampaignDao;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.CampaignImage;
import cnpm.ergo.entity.MarketingCampaign;
import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherByPrice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class MarketingCampaignDaoImpl implements IMarketingCampaignDao {
    /*...*/

    @Override
    public List<MarketingCampaign> findAll() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Fetch campaign images eagerly to avoid LazyInitializationException in the view
            String jpql = "SELECT DISTINCT m FROM MarketingCampaign m LEFT JOIN FETCH m.campaignImages WHERE m.isDelete = false";
            TypedQuery<MarketingCampaign> query = entityManager.createQuery(jpql, MarketingCampaign.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public MarketingCampaign findById(Long id) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Also fetch images when fetching a single campaign
            String jpql = "SELECT m FROM MarketingCampaign m LEFT JOIN FETCH m.campaignImages WHERE m.campaignId = :id";
            TypedQuery<MarketingCampaign> query = entityManager.createQuery(jpql, MarketingCampaign.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm MarketingCampaign theo id: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public MarketingCampaign getLatestCampaign() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // fetch images as well
            String jpql = "SELECT m FROM MarketingCampaign m LEFT JOIN FETCH m.campaignImages ORDER BY m.campaignId DESC";
            TypedQuery<MarketingCampaign> query = entityManager.createQuery(jpql, MarketingCampaign.class);
            query.setMaxResults(1);
            List<MarketingCampaign> res = query.getResultList();
            return res.isEmpty() ? null : res.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn getLatestCampaign: " + e.getMessage(), e);
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
            // Merge the voucher if it exists (handles detached entity)
            if (campaignEntity.getVoucher() != null) {
                Voucher managedVoucher = entityManager.merge(campaignEntity.getVoucher());
                campaignEntity.setVoucher(managedVoucher);
            }
            entityManager.persist(campaignEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Không thêm được MarketingCampaignEntity này: " + e.getMessage(), e);
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
            
            // Find the managed entity first
            MarketingCampaign managedCampaign = entityManager.find(MarketingCampaign.class, campaignEntity.getCampaignId());
            if (managedCampaign == null) {
                throw new RuntimeException("Campaign not found with id: " + campaignEntity.getCampaignId());
            }
            
            // Update fields on the managed entity
            managedCampaign.setContent(campaignEntity.getContent());
            managedCampaign.setIsDelete(campaignEntity.getIsDelete());
            
            // Handle voucher relationship
            if (campaignEntity.getVoucher() != null) {
                Voucher managedVoucher = entityManager.find(Voucher.class, campaignEntity.getVoucher().getVoucherId());
                managedCampaign.setVoucher(managedVoucher);
            } else {
                managedCampaign.setVoucher(null);
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cập nhật MarketingCampaign không thành công: " + e.getMessage(), e);
        } finally {
            entityManager.close();
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
        } finally {
            entityManager.close();
        }
    }
    // ...existing code...
}