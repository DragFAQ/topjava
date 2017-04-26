package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;

public interface DateTimeAdapter<T> {
    T getDateTime(LocalDateTime dateTime);
}
