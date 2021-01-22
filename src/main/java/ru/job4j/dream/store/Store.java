package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

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
     * Метод возвращает всех пользователей из БД.
     * @return Список пользователей.
     */
    Collection<User> findAllUsers();

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
     * Метод сохраняет или обновляет пользователя в БД.
     * @param user Пользователь.
     */
    void save(User user);

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

    /**
     * Метод ищет пользователя в БД по его идентификатору.
     * @param id Идентификатор пользователя.
     * @return Пользователь.
     */
    User findUserById(int id);

    /**
     * Метод удаляет кандидата из БД.
     * @param id Идентификатор кандидата.
     */
    void deleteCandidate(int id);

    /**
     * Метод удаляет вакансию из БД.
     * @param id Идентификатор вакансии.
     */
    void deletePost(int id);

    /**
     * Метод удаляет пользователя из БД.
     * @param id Идентификатор пользователя.
     */
    void deleteUser(int id);
}