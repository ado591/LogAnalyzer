package ru.angela.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.angela.NauJava.entities.Level;
import ru.angela.NauJava.entities.Log;
import ru.angela.NauJava.repositories.LogRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService{

    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void addLog(Log log){
        logRepository.create(log);
    };

    @Override
    public Log findById(Long id) {
        return logRepository.read(id);
    }

    @Override
    public List<Log> findAll() {
        return logRepository.readAll();
    }

    @Override
    public List<Log> findByLevel(Level level) {
        return logRepository.readAll().stream().filter(x -> x.getLevel() == level).toList();
    }

    @Override
    public List<Log> sortByTime() {
        return logRepository.readAll().stream().sorted().toList();
    }

    @Override
    public void deleteById(Long id) {
        logRepository.delete(id);
    }

    @Override
    public void printStatistics() {
        Optional<Log> oldestLog = logRepository.readAll().stream()
                .min(Comparator.comparing(Log::getTimestamp));

        Optional<Log> newestLog = logRepository.readAll().stream()
                .max(Comparator.comparing(Log::getTimestamp));
        System.out.println(STR."Самый старый лог: \{oldestLog}");
        System.out.println(STR."Самый свежий лог: \{newestLog}");
    }

    @Override
    public void updateUserAgent(Long id, String userAgent) {
        Log log = logRepository.read(id);
        if (log != null) {
            log.setUserAgent(userAgent);
            logRepository.update(log);
        }
    }
}
