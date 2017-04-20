package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            Meal dbMeal = em.find(Meal.class, meal.getId());
            if (dbMeal != null && dbMeal.getUser().getId() == userId) {
                dbMeal.setDateTime(meal.getDateTime());
                dbMeal.setDescription(meal.getDescription());
                dbMeal.setCalories(meal.getCalories());
                return em.merge(dbMeal);
            }
            else
                return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em
                .createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal dbMeal = em.find(Meal.class, id);
        if (dbMeal != null && dbMeal.getUser().getId() == userId)
            return dbMeal;
        else
            return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em
                .createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em
                .createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("start_date", startDate)
                .setParameter("end_date", endDate)
                .getResultList();
    }
}