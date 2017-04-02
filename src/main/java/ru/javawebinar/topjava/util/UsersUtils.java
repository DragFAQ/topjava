package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Drag on 02.04.2017.
 */
public class UsersUtils {
    public static final List<User> USERS = Arrays.asList(
            new User("Admin", "admin@gmail.com", "admin", true, Role.ROLE_USER, Role.ROLE_ADMIN),
            new User("someName", "somename@email.com", "somename", true, Role.ROLE_USER),
            new User("test", "asvlogan@mail.ru", "asvlogan", true, Role.ROLE_USER),
            new User("User", "user@yandex.ru", "user", true, Role.ROLE_USER),
            new User("user1", "user1@gmail.com", "user1", true, Role.ROLE_USER),
            new User("user2", "user2@gmail.com", "user2", false, Role.ROLE_USER)
            );

}
