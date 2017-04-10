package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() throws Exception {
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2), service.getAll(USER_ID));

    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserDelete() throws Exception {
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        Collection<Meal> meals = service.getBetweenDates(
                LocalDate.of(2017, Month.APRIL, 13),
                LocalDate.of(2017, Month.APRIL, 13), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1), meals);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> meals = service.getBetweenDateTimes(
                LocalDateTime.of(2017, Month.APRIL, 13, 9, 0),
                LocalDateTime.of(2017, Month.APRIL, 13, 11, 0), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL1), meals);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1), all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDescr");
        updated.setCalories(330);
        updated.setDateTime(LocalDateTime.of(2017, Month.APRIL, 1, 14, 0));
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDescr");
        updated.setCalories(330);
        updated.setDateTime(LocalDateTime.of(2017, Month.APRIL, 1, 14, 0));
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2017, Month.APRIL, 1, 14, 0), "some", 50);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1, newMeal), service.getAll(USER_ID));
    }

}