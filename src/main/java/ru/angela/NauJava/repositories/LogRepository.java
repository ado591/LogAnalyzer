package ru.angela.NauJava.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.angela.NauJava.entities.Log;

import java.util.List;

@Component
public class LogRepository implements ILogRepository<Log, Long>{
    private final List<Log> logList;

    @Autowired
    public LogRepository(List<Log> homeworkContainer) {
        this.logList = homeworkContainer;
    }

    @Override
    public void create(Log log) {
        logList.add(log);
    }

    @Override
    public Log read(Long id) {
        for (Log log : logList) {
            if (log.getId().equals(id)) {
                return log;
            }
        }
        return null;
    }

    @Override
    public List<Log> readAll() {
        return logList;
    }

    @Override
    public void update(Log log) {
        for (int i = 0; i < logList.size(); i++) {
            if (logList.get(i).getId().equals(log.getId())) {
                logList.set(i, log);
                return;
            }
        }
        logList.add(log);
    }

    @Override
    public void delete(Long id) {
        for (Log log : logList) {
            if (log.getId().equals(id)) {
                logList.remove(log);
                return;
            }
        }
    }
}
