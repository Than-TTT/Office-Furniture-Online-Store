package cnpm.ergo.DAO.interfaces;

import cnpm.ergo.entity.Administrator;

public interface IAdministratorDAO {
    public Administrator getAdministrator(String email);
}
