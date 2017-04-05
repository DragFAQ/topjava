package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    void save(Integer id, int userId, LocalDateTime dateTime, String description, Integer calories);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    List<MealWithExceed> getAll(int userId);
}