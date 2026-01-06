package cnpm.ergo.DAO.interfaces;

import cnpm.ergo.entity.Log;

public interface ILogDAO {
    // Create
    boolean addLog(Log log);
    // Read
    Log getLogById(int logId);
    // Update
    boolean updateLog(Log log);
    // Delete
    boolean deleteLog(int logId);
}
