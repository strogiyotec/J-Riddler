package com.jriddler;

import com.jriddler.attrs.*;
import com.jriddler.sql.SqlInsert;
import com.jriddler.sql.SqlOperation;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Test sql insert.
 */
@SuppressWarnings("MagicNumber")
public final class SqlInsertTest extends DbTest {

    /**
     * User data.
     */
    private User user;

    /**
     * Sql operation to test.
     */
    private SqlOperation<Integer> sqlOperation;

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
                this.jdbcTemplate,
                Arrays.asList(
                        new IntAttr("age", this.user.getAge()),
                        new VarCharAttr("name", 6, this.user.getName()),
                        new VarCharAttr("surname", 6, this.user.getSurname()),
                        new BoolAttr("active", this.user.isActive()),
                        new TimeStampAttr("birthday", this.user.getBirthday()),
                        new BigIntAttr("id", this.user.getId())
                ),
                "users"
        );
    }

    /**
     * Test insert query.
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testSqlInsert() {
        Assert.assertThat(
                this.sqlOperation.perform(),
                CoreMatchers.is(1)
        );
        final User dbUser = this.jdbcTemplate.queryForObject(
                "SELECT age,name,surname,active,birthday,id FROM users WHERE id = ?",
                User::mapRow,
                this.user.getId()
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
