package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

/**
 * Class PsqlMain
 * Класс проверяет работу класса PsqlStore.
 * @author Dmitry Razumov
 * @version 1
 */
public class PsqlMain {
    /**
     * Поле содержит синглтон.
     */
    private static Store store = PsqlStore.instOf();

    /**
     * Метод сохраняет вакансию в БД.
     * @param name Имя вакансии.
     */
    public static void postSave(String name) {
        store.save(new Post(0, name));

    }

    /**
     * Метод сохраняет кандидата в БД.
     * @param name Имя кандидата.
     */
    public static void candidateSave(String name) {
        store.save(new Candidate(0, name));
    }

    /**
     * Метод обновляет вакансию в БД.
     * @param id Идентификатор вакансии.
     * @param name Новое имя вакансии.
     */
    public static void postUpdate(int id, String name) {
        store.save(new Post(id, name));
            }

    /**
     * Метод обновляет кандидата в БД.
     * @param id Идентификатор кандидата.
     * @param name Новое имя кандидата.
     */
    public static void candidateUpdate(int id, String name) {
        store.save(new Candidate(id, name));
    }

    /**
     * Метод выводит все вакансии из БД на экран.
     */
    public static void findAllPost() {
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }

    /**
     * Метод выводит всех кандидатов из БД на экран.
     */
    public static void findAllCandidate() {
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
    }

    /**
     * Метод ищет вакансию по ее Id и если она есть, выводит ее на экран.
     * @param id Идентификатор вакансии.
     */
    public static void findPostById(int id) {
        Post post = store.findPostById(id);
        if (post != null) {
            System.out.println(post.getId() + " " + post.getName());
        } else {
            System.out.println("No Post");
        }
    }

    /**
     * Метод ищет кандидата по его Id и если он есть, выводит его на экран.
     * @param id Идентификатор кандидата.
     */
    public static void findCandidateById(int id) {
        Candidate candidate = store.findCandidateById(id);
        if (candidate != null) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        } else {
            System.out.println("No Candidate");
        }
    }

    /**
     * Главный метод программы.
     * @param args Параметры командной строки.
     */
    public static void main(String[] args) {
        postSave("Job 1");
        candidateSave("Candidate 1");
        findAllPost();
        findAllCandidate();
        postUpdate(4, "Java Job");
        candidateUpdate(3, "Java Middle");
        findPostById(4);
        findCandidateById(3);
    }
}
