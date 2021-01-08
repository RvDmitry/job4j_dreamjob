package ru.job4j.dream.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class Post
 * Класс описывает вакансию.
 * @author Dmitry Razumov
 * @version 1
 */
public class Post {
    /**
     * Идентификатор вакансии.
     */
    private int id;
    /**
     * Наименование вакансии.
     */
    private String name;
    /**
     * Описание вакансии.
     */
    private String description;
    /**
     * Дата создания вакансии.
     */
    private LocalDate created;

    /**
     * Конструктор создает объявление о вакансии.
     * @param id Идентификатор вакансии.
     * @param name Наименование вакансии.
     */
    public Post(int id, String name) {
        this.id = id;
        this.name = name;
        created = LocalDate.now();
    }

    /**
     * Метод возвращает идентификатор вакансии.
     * @return Идентификатор.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор вакансии.
     * @param id Идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает наименование вакансии.
     * @return Наименование.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод задает наименование вакансии.
     * @param name Наименование.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод возвращает описание вакансии.
     * @return Описание вакансии.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Метод задает описание вакансии.
     * @param description Описание вакансии.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Метод возвращает дату создания вакансии.
     * @return Дата создания вакансии.
     */
    public LocalDate getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
