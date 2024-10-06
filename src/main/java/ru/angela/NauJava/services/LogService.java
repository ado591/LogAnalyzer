package ru.angela.NauJava.services;

import ru.angela.NauJava.entities.Level;
import ru.angela.NauJava.entities.Log;

import java.util.List;

public interface LogService {

    /**
     * Добавляет лог
     */
    void addLog(Log log);

    /**
     * Выводит лог по заданному id
     */
    Log findById(Long id);

    List<Log> findAll();

    /**
     *
     * Выводит все логи указанного типа
     */
    List<Log> findByLevel(Level level);
    /**
     * Сортирует логи от самых свежих к самым старым
     */
    List<Log> sortByTime();

    /**
     * Удаляет лог по id
     */
    void deleteById(Long id);

    /**
     * Выводит статистику по логам: время первого и последнего лога
     * TODO: сделать статистику по каждому типу логов, UserAgent-ам и пр
     */
    void printStatistics();

    /**
     * Обновляет User Agent у лога
     */
    void updateUserAgent(Long id, String userAgent);
}
