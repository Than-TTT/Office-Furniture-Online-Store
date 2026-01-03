package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class IVoucherByPriceDAOImpl implements dao.IVoucherByPriceDAO {

    @Override
    public void insert(model.VoucherByPrice voucher) {
        EntityManager enma   = dao.JPAConfig.getEntityManager();
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
    public void update(model.VoucherByPrice voucher) {
        EntityManager enma   = dao.JPAConfig.getEntityManager();
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
    public void delete(model.VoucherByPrice voucher) {
        EntityManager enma   = dao.JPAConfig.getEntityManager();
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
    public List<model.VoucherByPrice> findAll() {
        EntityManager enma   = dao.JPAConfig.getEntityManager();
        try {
            TypedQuery<model.VoucherByPrice> query = enma.createNamedQuery("VoucherByPrice.findAll", model.VoucherByPrice.class);
            return query.getResultList();
        } finally {
            enma.close();
        }
    }

    @Override
    public model.VoucherByPrice findById(int Id) {
        EntityManager enma   = dao.JPAConfig.getEntityManager();
        try {
            model.VoucherByPrice voucher = enma.find(model.VoucherByPrice.class,Id);
            return voucher;
        } finally {
            enma.close();
        }

    }

    @Override
    public List<model.VoucherByPrice> findAll(int pageNo, int pageSize) {
        EntityManager entityManager = dao.JPAConfig.getEntityManager();
        try {
            TypedQuery<model.VoucherByPrice> query = entityManager.createNamedQuery("VoucherByPrice.findAll", model.VoucherByPrice.class);

            query.setFirstResult(Math.max(0, (pageNo - 1)) * pageSize);
            query.setMaxResults(pageSize);

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

}
