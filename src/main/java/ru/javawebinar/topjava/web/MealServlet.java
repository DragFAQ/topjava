package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static String INSERT_OR_EDIT = "meal.jsp";
    private static String LIST_MEAL = "meals.jsp";

    private MealDAO mealDAO;

    public MealServlet() {
        mealDAO = new MealDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action)) {
            LOG.debug("Delete item");
            Long id;
            id = Long.parseLong(request.getParameter("id"));
            mealDAO.delete(id);
            response.sendRedirect("meals");
            return;
        } else if ("edit".equalsIgnoreCase(action)) {
            LOG.debug("Edit item");
            forward = INSERT_OR_EDIT;
            Long id = Long.parseLong(request.getParameter("id"));
            Meal meal = mealDAO.getMealById(id);
            request.setAttribute("meal", meal);
        } else {
            LOG.debug("Get list");
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDAO.listMeals(), null, null, 2000));
            forward = LIST_MEAL;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String strId = request.getParameter("id");

        Long id = null;
        if(strId != null && !strId.isEmpty()) {
            id = Long.parseLong(strId);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);

        String description = request.getParameter("description");

        String strCalories = request.getParameter("calories");

        mealDAO.updateOrInsertMeal(id, dateTime, description, Integer.parseInt(strCalories));

        response.sendRedirect("meals");
    }
}
