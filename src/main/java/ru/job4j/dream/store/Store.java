package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;

/**
 * Interface Store
 * Интерфейс описывает взаимодействие с БД.
 * @author Dmitry Razumov
 * @version 1
 */
public interface Store {
    /**
     * Метод возвращает все объявления из БД.
     * @return Список вакансий.
     */
    Collection<Post> findAllPosts();

    /**
     * Метод возвращает все кандидатов из БД.
     * @return Список кандидатов.
     */
    Collection<Candidate> findAllCandidates();

    /**
     * Метод сохраняет или обновляет вакансию в БД
     * @param post Вакансия.
     */
    void save(Post post);

    /**
     * Метод сохраняет или обновляет кандидата в БД.
     * @param candidate Кандидат.
     */
    void save(Candidate candidate);

    /**
     * Метод ищет вакансию в БД по ее идентификатору.
     * @param id Идентификатор вакансии.
     * @return Вакансия.
     */
    Post findPostById(int id);

    /**
     * Метод ищет кандидата в БД по его идентификатору.
     * @param id Идентификатор кандидата.
     * @return Кандидат.
     */
    Candidate findCandidateById(int id);
}