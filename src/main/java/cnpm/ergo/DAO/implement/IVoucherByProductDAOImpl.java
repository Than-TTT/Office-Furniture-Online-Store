package cnpm.ergo.DAO.implement;

import cnpm.ergo.DAO.interfaces.IVoucherByProductDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.VoucherByProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class IVoucherByProductDAOImpl implements IVoucherByProductDAO {

    @Override
    public void insert(VoucherByProduct voucher) {
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
    public void update(VoucherByProduct voucher) {
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
    public void delete(VoucherByProduct voucher) {
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
    public List<VoucherByProduct> findAll() {
        EntityManager enma   = JPAConfig.getEntityManager();
        try {
            TypedQuery<VoucherByProduct> query = enma.createNamedQuery("VoucherByProduct.findAllActive", VoucherByProduct.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public VoucherByProduct findById(int Id) {
        EntityManager enma   = JPAConfig.getEntityManager();
        try {
            VoucherByProduct voucher = enma.find(VoucherByProduct.class, Id);
            if (voucher != null) {
                // safe debug prints (optional)
                // System.out.println("Found voucher: " + voucher.getCode());
            }
            return voucher;
        } finally {
            enma.close();
        }
    }

    @Override
    public List<VoucherByProduct> findAll(int pageNo, int pageSize) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // No need to begin transaction for read-only, but keep consistent behavior
            TypedQuery<VoucherByProduct> query = entityManager.createNamedQuery("VoucherByProduct.findAllActive", VoucherByProduct.class);

            query.setFirstResult(Math.max(0, (pageNo - 1)) * pageSize);
            query.setMaxResults(pageSize);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}