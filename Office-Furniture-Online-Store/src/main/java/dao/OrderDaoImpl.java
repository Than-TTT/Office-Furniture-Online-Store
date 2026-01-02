package dao;

import jakarta.persistence.*;
import model.Order;
import java.util.List;

public class OrderDaoImpl implements IOrderDao {
    
    private static EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("EcommercePU");

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Order> orders = em.createQuery(
                "SELECT DISTINCT o FROM Order o " +
                "LEFT JOIN FETCH o.orderItems oi " +
                "LEFT JOIN FETCH oi.product " +
                "WHERE o.customerId = :customerId " +
                "ORDER BY o.orderDate DESC", 
                Order.class)
                .setParameter("customerId", customerId)
                .getResultList();
            return orders;
        } finally {
            em.close();
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        EntityManager em = emf.createEntityManager();
        try {
            Order order = em.createQuery(
                "SELECT DISTINCT o FROM Order o " +
                "LEFT JOIN FETCH o.orderItems oi " +
                "LEFT JOIN FETCH oi.product " +
                "WHERE o.orderId = :orderId", 
                Order.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
            return order;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order> getOrdersByCustomerIdAndStatus(int customerId, String status) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Order> orders = em.createQuery(
                "SELECT DISTINCT o FROM Order o " +
                "LEFT JOIN FETCH o.orderItems oi " +
                "LEFT JOIN FETCH oi.product " +
                "WHERE o.customerId = :customerId AND o.status = :status " +
                "ORDER BY o.orderDate DESC", 
                Order.class)
                .setParameter("customerId", customerId)
                .setParameter("status", status)
                .getResultList();
            return orders;
        } finally {
            em.close();
        }
    }

    @Override
    public void createOrder(Order order) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Order order = em.find(Order.class, orderId);
            if (order != null) {
                order.setStatus(status);
                em.merge(order);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}