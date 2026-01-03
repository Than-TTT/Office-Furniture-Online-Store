// ...existing code...
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MarketingCampaignDaoImpl implements dao.IMarketingCampaignDao {
    /*...*/

    @Override
    public List<model.MarketingCampaign> findAll() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Fetch campaign images eagerly to avoid LazyInitializationException in the view
            String jpql = "SELECT DISTINCT m FROM MarketingCampaign m LEFT JOIN FETCH m.campaignImages WHERE m.isDelete = false";
            TypedQuery<model.MarketingCampaign> query = entityManager.createQuery(jpql, model.MarketingCampaign.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public model.MarketingCampaign findById(Long id) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Also fetch images when fetching a single campaign
            String jpql = "SELECT m FROM MarketingCampaign m LEFT JOIN FETCH m.campaignImages WHERE m.campaignId = :id";
            TypedQuery<model.MarketingCampaign> query = entityManager.createQuery(jpql, model.MarketingCampaign.class);
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
    public model.MarketingCampaign getLatestCampaign() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // fetch images as well
            String jpql = "SELECT m FROM MarketingCampaign m LEFT JOIN FETCH m.campaignImages ORDER BY m.campaignId DESC";
            TypedQuery<model.MarketingCampaign> query = entityManager.createQuery(jpql, model.MarketingCampaign.class);
            query.setMaxResults(1);
            List<model.MarketingCampaign> res = query.getResultList();
            return res.isEmpty() ? null : res.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy vấn getLatestCampaign: " + e.getMessage(), e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void insert(model.MarketingCampaign campaignEntity) {
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
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(model.MarketingCampaign campaignEntity) {
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
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(model.MarketingCampaign campaignEntity) {
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
