package ru.job4j.dream.servlet;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Class UserSendServlet
 * Сервлет принимает и отправляет данные в JSON формате.
 * @author Dmitry Razumov
 * @version 1
 */
public class UserSendServlet extends HttpServlet {
    /**
     * Поле создает логер.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserSendServlet.class.getName());

    /**
     * Метод принимает и отправляет данные в формате JSON.
     * @param req HTTP запрос.
     * @param resp HTTP ответ.
     * @throws ServletException Исключение
     * @throws IOException Исключение
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
            json = br.readLine();
            LOG.info("Получены данные: {}", json);
        } catch (IOException ex) {
            LOG.error("Ошибка получения данных.", ex);
        }
        JSONObject jsonData = new JSONObject(json);
        PrintWriter out = resp.getWriter();
        out.print(jsonData);
        out.flush();
    }
}
