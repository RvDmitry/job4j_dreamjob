package ru.job4j.dream.model;

/**
 * Class City
 * Класс описывает город.
 * @author Dmitry Razumov
 * @version 1
 */
public class City {
    /**
     * Идентификатор города.
     */
    private int id;
    /**
     * Наименование города.
     */
    private String name;

    /**
     * Конструктор создает город.
     * @param id Идентификатор.
     * @param name Наименование.
     */
    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Метод возвращает идентификатор города.
     * @return Идентификатор.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор города.
     * @param id Идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает наименование города.
     * @return Наименование.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод задает наименование города.
     * @param name Наименование.
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
