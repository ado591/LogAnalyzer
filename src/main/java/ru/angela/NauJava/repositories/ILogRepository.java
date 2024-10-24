package ru.angela.NauJava.repositories;

import java.util.List;

public interface ILogRepository<T, ID> {
    void create(T entity);

    T read(ID id);

    List<T> readAll();

    void update(T entity);

    void delete(ID id);
}
