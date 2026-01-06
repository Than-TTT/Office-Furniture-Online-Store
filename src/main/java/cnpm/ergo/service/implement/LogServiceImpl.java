package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.LogDAOImpl;
import cnpm.ergo.DAO.implement.UserDAOImpl;
import cnpm.ergo.DAO.interfaces.ILogDAO;
import cnpm.ergo.entity.Log;
import cnpm.ergo.service.interfaces.ILogService;

public class LogServiceImpl implements ILogService {
    @Override
    public boolean addLog(Log log) {
        ILogDAO ILogDAO = new LogDAOImpl();
        return ILogDAO.addLog(log);
    }

    @Override
    public Log getLogById(int logId) {
        ILogDAO ILogDAO = new LogDAOImpl();
        return ILogDAO.getLogById(logId);
    }

    @Override
    public boolean updateLog(Log log) {
        ILogDAO ILogDAO = new LogDAOImpl();
        return ILogDAO.updateLog(log);
    }

    @Override
    public boolean deleteLog(int logId) {
        return false;
    }

    public static void main(String[] args) {
        ILogService ILogService = new LogServiceImpl();
        Log log = new Log();
        log.setContent("User login");
        log.setDateLog(java.time.LocalDateTime.now());
        log.setUser(new UserDAOImpl().getUserById(2));
        ILogService.addLog(log);

    }

}
