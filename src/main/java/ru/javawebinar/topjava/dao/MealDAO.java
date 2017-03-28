package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDAO {
    public List<Meal> listMeals();
    public void delete(Long id);
    public Meal getMealById(Long id);
    public void updateOrInsertMeal(Long id, LocalDateTime dateTime, String description, int calories);
}
