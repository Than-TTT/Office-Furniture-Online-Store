package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.EmployeeDAOImpl;
import cnpm.ergo.DAO.interfaces.IEmployeeDAO;
import cnpm.ergo.entity.Employee;
import cnpm.ergo.service.interfaces.IEmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements IEmployeeService {
	private IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
    @Override
    public List<Employee> findAll(int pageNo, int pageSize) {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the findAll method to fetch all employees
        return employeeDAO.findAll(pageNo, pageSize);
    }
    @Override
    public void insert(Employee employee) {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the insert method of EmployeeDAOImpl to save the employee
        employeeDAO.insert(employee);
    }

    @Override
    public void update(Employee employee) {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the update method of EmployeeDAOImpl to update the employee details
        employeeDAO.update(employee);
    }

    @Override
    public void delete(int employeeId) {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the delete method of EmployeeDAOImpl to remove the employee by id
        employeeDAO.delete(employeeId);
    }

    @Override
    public Employee findById(int employeeId) {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the findById method to fetch an employee by their ID
        return employeeDAO.findById(employeeId);
    }

    @Override
    public List<Employee> findAll() {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the findAll method to fetch all employees
        return employeeDAO.findAll();
    }

    @Override
    public List<Employee> searchByName(String name) {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the searchByName method to fetch employees whose name contains the search string
        return employeeDAO.searchByName(name);
    }

    @Override
    public int count() {
        // Create a new instance of the EmployeeDAOImpl
        IEmployeeDAO employeeDAO = new EmployeeDAOImpl();
        // Call the count method to return the number of employees in the database
        return employeeDAO.count();
    }
	@Override
	public boolean login(String email, String password) {
		Employee employee = employeeDAO.getEmployee(email);
        if (employee != null) {
            return employee.getPassword().equals(password);
        }
        return false;
	}
	@Override
	public Employee getEmployee(String email) {
		 return employeeDAO.getEmployee(email);
	}
    public static void main(String[] args) {
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        if (employeeService.login("anhQuang@gmail.com", "12345")) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }
}
