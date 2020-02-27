package com.jriddler;

import com.jriddler.attrs.*;
import com.jriddler.sql.SqlInsert;
import com.jriddler.sql.SqlOperation;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;

public final class SqlInsertTest extends DbTest {

    private User user;

    private SqlOperation<Boolean> sqlOperation;

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
                DATASOURCE,
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

    @Test
    public void testSqlInsert() throws SQLException {
        Assert.assertFalse(this.sqlOperation.perform());

        try (final Connection connection = DATASOURCE.getConnection()) {
            try (final PreparedStatement statement = connection.prepareStatement("SELECT age,name,surname,active,birthday,id FROM users WHERE id = ?")) {
                statement.setLong(1, 10);
                try (final ResultSet set = statement.executeQuery()) {
                    while (set.next()) {
                        Assert.assertThat(
                                set.getInt(1),
                                CoreMatchers.is(this.user.getAge())
                        );
                        Assert.assertThat(
                                set.getString(2),
                                CoreMatchers.is(this.user.getName())
                        );
                        Assert.assertThat(
                                set.getString(3),
                                CoreMatchers.is(this.user.getSurname())
                        );
                        Assert.assertThat(
                                set.getBoolean(4),
                                CoreMatchers.is(this.user.isActive())
                        );
                        Assert.assertThat(
                                set.getTimestamp(5).getTime(),
                                CoreMatchers.is(Timestamp.valueOf(this.user.getBirthday().toLocalDateTime()).getTime())
                        );
                        Assert.assertThat(
                                set.getLong(6),
                                CoreMatchers.is(this.user.getId())
                        );
                    }
                }
            }
        }
    }
}
