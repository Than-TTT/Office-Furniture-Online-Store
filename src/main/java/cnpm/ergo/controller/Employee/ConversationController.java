package cnpm.ergo.controller.Employee;

import cnpm.ergo.DAO.implement.ConversationDAOImpl;
import cnpm.ergo.DAO.implement.MessageDAOImpl;
import cnpm.ergo.DAO.implement.QuestionDAOImpl;
import cnpm.ergo.entity.Conversation;
import cnpm.ergo.entity.Employee;
import cnpm.ergo.entity.Message;
import cnpm.ergo.entity.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/employee/chat")
public class ConversationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ConversationDAOImpl conversationDAO = new ConversationDAOImpl();
    private QuestionDAOImpl questionDAO = new QuestionDAOImpl();
    private MessageDAOImpl messageDAO = new MessageDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") == null) {
            resp.sendRedirect(req.getContextPath() + "/employee/login");
            return;
        }

        int questionId = Integer.parseInt(req.getParameter("questionId"));
        //cập nhật Pending
        Question question =  questionDAO.findById(questionId);
        question.setPending(false);
        questionDAO.update(question);
        //Tạo mới một conversation
        Conversation conversation = new Conversation();
        conversation.setCustomer(question.getCustomer());
        Employee employee = new Employee();
        employee.setUserId(4);
        conversation.setEmployee(employee);
        conversationDAO.insert(conversation);
        //Lấy Conversation vừa tạo
        Conversation conversationNew = conversationDAO.findByEmployee_EmployeeIdAndCustomer_CustomerId(4, question.getCustomer().getUserId());
        Message message = new Message();
        message.setConversation(conversationNew);
        message.setContent(question.getContent());
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        message.setTimestamp(timestamp);
        message.setSender(question.getCustomer());
        messageDAO.insert(message);
        List<Message> messageList = new ArrayList<>();
        messageList = messageDAO.findByConversationId(conversationNew.getConversationId());
        req.setAttribute("messages", messageList);
        req.getRequestDispatcher("/employee/views/box-chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        String employeeId = req.getParameter("employeeId");
        String customerId = req.getParameter("customerId");
        Message message = new Message();
        message.setContent(content);
        message.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        //chỗ này truyền mã nhân viên dô
        Employee employee = new Employee();
        employee.setUserId(4);
        message.setSender(employee);
        Conversation conversationOld = conversationDAO.findByEmployee_EmployeeIdAndCustomer_CustomerId(4, Integer.parseInt(customerId));
        message.setConversation(conversationOld);
        messageDAO.insert(message);
        List<Message> messageList = new ArrayList<>();
        messageList = messageDAO.findByConversationId(conversationOld.getConversationId());
        req.setAttribute("messages", messageList);
        req.getRequestDispatcher("/employee/views/box-chat.jsp").forward(req, resp);
    }
}
