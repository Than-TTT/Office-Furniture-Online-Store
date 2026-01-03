package dao;

import dao.IVoucherByProductDAO;
import config.JPAConfig;
import model.Voucher;
import model.VoucherByPrice;
import model.VoucherByProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class IVoucherByProductDAOImpl implements IVoucherByProductDAO {
    private EntityManager enma   = JPAConfig.getEntityManager();
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
            trans.rollback();
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
            trans.rollback();
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
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }

    }

    @Override
    public List<VoucherByProduct> findAll() {
        EntityManager enma   = JPAConfig.getEntityManager();
        TypedQuery<VoucherByProduct> query = enma.createNamedQuery("VoucherByProduct.findAll", VoucherByProduct.class);
        return query.getResultList();
    }

    @Override
    public VoucherByProduct findById(int Id) {
        EntityManager enma   = JPAConfig.getEntityManager();
        try {
            VoucherByProduct voucher = enma.find(VoucherByProduct.class, Id);
            System.out.println("tim duoc san pham");
            System.out.println(voucher.getCode());
            return voucher;
        }
        catch (NoResultException  e)
        {
            System.out.println("khoong  duoc san pham");
            return null;
        }
        finally {
            enma.close();
        }
    }

    @Override
    public List<VoucherByProduct> findAll(int pageNo, int pageSize) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin a transaction
            entityManager.getTransaction().begin();

            // Create a query to find all employees
            TypedQuery<VoucherByProduct> query = entityManager.createNamedQuery("VoucherByProduct.findAll", VoucherByProduct.class);

            // Set the first result and max results for pagination
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);

            // Get the list of employees
            List<VoucherByProduct> vouchers = query.getResultList();

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
