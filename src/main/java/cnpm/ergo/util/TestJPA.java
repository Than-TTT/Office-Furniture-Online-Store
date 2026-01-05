package cnpm.ergo.util;

import cnpm.ergo.configs.JPAConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TestJPA {
	public static void main(String[] args) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
        	trans.begin();
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			em.close();
		}
    }
}
