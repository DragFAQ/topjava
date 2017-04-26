package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;

@Configuration
@Profile(Profiles.POSTGRES_DB)
public class DateTimeForDB implements DateTimeAdapter<LocalDateTime> {
    @Override
    public LocalDateTime getDateTime(LocalDateTime dateTime) {
        return dateTime;
    }
}
