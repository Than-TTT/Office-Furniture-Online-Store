package cnpm.ergo.entity;

import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Customer;
import cnpm.ergo.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TestDB {
	public static void main(String[] args) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Tạo Customer giả để test
	        Customer c = new Customer();
	        c.setName("Test Debug");
	        c.setEmail("debug" + System.currentTimeMillis() + "@gmail.com");
	        c.setPassword("123");
	        c.setIsDelete(false);
	        
	        // Quan trọng: Phải gán Role hiện có trong DB (ví dụ ID 1)
	        Role r = em.find(Role.class, 1); 
	        c.setRole(r);

	        em.persist(c);
	        em.getTransaction().commit(); // Dòng này cực kỳ quan trọng
	        System.out.println("LƯU THÀNH CÔNG!");
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}
}
