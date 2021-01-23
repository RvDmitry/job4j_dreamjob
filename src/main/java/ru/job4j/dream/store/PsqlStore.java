package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;

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
                    Candidate candidate = new Candidate(it.getInt("id"), it.getString("name"));
                    candidate.setPhotoId(it.getInt("photoid"));
                    candidates.add(candidate);
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return candidates;
    }

    /**
     * Метод возвращает всех пользователей из БД.
     * @return Список пользователей.
     */
    @Override
    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    User user = new User();
                    user.setId(it.getInt("id"));
                    user.setName(it.getString("name"));
                    user.setEmail(it.getString("email"));
                    user.setPassword(it.getString("password"));
                    users.add(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return users;
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
     * Метод сохраняет или обновляет пользователя в БД.
     * @param user Пользователь.
     */
    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
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
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO candidate(name, photoid) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            int photoId = createPhotoId();
            ps.setString(1, candidate.getName());
            ps.setInt(2, photoId);
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
     * Метод сохраняет пользователя в БД.
     * @param user Пользователь, которого нужно сохранить.
     * @return Пользователь.
     */
    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            LOG.info("Создан пользователь {}", user);
        } catch (Exception e) {
            LOG.error("Ошибка записи.", e);
        }
        return user;
    }

    /**
     * Метод создает запись в таблице Photo, вызывая тем самым увелечение первичного ключа.
     * @return Значение первичного ключа в таблице Photo.
     */
    private int createPhotoId() {
        int id = 0;
        try (Connection cn = pool.getConnection(); Statement st = cn.createStatement()) {
            String query = "insert into photo default values";
            st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                LOG.info("Значение ключа получено.");
            }
        } catch (Exception e) {
            LOG.error("Ошибка записи.", e);
        }
        return id;
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
                     cn.prepareStatement(
                             "update candidate set name = ? where id = ?"
                     )
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
     * Метод обновляет пользователя в БД.
     * @param user Пользователь, которого нужно обновить.
     */
    private void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "update users set name = ?, email = ?, password = ? where id = ?"
                     )
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
            LOG.info("Пользователь обновлен {}", user);
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
                    candidate.setPhotoId(rs.getInt("photoid"));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return candidate;
    }

    /**
     * Метод ищет пользователя в БД по его идентификатору.
     * @param id Идентификатор пользователя.
     * @return Пользователь.
     */
    @Override
    public User findUserById(int id) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(id);
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return user;
    }

    /**
     * Метод осуществляет поиск пользователя в БД по его email.
     * @param email Email пользователя.
     * @return Пользователь.
     */
    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users where email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("Ошибка запроса.", e);
        }
        return user;
    }

    /**
     * Метод удаляет кандидата из БД.
     * @param id Идентификатор кандидата.
     */
    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from candidate where id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LOG.info("Кандидат с идентификатором {} удален", id);
        } catch (Exception e) {
            LOG.error("Ошибка удаления.", e);
        }
    }

    /**
     * Метод удаляет вакансию из БД.
     * @param id Идентификатор вакансии.
     */
    @Override
    public void deletePost(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from post where id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LOG.info("Вакансия с идентификатором {} удалена", id);
        } catch (Exception e) {
            LOG.error("Ошибка удаления.", e);
        }
    }

    /**
     * Метод удаляет пользователя из БД.
     * @param id Идентификатор пользователя.
     */
    @Override
    public void deleteUser(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from users where id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            LOG.info("Пользователь с идентификатором {} удален", id);
        } catch (Exception e) {
            LOG.error("Ошибка удаления.", e);
        }
    }
}
