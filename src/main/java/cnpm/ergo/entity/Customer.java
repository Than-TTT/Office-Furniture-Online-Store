package cnpm.ergo.entity;

import java.util.List;

import cnpm.ergo.configs.JPAConfig;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.TypedQuery;

@Entity
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
@PrimaryKeyJoinColumn(name = "customerId")

public class Customer extends User{
    @OneToMany(mappedBy = "customer")
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "customer")
    private List<Question> questions;

    public static void main(String[] args) {
        //insert customer
//        EntityManager entityManager = JPAConfig.getEntityManager();
//        entityManager.getTransaction().begin();
//
//        Customer customer = new Customer();
//        customer.setName("userGender");
//        customer.setEmail("userEmail");
//        customer.setPassword("userPassword");
//        customer.setPhone("userPhone");
//
//        entityManager.persist(customer);
//        entityManager.getTransaction().commit();
//        entityManager.close();
        /////////////////
    	EntityManager entityManager = JPAConfig.getEntityManager();
        entityManager.getTransaction().begin();

        Customer customer = new Customer();

        entityManager.persist(customer);
        entityManager.getTransaction().commit();

        // Fetch all customers using NamedQuery
        TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findAll", Customer.class);
        List<Customer> customers = query.getResultList();
        // Print the list of customers
        for (Customer c : customers) {
            System.out.println(c.getName() + " - " + c.getEmail() + " - "  + c.getPassword());
        }
        entityManager.close();
    }

}
