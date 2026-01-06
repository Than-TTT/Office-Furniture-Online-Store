package cnpm.ergo.DAO.implement;

import cnpm.ergo.DAO.interfaces.IRoleDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Role;

import jakarta.persistence.EntityManager;

import java.util.List;

public class RoleDAOImpl implements IRoleDAO {
    @Override
    public boolean addRole(Role role) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(role); // Thêm mới role vào database
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
    @Override
    public List<Role> getAllRoles() {
        EntityManager em = JPAConfig.getEntityManager();
        List<Role> roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
        em.close();
        return roles;
    }
    @Override
    public Role getRoleById(int roleId) {
        EntityManager em = JPAConfig.getEntityManager();
        Role role = em.find(Role.class, roleId);
        em.close();
        return role;
    }
    @Override
    public boolean updateRole(Role role) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(role); // Cập nhật role vào database
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
    @Override
    public boolean deleteRole(int roleId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Role role = em.find(Role.class, roleId);
            if (role != null) {
                em.remove(role); // Xóa role khỏi database
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
    public static void main(String[] args) {
        IRoleDAO IRoleDAO = new RoleDAOImpl();
        Role role = new Role();
        role.setRoleName("Manager");
        IRoleDAO.addRole(role);

        List<Role> roles = IRoleDAO.getAllRoles();
        for (Role r : roles) {
            System.out.println(r.getRoleId() + " - " + r.getRoleName());
        }

        Role role2 = IRoleDAO.getRoleById(1);
        if (role2 != null) {
            System.out.println(role2.getRoleId() + " - " + role2.getRoleName());
            role2.setRoleName("Admin");
            IRoleDAO.updateRole(role2);
        }
        IRoleDAO.deleteRole(1);
        roles = IRoleDAO.getAllRoles();
        for (Role r : roles) {
            System.out.println(r.getRoleId() + " - " + r.getRoleName());
        }
    }
}
