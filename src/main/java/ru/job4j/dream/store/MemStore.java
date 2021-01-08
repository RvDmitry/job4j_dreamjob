package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

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
     * Коллекция хранит объявления.
     */
    private Map<Integer, Post> posts = new ConcurrentHashMap<>();
    /**
     * Коллекция хранит кандидатов.
     */
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

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
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    /**
     * Метод возвращает всех кандидатов из хранилища.
     * @return Коллекция кандидатов.
     */
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    /**
     * Метод сохраняет объявление в хранилище.
     * @param post Объявление.
     */
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
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    /**
     * Метод осуществляет поиск вакансии по ее id.
     * @param id Идентификатор вакансии.
     * @return Вакансия.
     */
    public Post findPostById(int id) {
        return posts.get(id);
    }

    /**
     * Метод осуществляет поиск кандидата по его id.
     * @param id Идентификатор кандидата.
     * @return Кандидат.
     */
    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }
}