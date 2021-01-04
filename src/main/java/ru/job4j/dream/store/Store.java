package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Store
 * Класс описывает хранилище.
 * @author Dmitry Razumov
 * @version 1
 */
public class Store {
    /**
     * Синглтон, создает объект хранилища.
     */
    private static final Store INST = new Store();
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
    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "Desc Junior"));
        posts.put(2, new Post(2, "Middle Java Job", "Desc Middle"));
        posts.put(3, new Post(3, "Senior Java Job", "Desc Senior"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    /**
     * Метод возвращает объект хранилища.
     * @return Синглтон.
     */
    public static Store instOf() {
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
}
