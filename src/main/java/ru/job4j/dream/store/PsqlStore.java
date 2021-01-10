package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class PsqlStore
 * Класс осуществляет взаимодействие с базой данных.
 * @author Dmitry Razumov
 * @version 1
 */
public class PsqlStore implements Store {
    /**
     * Поле создает логер.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    /**
     * Поле содержит пул соединений с БД.
     */
    private final BasicDataSource pool = new BasicDataSource();

    /**
     * Конструктор создает соединение с БД.
     */
    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    /**
     * Класс создает синглтон.
     */
    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    /**
     * Метод возвращает синглтон.
     * @return Синглтон.
     */
    public static Store instOf() {
        return Lazy.INST;
    }

    /**
     * Метод возвращает все объявления из БД.
     * @return Список вакансий.
     */
    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return posts;
    }

    /**
     * Метод возвращает все кандидатов из БД.
     * @return Список кандидатов.
     */
    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return candidates;
    }

    /**
     * Метод сохраняет или обновляет вакансию в БД
     * @param post Вакансия.
     */
    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    /**
     * Метод сохраняет или обновляет кандидата в БД.
     * @param candidate Кандидат.
     */
    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    /**
     * Метод сохраняет вакансию в БД.
     * @param post Вакансия.
     * @return Сохраненная вакансия.
     */
    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
            LOG.info("Создана вакансия {}", post);
        } catch (Exception e) {
            LOG.error("Ошибка записи.", e);
        }
        return post;
    }

    /**
     * Метод сохраняет кандидата в БД.
     * @param candidate Кандидат.
     * @return Сохраненный кандидат.
     */
    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
            LOG.info("Создан кандидат {}", candidate);
        } catch (Exception e) {
            LOG.error("Ошибка записи.", e);
        }
        return candidate;
    }

    /**
     * Метод обновляет вакансию в БД.
     * @param post Вакансия, которую нужно обновить.
     */
    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("update post set name = ? where id = ?")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.executeUpdate();
            LOG.info("Вакансия обновлена {}", post);
        } catch (Exception e) {
            LOG.error("Ошибка обновления.", e);
        }
    }

    /**
     * Метод обновляет кандидата в БД.
     * @param candidate Кандидат, которого нужно обновить.
     */
    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement("update candidate set name = ? where id = ?")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.executeUpdate();
            LOG.info("Кандидат обновлен {}", candidate);
        } catch (Exception e) {
            LOG.error("Ошибка обновления.", e);
        }
    }

    /**
     * Метод ищет вакансию в БД по ее идентификатору.
     * @param id Идентификатор вакансии.
     * @return Вакансия.
     */
    @Override
    public Post findPostById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from post where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    post = new Post(id, name);
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return post;
    }

    /**
     * Метод ищет кандидата в БД по его идентификатору.
     * @param id Идентификатор кандидата.
     * @return Кандидат.
     */
    @Override
    public Candidate findCandidateById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from candidate where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    candidate = new Candidate(id, name);
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return candidate;
    }
}
