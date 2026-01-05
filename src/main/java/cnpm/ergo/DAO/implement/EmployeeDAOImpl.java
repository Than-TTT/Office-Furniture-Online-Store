package cnpm.ergo.DAO.implement;

import cnpm.ergo.DAO.interfaces.IEmployeeDAO;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmployeeDAOImpl implements IEmployeeDAO {

    @Override
    public List<Employee> findAll(int pageNo, int pageSize) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin a transaction
            entityManager.getTransaction().begin();

            // Create a query to find all employees
            TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAll", Employee.class);

            // Set the first result and max results for pagination
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);

            // Get the list of employees
            List<Employee> employees = query.getResultList();

            // Commit the transaction
            entityManager.getTransaction().commit();

            return employees;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public void insert(Employee employee) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void update(Employee employee) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin transaction
            entityManager.getTransaction().begin();

            // Merge the updated employee object
            entityManager.merge(employee);

            // Commit the transaction
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int employeeId) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin transaction
            entityManager.getTransaction().begin();

            // Find the employee by ID
            Employee employee = entityManager.find(Employee.class, employeeId);
            if (employee != null) {
                // Set isDelete to true
                employee.setIsDelete(true);
                // Merge the changes
                entityManager.merge(employee);
            }

            // Commit the transaction
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Employee findById(int employeeId) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Find employee by ID
            entityManager.getTransaction().begin();
            Employee employee = entityManager.find(Employee.class, employeeId);
            entityManager.getTransaction().commit();
            return employee;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Employee> findAll() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin a transaction
            entityManager.getTransaction().begin();

            // Use named query to find all employees
            TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAll", Employee.class);
            List<Employee> employees = query.getResultList();

            // Commit the transaction
            entityManager.getTransaction().commit();

            return employees;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Employee> searchByName(String name) {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin transaction
            entityManager.getTransaction().begin();

            // Create a query to search employees by name (case insensitive)
            List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(:name) and e.isDelete = false ", Employee.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();

            // Commit the transaction
            entityManager.getTransaction().commit();

            return employees;
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public int count() {
        EntityManager entityManager = JPAConfig.getEntityManager();
        try {
            // Begin transaction
            entityManager.getTransaction().begin();

            // Create a query to count the number of employees
            Long count = entityManager.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.isDelete = false", Long.class)
                    .getSingleResult();

            // Commit the transaction
            entityManager.getTransaction().commit();

            return count.intValue();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
	@Override
	public Employee getEmployee(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery(
                    "SELECT a FROM Employee a WHERE a.email = :email and a.isDelete = false",
                    Employee.class
            );
            query.setParameter("email", email);

            // Wrap getSingleResult in a try-catch block to handle NoResultException
            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null; // Return null if no result is found
            }
        } finally {
            em.close();
        }
    }
	public static void main(String[] args) {
	    EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

	    // Tạo đối tượng Employee mới
	    Employee newEmployee = new Employee();
	    newEmployee.setName("Nguyen Van A");
	    newEmployee.setEmail("nguyenvana@gmail.com");
	    newEmployee.setPassword("password123");
	    newEmployee.setIsDelete(false); // Đảm bảo rằng thuộc tính isDelete được đặt là false

	    // Thêm nhân viên mới vào cơ sở dữ liệu
	    employeeDAO.insert(newEmployee);

	    // Kiểm tra xem nhân viên đã được thêm thành công hay chưa
	    Employee insertedEmployee = employeeDAO.getEmployee("nguyenvana@gmail.com");
	    if (insertedEmployee != null) {
	        System.out.println("Employee added successfully.");
	        System.out.println("Name: " + insertedEmployee.getName());
	        System.out.println("Email: " + insertedEmployee.getEmail());
	    } else {
	        System.out.println("Failed to add employee.");
	    }
	}

}
