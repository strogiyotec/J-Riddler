package com.jriddler;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Date;

public final class SqlInsertTest {

    private final OffsetDateTime NOW = OffsetDateTime.now();

    private SqlOperation<Boolean> sqlOperation;

    private DataSource dataSource;


    @Before
    public void init() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/test");
        dataSource.setUser("postgres");
        dataSource.setPassword("123");
        this.dataSource = dataSource;
        this.sqlOperation = new SqlInsert(
                dataSource,
                Arrays.asList(
                        new IntAttr("age", 25),
                        new VarCharAttr("name", 6, "Almas"),
                        new VarCharAttr("surname", 6, "Abdrazak"),
                        new BoolAttr("active", true),
                        new TimeStampAttr("birthday", NOW),
                        new BigIntAttr("id", 10)
                ),
                "test_users"
        );
    }

    @Test
    public void testSqlInsert() throws SQLException {
        Assert.assertFalse(this.sqlOperation.perform());

        try (final Connection connection = this.dataSource.getConnection()) {
            try (final PreparedStatement statement = connection.prepareStatement("SELECT age,name,surname,active,birthday,id FROM test_users WHERE id = ?")) {
                statement.setLong(1, 10);
                try (final ResultSet set = statement.executeQuery()) {
                    while (set.next()) {
                        Assert.assertThat(
                                set.getInt(1),
                                CoreMatchers.is(25)
                        );
                        Assert.assertThat(
                                set.getString(2),
                                CoreMatchers.is("Almas")
                        );
                        Assert.assertThat(
                                set.getString(3),
                                CoreMatchers.is("Abdrazak")
                        );
                        Assert.assertThat(
                                set.getBoolean(4),
                                CoreMatchers.is(true)
                        );
                        Assert.assertThat(
                                set.getTimestamp(5).getTime(),
                                CoreMatchers.is(Timestamp.valueOf(NOW.toLocalDateTime()).getTime())
                        );
                        Assert.assertThat(
                                set.getLong(6),
                                CoreMatchers.is(10L)
                        );
                    }
                }
            }
        }
    }
}
