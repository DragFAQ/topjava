package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceImpl.class);

    @Autowired
    private MealRepository repository;

    @Override
    public void save(Integer id, int userId, LocalDateTime dateTime, String description, Integer calories) {
        if (id != null)
            if (repository.get(id).getUserId() != userId) {
                throw new NotFoundException("Update ERROR! Wrong user for mealId " + id);
            }

        Meal meal = new Meal(userId, dateTime, description, calories);

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
            throw new NotFoundException("Delete ERROR! Wrong user for mealId " + id);
        }

        LOG.info("Delete {}", id);
        repository.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
            throw new NotFoundException("Get ERROR! Wrong user for mealId " + id);
        } else {
            return repository.get(id);
        }
    }

    @Override
    public List<MealWithExceed> getAll(int userId) {
        return MealsUtil.getWithExceeded(repository.getByUser(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}