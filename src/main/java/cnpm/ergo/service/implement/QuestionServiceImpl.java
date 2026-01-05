//package cnpm.ergo.service.implement;
//
//import java.util.List;
//
//import cnpm.ergo.DAO.implement.BlogDaoImpl;
//import cnpm.ergo.DAO.implement.QuestionDAOImpl;
//import cnpm.ergo.DAO.interfaces.IBlogDao;
//import cnpm.ergo.DAO.interfaces.IQuestion;
//import cnpm.ergo.entity.Blog;
//import cnpm.ergo.entity.Question;
//
//public class QuestionServiceImpl {
//	private final IQuestion questionDao = new QuestionDAOImpl();
//
//    @Override
//    public void addQuestion(Question question) {
//        questionDao.insert(question);
//    }
//
//    @Override
//    public void deleteQuestion(int questionId) {
//        try {
//            questionDao.delete(questionId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error deleting blog with ID: " + questionId);
//        }
//    }
//
//    @Override
//    public Question getQuestionById(int questionId) {
//        return questionDao.findById(questionId);
//    }
//
//    @Override
//    public List<Question> getAllQuestions() {
//        return questionDao.findAll();
//    }
//
//    @Override
//    public int getBlogCount() {
//        return questionDao.count();
//    }
//}
