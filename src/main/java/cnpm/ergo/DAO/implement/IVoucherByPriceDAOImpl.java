package cnpm.ergo.DAO.implement;

import cnpm.ergo.DAO.interfaces.IVoucherByPriceDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.VoucherByPrice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class IVoucherByPriceDAOImpl implements IVoucherByPriceDAO {

    @Override
    public void insert(VoucherByPrice voucher) {
        EntityManager enma   = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(voucher);
            trans.commit();

        } catch (Exception e )
        {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }

    }

    @Override
    public void update(VoucherByPrice voucher) {
        EntityManager enma   = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(voucher);
            trans.commit();

        } catch (Exception e )
        {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }

    }

    @Override
    public void delete(VoucherByPrice voucher) {
        EntityManager enma   = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            voucher.setDelete(true);
            enma.merge(voucher);
            trans.commit();

        } catch (Exception e )
        {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            enma.close();
        }

    }

    @Override
    public List<VoucherByPrice> findAll() {
        EntityManager enma   = JPAConfig.getEntityManager();
        try {
            TypedQuery<VoucherByPrice> query = enma.createNamedQuery("VoucherByPrice.findAllActive", VoucherByPrice.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public VoucherByPrice findById(int Id) {
        EntityManager enma   = JPAConfig.getEntityManager();
        try {
            VoucherByPrice voucher = enma.find(VoucherByPrice.class,Id);
            return voucher;
        } finally {
            enma.close();
        }

    }

    @Override
    public List<VoucherByPrice> findAll(int pageNo, int pageSize) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            TypedQuery<VoucherByPrice> query = entityManager.createNamedQuery("VoucherByPrice.findAllActive", VoucherByPrice.class);

            query.setFirstResult(Math.max(0, (pageNo - 1)) * pageSize);
            query.setMaxResults(pageSize);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

}