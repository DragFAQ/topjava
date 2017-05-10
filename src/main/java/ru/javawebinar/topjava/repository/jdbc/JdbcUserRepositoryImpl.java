package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final BeanPropertyRowMapper<String> ROLE_MAPPER = BeanPropertyRowMapper.newInstance(String.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        String roles = "'";
        for (Role r : user.getRoles())
            roles += r.toString() + "','";
        roles = roles.replaceFirst("\\,\\'$", "");
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);

            jdbcTemplate.update("DELETE FROM user_roles where user_id=? AND role not in (" + roles + ")", user.getId());
        }

        jdbcTemplate.update("INSERT INTO user_roles (user_id, role) " +
                "SELECT r.id, r.role_array from (SELECT ? as id, unnest(array[" + roles + "]) AS role_array) r " +
                "WHERE r.role_array not in (SELECT role FROM user_roles where user_id=r.id)", user.getId());


        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    private Set<Role> getRoles(int id) {
        List<String> strRoles = jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=" + id, String.class);
        Set<Role> roles = new HashSet<>();
        for (String s : strRoles)
            roles.add(Role.valueOf(s));

        return roles;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        User user = DataAccessUtils.singleResult(users);
        if (user != null)
            user.setRoles(getRoles(id));
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        user.setRoles(getRoles(user.getId()));
        return user;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON user_id = id ORDER BY name, email", (new UserExtractor()));
    }

    private class UserExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> map = new LinkedHashMap<>();
            User user = null;
            while (rs.next()) {
                Integer id = rs.getInt("id");
                user = map.get(id);
                if (user == null) {
                    user = new User(id, rs.getString("name"), rs.getString("email"),
                            rs.getString("password"), rs.getInt("calories_per_day"),
                            rs.getBoolean("enabled"), new HashSet<>());
                    map.put(id, user);
                }
                user.getRoles().add(Role.valueOf(rs.getString("role")));
            }

            return new ArrayList<User>(map.values());
        }
    }
}
