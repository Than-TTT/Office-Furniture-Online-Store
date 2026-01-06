package cnpm.ergo.DAO.implement;

import cnpm.ergo.DAO.interfaces.ICustomerDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {

    @Override
    public long count() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            long count = entityManager.createQuery("SELECT COUNT(c) FROM Customer c where c.isDelete = false ", Long.class)
                                      .getSingleResult();
            return count;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Customer getCustomer(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery(
                    "SELECT c FROM Customer c WHERE c.email = :email and c.isDelete = false",
                    Customer.class
            );
            query.setParameter("email", email);
            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null; 
            }
        } finally {
            em.close();
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.find(Customer.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Sử dụng getResultList để tránh NoResultException gây crash server
            List<Customer> customers = entityManager.createQuery(
                    "SELECT c FROM Customer c WHERE c.email = :email and c.isDelete != true ", Customer.class)
                    .setParameter("email", email)
                    .getResultList();
            
            if (customers != null && !customers.isEmpty()) {
                return customers.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            List<Customer> customers = entityManager.createQuery(
                    "SELECT c FROM Customer c WHERE c.phone = :phone and c.isDelete != true ", Customer.class)
                    .setParameter("phone", phone)
                    .getResultList();
            
            if (customers != null && !customers.isEmpty()) {
                return customers.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Customer c where c.isDelete = false ", Customer.class)
                                .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean insert(Customer customer) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean update(Customer customer) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(customer);
            entityManager.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class, id);
            if (customer != null) {
                customer.setIsDelete(true);
            }
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Customer> search(String keyword) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Customer c WHERE c.name LIKE :keyword and c.isDelete = false ", Customer.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Customer> findAll(int pageNo, int pageSize) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Customer c where c.isDelete = false ", Customer.class)
                    .setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static void main(String[] args) {
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        // Kiểm tra thử một email có trong DB
        Customer customer = customerDAO.getCustomerByEmail("phucka004@gmail.com");
        if (customer != null) {
            System.out.println("Tìm thấy: " + customer.getEmail());
        } else {
            System.out.println("Không tìm thấy khách hàng này.");
        }
    }
}