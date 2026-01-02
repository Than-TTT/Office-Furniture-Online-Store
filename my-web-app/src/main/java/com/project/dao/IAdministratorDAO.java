package com.project.dao;
import com.project.entity.Administrator;

public interface IAdministratorDAO {
    Administrator findByUsername(String username);
}