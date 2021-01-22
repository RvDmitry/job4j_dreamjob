package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class Store
 * Класс описывает хранилище.
 * @author Dmitry Razumov
 * @version 1
 */
public class MemStore implements Store {
    /**
     * Синглтон, создает объект хранилища.
     */
    private static final MemStore INST = new MemStore();
    /**
     * Поле генерирует идентификатор объявления.
     */
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    /**
     * Поле генерирует идентификатор кандидата.
     */
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    /**
     * Поле генерирует идентификатор пользователя.
     */
    private static final AtomicInteger USER_ID = new AtomicInteger(1);
    /**
     * Коллекция хранит объявления.
     */
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();
    /**
     * Коллекция хранит кандидатов.
     */
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    /**
     * Коллекция хранит пользователей.
     */
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    /**
     * Конструктор инициализирует хранилище.
     */
    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    /**
     * Метод возвращает объект хранилища.
     * @return Синглтон.
     */
    public static MemStore instOf() {
        return INST;
    }

    /**
     * Метод возвращает все объявления из хранилища.
     * @return Коллекция объявлений.
     */
    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    /**
     * Метод возвращает всех кандидатов из хранилища.
     * @return Коллекция кандидатов.
     */
    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    /**
     * Метод возвращает всех пользователей из хранилища.
     * @return Коллекция пользователей.
     */
    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    /**
     * Метод сохраняет объявление в хранилище.
     * @param post Объявление.
     */
    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    /**
     * Метод сохранает кандидата в хранилище.
     * @param candidate Кандидат.
     */
    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    /**
     * Метод сохраняет пользователя в хранилище.
     * @param user Пользователь.
     */
    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    /**
     * Метод осуществляет поиск вакансии по ее id.
     * @param id Идентификатор вакансии.
     * @return Вакансия.
     */
    @Override
    public Post findPostById(int id) {
        return posts.get(id);
    }

    /**
     * Метод осуществляет поиск кандидата по его id.
     * @param id Идентификатор кандидата.
     * @return Кандидат.
     */
    @Override
    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    /**
     * Метод осуществляет поиск пользователя по его id.
     * @param id Идентификатор пользователя.
     * @return Пользователь.
     */
    @Override
    public User findUserById(int id) {
        return users.get(id);
    }

    /**
     * Метод удаляет кандидата из хранилища.
     * @param id Идентификатор кандидата.
     */
    @Override
    public void deleteCandidate(int id) {
        candidates.remove(id);
    }

    /**
     * Метод удаляет вакансию из хранилища.
     * @param id Идентификатор вакансии.
     */
    @Override
    public void deletePost(int id) {
        posts.remove(id);
    }

    /**
     * Метод удаляет пользователя из хранилища.
     * @param id Идентификатор пользователя.
     */
    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }
}
