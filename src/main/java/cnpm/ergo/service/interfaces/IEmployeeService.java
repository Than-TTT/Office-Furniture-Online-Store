package cnpm.ergo.service.interfaces;

import cnpm.ergo.entity.Administrator;
import cnpm.ergo.entity.Employee;
import java.util.List;

public interface IEmployeeService {
    void insert(Employee employee);
    void update(Employee employee);
    void delete(int employeeId);
    Employee findById(int employeeId);
    List<Employee> findAll();
    List<Employee> searchByName(String name);
    List<Employee> findAll(int pageNo, int pageSize);
    public boolean login(String email, String password);
    Employee getEmployee(String email);
    int count();
}
