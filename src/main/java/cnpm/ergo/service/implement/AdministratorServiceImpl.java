package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.AdministratorDAOImpl;
import cnpm.ergo.DAO.interfaces.IAdministratorDAO;
import cnpm.ergo.entity.Administrator;
import cnpm.ergo.service.interfaces.IAdministratorService;


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
        AdministratorServiceImpl administratorService = new AdministratorServiceImpl();
        if (administratorService.login("anhQuang@gmail.com", "12345")) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }
}
