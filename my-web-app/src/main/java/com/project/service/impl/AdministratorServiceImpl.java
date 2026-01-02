package com.project.service.impl;

import com.project.dao.IAdministratorDAO;
import com.project.dao.impl.AdministratorDAOImpl;
import com.project.entity.Administrator;
import com.project.service.IAdministratorService;

public class AdministratorServiceImpl implements IAdministratorService {
    // Gọi DAO theo kiểu truyền thống (không dùng @Autowired của Spring)
    private IAdministratorDAO adminDAO = new AdministratorDAOImpl();

    @Override
    public Administrator login(String username, String password) {
        Administrator admin = adminDAO.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
