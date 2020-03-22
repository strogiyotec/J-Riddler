package com.jriddler.sql;

import com.jriddler.TestDbInstance;
import com.jriddler.attrs.*;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

/**
 * Test sql insert.
 */
@SuppressWarnings("MagicNumber")
public final class SqlInsertTestCase extends TestDbInstance {

    /**
     * User data.
     */
    private User user;

    /**
     * Sql operation to test.
     */
    private SqlOperation<KeyHolder> sqlOperation;

    /**
     * Init.
     */
    @Before
    public void init() {
        this.user = new User(
                "Almas",
                "Abdrazak",
                NOW,
                25,
                10,
                true
        );
        this.sqlOperation = new SqlInsert(
                Arrays.asList(
                        new IntAttr("age", this.user.getAge()),
                        new VarCharAttr("name", 6, this.user.getName()),
                        new VarCharAttr("surname", 8, this.user.getSurname()),
                        new BoolAttr("active", this.user.isActive()),
                        new TimeStampAttr("birthday", this.user.getBirthday()),
                        new BigIntAttr("id", this.user.getId())
                ),
                new PrimaryKeys(
                        "users",
                        this.jdbcTemplate
                ),
                "users",
                this.jdbcTemplate
        );
    }

    /**
     * Test insert query.
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testSqlInsert() {
        Assert.assertThat(
                this.sqlOperation.perform().getKey(),
                CoreMatchers.is(this.user.getId())
        );
        final User dbUser = Objects.requireNonNull(
                this.jdbcTemplate.queryForObject(
                        "SELECT age,name,surname,active,birthday,id FROM users WHERE id = ? LIMIT 1",
                        User::mapRow,
                        this.user.getId()
                )
        );
        Assert.assertThat(
                dbUser.getAge(),
                CoreMatchers.is(this.user.getAge())
        );
        Assert.assertThat(
                dbUser.getName(),
                CoreMatchers.is(this.user.getName())
        );
        Assert.assertThat(
                dbUser.getSurname(),
                CoreMatchers.is(this.user.getSurname())
        );
        Assert.assertThat(
                dbUser.isActive(),
                CoreMatchers.is(this.user.isActive())
        );
        Assert.assertThat(
                Timestamp.valueOf(
                        dbUser.getBirthday()
                                .toLocalDateTime()
                ).getTime(),
                CoreMatchers.is(
                        Timestamp.valueOf(
                                this.user.getBirthday()
                                        .toLocalDateTime()
                        ).getTime()
                )
        );
        Assert.assertThat(
                dbUser.getId(),
                CoreMatchers.is(this.user.getId())
        );
    }
}
