package cnpm.ergo.entity;


import jakarta.persistence.*;
import java.util.List;
@Entity
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e where e.isDelete = false")
@PrimaryKeyJoinColumn(name = "employeeId")

public class Employee extends User{
    @OneToMany(mappedBy = "employee")
    private List<Conversation> conversations;

}
