package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.UserDAOImpl;
import cnpm.ergo.DAO.interfaces.IUserDAO;
import cnpm.ergo.entity.User;
import cnpm.ergo.service.interfaces.IUserService;

import java.util.List;

public class UserServiceImpl implements IUserService {

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
    }

    @Override
    public boolean updateCustomerPassword(String email, String newPassword) {
        return false;
    }

    @Override
    public boolean getUserByEmail(String email) {
        return false;
    }
}
