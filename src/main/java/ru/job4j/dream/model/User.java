package ru.job4j.dream.model;

import java.util.Objects;

/**
 * Class User
 * Класс описывает пользователя ресурса.
 * @author Dmitry Razumov
 * @version 1
 */
public class User {
    /**
     * Идентификатор пользователя.
     */
    private int id;
    /**
     * Имя пользователя.
     */
    private String name;
    /**
     * Email пользователя.
     */
    private String email;
    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Метод возвращает идентификатор.
     * @return Идентификатор.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод задает идентификатор.
     * @param id Идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает имя.
     * @return Имя.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод задает имя.
     * @param name Имя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод вовзращает email.
     * @return Email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Метод задает email.
     * @param email Email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Метод возвращает пароль.
     * @return Пароль.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Метод задает пароль.
     * @param password Пароль.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\'' + '}';
    }
}
