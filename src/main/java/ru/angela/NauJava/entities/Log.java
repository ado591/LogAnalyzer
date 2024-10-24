package ru.angela.NauJava.entities;

import java.time.ZonedDateTime;
import java.util.Comparator;

public class Log {
    private Long id;
    private String message;
    private String userAgent;
    private Level level;
    private ZonedDateTime timestamp;


    public Log(Long id, String message, String userAgent, Level level) {
        this.id = id;
        this.message = message;
        this.userAgent = userAgent;
        this.level = level;
        this.timestamp = ZonedDateTime.now();
    }

    public Log() {
        this.id = 100500L;
        this.message = "Это сообщение лога";
        this.userAgent = "Нет данных";
        this.level = Level.INFO;
        this.timestamp = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return STR."id=\{id}, message='\{message}\{'\''}, User-Agent='\{userAgent}\{'\''}, level=\{level}, timestamp=\{timestamp}\{'}'}";
    }

    public static Comparator<Log> comparator = new Comparator<Log>() {
        @Override
        public int compare(Log o1, Log o2) {
            int timeComparison = o1.timestamp.compareTo(o2.timestamp);
            if (timeComparison != 0) {
                return timeComparison;
            }
            int idComparison = Long.compare(o1.id, o2.id);
            return idComparison;
        }
    };
}
