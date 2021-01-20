package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Class CandidateDeleteServlet
 * Сервлет выполняет удаление кандидата из БД.
 * А также удаляет картинку связанную с данным кандидатом из папки.
 * @author Dmitry Razumov
 * @version 1
 */
public class CandidateDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().delete(Integer.valueOf(req.getParameter("id")));
        String photoId = req.getParameter("photoid");
        for (File name : new File("images").listFiles()) {
            if (name.getName().substring(0, name.getName().indexOf(".")).equals(photoId)) {
                name.delete();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
