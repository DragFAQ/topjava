package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATA_JPA)

public class DataJPAUserServiceTest extends UserServiceTest {
    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MEALS, user.getMeals());
    }
}
