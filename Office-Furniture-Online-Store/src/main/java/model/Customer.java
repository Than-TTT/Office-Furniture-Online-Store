package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Customer")
public class Customer {
    
    @Id
    @Column(name = "customer_id")
    private int customerId;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private User user;

    // Constructor
    public Customer() {}

    public Customer(User user) {
        this.user = user;
        this.customerId = user.getUserId();
    }

    // Getters & Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    // Convenience methods
    public String getName() {
        return user != null ? user.getName() : null;
    }
    
    public String getEmail() {
        return user != null ? user.getEmail() : null;
    }
    
    public String getPhone() {
        return user != null ? user.getPhone() : null;
    }
    
    public String getAddress() {
        return user != null ? user.getAddress() : null;
    }
    
    public String getGender() {
        return user != null ? user.getGender() : null;
    }
}