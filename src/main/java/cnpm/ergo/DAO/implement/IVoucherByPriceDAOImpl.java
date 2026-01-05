package cnpm.ergo.DAO.implement;

import cnpm.ergo.DAO.interfaces.IVoucherByPriceDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.entity.VoucherByProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
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
            trans.rollback();
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
            trans.rollback();
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
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }

    }

    @Override
    public List<VoucherByPrice> findAll() {
        EntityManager enma   = JPAConfig.getEntityManager();
        TypedQuery<VoucherByPrice> query = enma.createNamedQuery("VoucherByPrice.findAll", VoucherByPrice.class);
        return query.getResultList();
    }

    @Override
    public VoucherByPrice findById(int Id) {
        EntityManager enma   = JPAConfig.getEntityManager();
        try {
            VoucherByPrice voucher = enma.find(VoucherByPrice.class,Id);
            return voucher;
        }
        catch (NoResultException e)
        {
            return null;
        }finally {
            enma.close();
        }

    }

    @Override
    public List<VoucherByPrice> findAll(int pageNo, int pageSize) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin a transaction
            entityManager.getTransaction().begin();

            // Create a query to find all employees
            TypedQuery<VoucherByPrice> query = entityManager.createNamedQuery("VoucherByPrice.findAll", VoucherByPrice.class);

            // Set the first result and max results for pagination
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);

            // Get the list of employees
            List<VoucherByPrice> vouchers = query.getResultList();

            // Commit the transaction
            entityManager.getTransaction().commit();

            return vouchers;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

}
