package com.jriddler;


import lombok.AllArgsConstructor;
import lombok.Getter;

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
@SuppressWarnings("MagicNumber")
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
     * Create user from rowMapper.
     *
     * @param resultSet ResultSet
     * @param rowNum    Rows
     * @return User
     * @throws SQLException if failed
     */
    public static User mapRow(
            final ResultSet resultSet,
            final int rowNum
    ) throws SQLException {
        return new User(
                resultSet.getString("name"),
                resultSet.getString("surname"),
                ofInstant(
                        ofEpochMilli(resultSet.getTimestamp(5).getTime()),
                        systemDefault()
                ),
                resultSet.getInt("age"),
                resultSet.getLong("id"),
                resultSet.getBoolean("active")
        );
    }
}
