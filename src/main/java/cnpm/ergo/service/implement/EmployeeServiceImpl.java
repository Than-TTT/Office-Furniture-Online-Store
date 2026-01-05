package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.EmployeeDAOImpl;
import cnpm.ergo.DAO.interfaces.IEmployeeDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Employee;
import cnpm.ergo.entity.Role;
import cnpm.ergo.service.interfaces.IEmployeeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class EmployeeServiceImpl implements IEmployeeService {
    
    // Khai báo duy nhất một thực thể DAO để dùng chung, tránh lãng phí bộ nhớ
    private final IEmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Override
    public void insert(Employee employee) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            // Bước 1: Chỉ lấy Role ra (Không mở Transaction ở đây để tránh treo)
            Role staffRole = null;
            try {
                staffRole = em.createQuery("SELECT r FROM Role r WHERE r.roleName = :name", Role.class)
                        .setParameter("name", "Staff")
                        .getSingleResult();
            } catch (NoResultException nre) {
                System.err.println("LỖI: Không tìm thấy Role 'Staff' trong Database!");
            }

            // Bước 2: Gán Role cho nhân viên
            if (staffRole != null) {
                employee.setRole(staffRole);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Bước 3: ĐÓNG EntityManager ngay lập tức để giải phóng Connection
            if (em.isOpen()) {
                em.close();
            }
        }

        // Bước 4: Sau khi đã đóng EM ở Service, mới gọi DAO để thực hiện Insert
        // Việc này giúp DAO tự mở Transaction riêng mà không bị xung đột
        employeeDAO.insert(employee);
    }

    @Override
    public List<Employee> findAll(int pageNo, int pageSize) {
        return employeeDAO.findAll(pageNo, pageSize);
    }

    @Override
    public void update(Employee employee) {
        employeeDAO.update(employee);
    }

    @Override
    public void delete(int employeeId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, employeeId);
            if (employee != null) {
                em.remove(employee);
                System.out.println("DAO: Đã xóa nhân viên ID " + employeeId);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    @Override
    public Employee findById(int employeeId) {
        return employeeDAO.findById(employeeId);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public List<Employee> searchByName(String name) {
        return employeeDAO.searchByName(name);
    }

    @Override
    public int count() {
        return employeeDAO.count();
    }

    @Override
    public boolean login(String email, String password) {
        Employee employee = employeeDAO.getEmployee(email);
        if (employee != null) {
            // Match password thô (Bạn nên cân nhắc dùng BCrypt sau này)
            return employee.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public Employee getEmployee(String email) {
        return employeeDAO.getEmployee(email);
    }

    public static void main(String[] args) {
        IEmployeeService employeeService = new EmployeeServiceImpl();
        // Kiểm tra logic login
        if (employeeService.login("admin@gmail.com", "12345")) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }
}