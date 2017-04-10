package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2017, Month.APRIL, 13, 10, 10), "aaa", 250);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.of(2017, Month.APRIL, 13, 12, 10), "bbb", 350);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
            (Objects.equals(expected.getCalories(), actual.getCalories())
            && Objects.equals(expected.getId(), actual.getId())
            && Objects.equals(expected.getDateTime(), actual.getDateTime())
            && Objects.equals(expected.getDescription(), actual.getDescription())
            )
            );

}
