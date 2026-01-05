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
        IRoleService roleService = new RoleServiceImpl();
        
        // Tạo mảng tên các Role cần có
        String[] names = {"Admin", "Staff", "Customer"};
        
        for (String name : names) {
            Role r = new Role();
            r.setRoleName(name);
            roleService.addRole(r);
        }

        // In ra để xác nhận ID
        System.out.println("--- DANH SÁCH ROLE TRONG DB ---");
        List<Role> roles = roleService.getAllRoles();
        for (Role r : roles) {
            System.out.println("ID: " + r.getRoleId() + " | Tên: " + r.getRoleName());
        }
    }

}
