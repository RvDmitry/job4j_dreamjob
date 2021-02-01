package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class PostDeleteServlet
 * Сервлет выполняет удаление вакансии из БД.
 * @author Dmitry Razumov
 * @version 1
 */
public class PostDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().deletePost(Integer.valueOf(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
