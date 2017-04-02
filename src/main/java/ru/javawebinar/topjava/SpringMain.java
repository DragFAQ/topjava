package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            InMemoryUserRepositoryImpl dao = appCtx.getBean(InMemoryUserRepositoryImpl.class);
// repository tests
            System.out.println("All list");
            dao.getAll().stream().forEach(System.out::println);
            System.out.println("Delete 4");
            dao.delete(4);
            dao.getAll().stream().forEach(System.out::println);
            System.out.println("Find asvlogan@mail.ru");
            System.out.println(dao.getByEmail("asvlogan@mail.ru"));
            System.out.println("Add new user");
            dao.save(new User("bbb", "abs", "qwe", true, Role.ROLE_USER));
            dao.getAll().stream().forEach(System.out::println);

        }
    }
}
