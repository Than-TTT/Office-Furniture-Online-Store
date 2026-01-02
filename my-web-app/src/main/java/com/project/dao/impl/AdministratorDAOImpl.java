package com.project.dao.impl;
import com.project.dao.IAdministratorDAO;
import com.project.entity.Administrator;

public class AdministratorDAOImpl implements IAdministratorDAO {
    @Override
    public Administrator findByUsername(String username) {
        // Sau này thay đoạn này bằng code kết nối SQL Server/MySQL
        if ("admin".equals(username)) {
            return new Administrator("admin", "123456");
        }
        return null;
    }
}