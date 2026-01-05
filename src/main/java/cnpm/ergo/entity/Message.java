package cnpm.ergo.entity;

import java.util.Date;

import cnpm.ergo.configs.JPAConfig;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Message")
@NamedQuery(name = "Message.findAll", query = "SELECT c FROM Message c")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messsageId")
    private int messageId;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "timestamp", columnDefinition = "DATETIME")
    private Date timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conversationId", referencedColumnName = "conversationId")
    private Conversation conversation;

    public static void main(String[] args) {
        // insert employee
        EntityManager entityManager = JPAConfig.getEntityManager();
        entityManager.getTransaction().begin();

        //Get customer by id
        Customer customer = entityManager.find(Customer.class, 1);

        //Get employee by id
        Employee employee = entityManager.find(Employee.class, 3);

        //Get conversation
        Conversation conversation = entityManager.find(Conversation.class, 1);

        //Create new message
        Message message = new Message();
        message.setContent("Hello");
        message.setTimestamp(new Date());
        message.setSender(customer);
        message.setConversation(conversation);
        entityManager.persist(message);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}