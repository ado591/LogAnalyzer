package ru.angela.NauJava.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.angela.NauJava.entities.Level;
import ru.angela.NauJava.entities.Log;
import ru.angela.NauJava.services.LogService;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommandProcessor {
    private Scanner scanner = new Scanner(System.in);
    private final LogService logService;

    @Autowired
    public CommandProcessor(LogService logService)
    {
        this.logService = logService;
    }

    public void processCommand(String command) {

        switch (command) {
            case "create":
                System.out.println("Введите лог в формате \"ID message=\"message\" User-Agent=\"User-Agent\" level\"");
                String data = scanner.nextLine();
                try {
                    Log newLog = parseLog(data);
                    if (logService.findById(newLog.getId()) == null) {
                        logService.addLog(newLog);
                    } else {
                        System.out.println("Лог с таким id уже существует");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case "find":
                System.out.print("Введите ID лога: ");
                Log log;
                try {
                    Long id = Long.parseLong(scanner.nextLine());
                    if (id < 0) {
                        throw new IllegalArgumentException();
                    }
                    log = logService.findById(id);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверный формат id");
                    break;
                }
                System.out.println(log != null ? log : "Лог не найден.");
                break;

            case "findAll":
                List<Log> logs = logService.findAll();
                for (int i = 0; i < logs.size(); i++) {
                    System.out.println(logs.get(i));
                }
                break;

            case "findByLevel":
                System.out.print("Введите уровень (например, INFO, ERROR): ");
                Level level;
                try {
                    level = Level.valueOf(scanner.nextLine().toUpperCase());
                    List<Log> logsByLevel = logService.findByLevel(level);
                    for (int i = 0; i < logsByLevel.size(); i++) {
                        System.out.println(logsByLevel.get(i));
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Не найден такой уровень логирования. Доступные для поиска уровни: INFO, ERROR, DEBUG, FATAL, WARNING");
                }
                break;

            case "sortByTime":
                List<Log> sortedLogs = logService.sortByTime();
                for (int i = 0; i < sortedLogs.size(); i++) {
                    System.out.println(sortedLogs.get(i));
                }
                break;

            case "delete":
                System.out.print("Введите ID лога для удаления: ");
                try {
                    Long deleteId = Long.parseLong(scanner.nextLine());
                    if (logService.findById(deleteId) == null) {
                        System.out.println("Лог не найден");
                        break;
                    }
                    logService.deleteById(deleteId);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверный формат id");
                    break;
                }
                System.out.println("Лог удален.");
                break;

            case "statistics":
                logService.printStatistics();
                break;

            case "update":
                System.out.print("Введите ID лога для обновления: ");
                Long updateId;
                try {
                    updateId = Long.parseLong(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверный формат id");
                    break;
                }
                if (logService.findById(updateId) == null) {
                    System.out.println("Лог не найден");
                    break;
                }
                System.out.print("Введите новый User-Agent: ");
                String userAgent = scanner.nextLine();
                logService.updateUserAgent(updateId, userAgent);
                System.out.println("User-Agent обновлен.");
                break;

            case "exit":
                System.out.println("Выход из программы.");
                scanner.close();
                return;

            default:
                System.out.println("Неизвестная команда. Попробуйте еще раз.");
        }
    }

    private Log parseLog(String logLine) {
        String regex = "(\\d+) message=\"([^\"]+)\" User-Agent=\"([^\"]+)\" (\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(logLine);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Неверный формат строки лога");
        }

        try {
            Long id = Long.parseLong(matcher.group(1));
            String message = matcher.group(2);
            String userAgent = matcher.group(3);
            Level level = Level.valueOf(matcher.group(4));

            return new Log(id, message, userAgent, level);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            throw new IllegalArgumentException(STR."Ошибка при парсинге лога: \{e.getMessage()}");
        }
    }
}
