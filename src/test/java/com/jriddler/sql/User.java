package com.jriddler.sql;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static java.time.Instant.ofEpochMilli;
import static java.time.OffsetDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;

/**
 * Db user.
 */
@AllArgsConstructor
@Getter
final class User {
    /**
     * Name.
     */
    private final String name;

    /**
     * Surname.
     */
    private final String surname;

    /**
     * Birthday.
     */
    private final OffsetDateTime birthday;

    /**
     * Age.
     */
    private final int age;

    /**
     * Id.
     */
    private final long id;

    /**
     * Active.
     */
    private final boolean active;

    /**
     * Row Mapper for {@link User}.
     */
    public static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
            return new User(
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    ofInstant(
                            ofEpochMilli(
                                    resultSet.getTimestamp("birthday").getTime()
                            ),
                            systemDefault()
                    ),
                    resultSet.getInt("age"),
                    resultSet.getLong("id"),
                    resultSet.getBoolean("active")
            );
        }

    }
}
