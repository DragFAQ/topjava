package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.javawebinar.topjava.Profiles;
import java.sql.Timestamp;

import java.time.LocalDateTime;

@Configuration
@Profile(Profiles.HSQL_DB)
public class DateTimeForHSQL implements DateTimeAdapter<Timestamp> {
    public Timestamp getDateTime(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}
