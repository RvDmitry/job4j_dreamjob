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
     * Идентификатор фото кандидата.
     */
    private int photoId;
    /**
     * Идентификатор города кандидата.
     */
    private int cityId;

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

    /**
     * Метод возвращает идентификатор фото.
     * @return Идентификатор фото.
     */
    public int getPhotoId() {
        return photoId;
    }

    /**
     * Метод задает идентификатор фото.
     * @param photoId Идентификатор фото.
     */
    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    /**
     * Метод возвращает идентификатор города.
     * @return Идентификатор города.
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * Метод задает идентификатор города.
     * @param cityId Идентификатор города.
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
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
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", photoId=" + photoId
                + ", cityId=" + cityId
                + '}';
    }
}
