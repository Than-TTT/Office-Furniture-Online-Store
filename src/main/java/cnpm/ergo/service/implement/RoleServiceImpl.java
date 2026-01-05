package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.RoleDAOImpl;
import cnpm.ergo.DAO.interfaces.IRoleDAO;
import cnpm.ergo.service.interfaces.IRoleService;
import cnpm.ergo.entity.Role;
import java.util.List;

public class RoleServiceImpl implements IRoleService {
    @Override
    public boolean addRole(Role role) {
        IRoleDAO IRoleDAO = new RoleDAOImpl();
        return IRoleDAO.addRole(role);
    }

    @Override
    public List<Role> getAllRoles() {
        IRoleDAO IRoleDAO = new RoleDAOImpl();
        return IRoleDAO.getAllRoles();
    }

    @Override
    public Role getRoleById(int roleId)
    {
        IRoleDAO IRoleDAO = new RoleDAOImpl();
        return IRoleDAO.getRoleById(roleId);
    }

    @Override
    public boolean updateRole(Role role) {
        IRoleDAO IRoleDAO = new RoleDAOImpl();
        return IRoleDAO.updateRole(role);
    }

    @Override
    public boolean deleteRole(int roleId) {
        IRoleDAO IRoleDAO = new RoleDAOImpl();
        return IRoleDAO.deleteRole(roleId);
    }

    public static void main(String[] args) {
        IRoleService IRoleService = new RoleServiceImpl();
        Role role = new Role();
        role.setRoleName("Admin");
        IRoleService.addRole(role);

        List<Role> roles = IRoleService.getAllRoles();
        for (Role r : roles) {
            System.out.println(r.getRoleId() + " - " + r.getRoleName());
        }
    }

}
