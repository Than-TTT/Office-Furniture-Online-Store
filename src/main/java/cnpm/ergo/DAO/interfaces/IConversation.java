package cnpm.ergo.DAO.interfaces;

import java.util.List;

import cnpm.ergo.entity.Conversation;

public interface IConversation {

    void delete(int conversationId);

    void update(Conversation conversation);

    void insert(Conversation conversation);

    int count();

    List<Conversation> findAll();

    Conversation findById(int conversationId);
    Conversation findByEmployee_EmployeeIdAndCustomer_CustomerId(int employeeId, int customerId);

}