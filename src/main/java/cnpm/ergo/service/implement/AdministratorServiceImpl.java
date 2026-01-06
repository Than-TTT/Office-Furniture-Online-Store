package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.AdministratorDAOImpl;
import cnpm.ergo.DAO.interfaces.IAdministratorDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Administrator;
import cnpm.ergo.entity.Role;
import cnpm.ergo.service.interfaces.IAdministratorService;
import jakarta.persistence.EntityManager;


public class AdministratorServiceImpl implements IAdministratorService {
    private IAdministratorDAO administratorDAO = new AdministratorDAOImpl();

    @Override
    public boolean login(String email, String password) {
        Administrator administrator = administratorDAO.getAdministrator(email);
        if (administrator != null) {
            return administrator.getPassword().equals(password);
        }
        return false;
    }
    @Override
    public Administrator getAdministrator(String email) {
        return administratorDAO.getAdministrator(email);
    }

    public static void main(String[] args) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();

            Role adminRole = null;
            try {
                adminRole = em.createQuery("SELECT r FROM Role r WHERE r.roleName = :name", Role.class)
                              .setParameter("name", "Admin")
                              .getSingleResult();
            } catch (Exception e) {
                adminRole = new Role();
                adminRole.setRoleName("Admin");
                em.persist(adminRole);
            }

            Administrator admin = new Administrator();
            admin.setEmail("admin@gmail.com");
            admin.setPassword("12345"); 
            admin.setName("Trần Hà Đăng");
            admin.setRole(adminRole); 
            admin.setStatus("Active");
            admin.setIsDelete(false);

            // 3. Lưu xuống (Hibernate sẽ tự insert vào bảng user và administrator)
            em.persist(admin);

            em.getTransaction().commit();
            System.out.println("Tạo Admin thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
