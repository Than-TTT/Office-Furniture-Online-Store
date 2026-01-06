package cnpm.ergo.DAO.interfaces;

import java.util.List;

import cnpm.ergo.entity.Administrator;
import cnpm.ergo.entity.Employee;

public interface IEmployeeDAO {
    void insert(Employee employee);
    void update(Employee employee);
    void delete(int employeeId);
    public Employee getEmployee(String email);
    Employee findById(int employeeId);
    List<Employee> findAll();
    List<Employee> searchByName(String name);
    List<Employee> findAll(int pageNo, int pageSize);
    int count();
}
