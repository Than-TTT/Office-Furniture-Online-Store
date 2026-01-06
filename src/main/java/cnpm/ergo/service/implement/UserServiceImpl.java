package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.UserDAOImpl;
import cnpm.ergo.DAO.interfaces.IUserDAO;
import cnpm.ergo.entity.User;
import cnpm.ergo.service.interfaces.IUserService;

import java.util.List;

public class UserServiceImpl implements IUserService {
    private IUserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean addUser(User user) {
        try {
            return userDAO.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public User getUserById(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try {
            return userDAO.deleteUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCustomerPassword(String email, String newPassword) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                user.setPassword(newPassword);
                return userDAO.updateUser(user);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean getUserByEmail(String email) {
        try {
            User user = userDAO.getUserByEmail(email);
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Thêm phương thức để lấy User object theo email
    public User findUserByEmail(String email) {
        try {
            return userDAO.getUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
