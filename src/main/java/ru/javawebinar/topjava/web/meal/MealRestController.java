package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public void save(HttpServletRequest request) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        service.save(id.isEmpty() ? null : Integer.valueOf(id),
                AuthorizedUser.id(),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
    }

    public void delete(HttpServletRequest request) throws ServletException, IOException {
        int id = getId(request);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(HttpServletRequest request) {
        int id = getId(request);
        return service.get(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.id());
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}