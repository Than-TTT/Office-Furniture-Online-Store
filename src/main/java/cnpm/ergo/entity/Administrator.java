package cnpm.ergo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQuery(name = "Administrator.findAll", query = "SELECT a FROM Administrator a where a.isDelete = false")
@PrimaryKeyJoinColumn(name = "administratorId")

public class Administrator extends User{

}
