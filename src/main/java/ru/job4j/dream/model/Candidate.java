package ru.job4j.dream.model;

import java.util.Objects;

/**
 * Class Candidate
 * Класс описывает кандидата.
 * @author Dmitry Razumov
 * @version 1
 */
public class Candidate {
    /**
     * Идентификатор кандидата.
     */
    private int id;
    /**
     * Имя кандидата.
     */
    private String name;

    /**
     * Конструктор создает кандидата.
     * @param id Идентификатор.
     * @param name Имя.
     */
    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Метод возвращает идентификатор кандидата.
     * @return Идентификатор.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор кандидата.
     * @param id Идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает имя кандидата.
     * @return Имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод задает имя кандидата.
     * @param name Имя.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
