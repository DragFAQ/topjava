package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDAO {

    public static final AtomicLong lastId = new AtomicLong();

    public static Map<Long, Meal> dbTable;

    static {
        dbTable = new ConcurrentHashMap<>();
        Long id = lastId.incrementAndGet();
        dbTable.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        id = lastId.incrementAndGet();
        dbTable.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        id = lastId.incrementAndGet();
        dbTable.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        id = lastId.incrementAndGet();
        dbTable.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        id = lastId.incrementAndGet();
        dbTable.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        id = lastId.incrementAndGet();
        dbTable.put(id, new Meal(id, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    private static final Logger LOG = getLogger(MealDAO.class);

    public List<Meal> listMeals() {
        return new ArrayList<Meal>(dbTable.values());
    }

    public void delete(Long id) {
        dbTable.remove(id);
    }

    public Meal getMealById(Long id) {
        return dbTable.get(id);
    }

    public void updateOrInsertMeal(Long id, LocalDateTime dateTime, String description, int calories) {
        if (id == null)
            id = lastId.incrementAndGet();
        dbTable.put(id, new Meal(id, dateTime, description, calories));
    }
}
