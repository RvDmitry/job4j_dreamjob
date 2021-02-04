package ru.job4j.dream.servlet;

import org.json.JSONObject;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class CityServlet
 * Сервлет загружает список городов из БД и отправляет в поток вывода.
 * @author Dmitry Razumov
 * @version 1
 */
public class CityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        List<City> cities = new ArrayList<>(PsqlStore.instOf().findAllCities());
        JSONObject json = new JSONObject();
        cities.forEach(city -> json.put(String.valueOf(city.getId()), city.getName()));
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
