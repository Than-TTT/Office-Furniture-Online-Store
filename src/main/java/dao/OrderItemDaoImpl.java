package dao;

import jakarta.persistence.*;
import model.OrderItem;
import java.util.List;

public class OrderItemDaoImpl implements IOrderItemDao {
    
    private static EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("EcommercePU");

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId", 
                OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(OrderItem.class, orderItemId);
        } finally {
            em.close();
        }
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(orderItem);
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