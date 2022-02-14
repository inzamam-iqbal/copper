package co.copper.test.storage;

import co.copper.test.datamodel.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbuslab.utils.db.JacksonBeanRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JacksonBeanRowMapper<User> rowMapper;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new JacksonBeanRowMapper<>(User.class, mapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", rowMapper);
    }

    public void saveUsers(List<User> users) {
        for(User user: users) {
            jdbcTemplate.update("INSERT INTO users (id, first_name, last_name, email, password) VALUES (:id, :firstName, :lastName, :email, :password)",
                    Map.of(
                            "id", user.getId(),
                            "firstName", user.getFirstName(),
                            "lastName", user.getLastName(),
                            "email", user.getEmail(),
                            "password", user.getPassword()
                    )
            );
        }
    }
}
