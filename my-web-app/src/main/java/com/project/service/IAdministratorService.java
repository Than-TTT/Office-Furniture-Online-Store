package com.project.service;
import com.project.entity.Administrator;

public interface IAdministratorService {
    Administrator login(String username, String password);
}