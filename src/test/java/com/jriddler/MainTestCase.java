package com.jriddler;

import com.jriddler.cli.UserInput;
import com.jriddler.text.Help;
import com.jriddler.text.Version;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;

/**
 * Test {@link Main}.
 */
public final class MainTestCase extends TestDbInstance {

    /**
     * Test that new random row is created.
     * This method inserts new user
     * and check that users table is not empty
     * and contains new inserted user
     *
     * @throws SQLException If failed
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testInsertRandomRow() throws SQLException {
        new Main(
                System.out,
                new UserInput(new String[]{
                        "-table", "users",
                        "-host", CONTAINER.getContainerIpAddress(),
                        "-port", CONTAINER.getMappedPort(POSTGRESQL_PORT).toString(),
                        "-name", CONTAINER.getUsername(),
                        "-password", CONTAINER.getPassword(),
                        "-db", CONTAINER.getDatabaseName(),
                })
        ).call();
        Assert.assertThat(
                this.query
                        .select("SELECT count(*) FROM users;")
                        .singleResult(Mappers.singleLong()),
                Matchers.greaterThan(0L)
        );
    }

    /**
     * Test that row with foreign keys is created.
     * This method inserts new user_to_item row
     * as this table contains two foreign keys
     * to users and items tables then
     * new users and items rows will be created as well
     *
     * @throws SQLException If failed
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testRowWithFk() throws SQLException {
        new Main(
                System.out,
                new UserInput(
                        new String[]{
                                "-table", "user_to_item",
                                "-host", CONTAINER.getContainerIpAddress(),
                                "-port", CONTAINER.getMappedPort(POSTGRESQL_PORT).toString(),
                                "-name", CONTAINER.getUsername(),
                                "-password", CONTAINER.getPassword(),
                                "-db", CONTAINER.getDatabaseName(),
                        }
                )
        ).call();
        Assert.assertThat(
                this.query
                        .select("SELECT count(*) FROM user_to_item;")
                        .singleResult(Mappers.singleLong()),
                Matchers.greaterThan(0L)
        );
        Assert.assertThat(
                this.query
                        .select("SELECT count(*) FROM users;")
                        .singleResult(Mappers.singleLong()),
                Matchers.greaterThan(0L)
        );
        Assert.assertThat(
                this.query
                        .select("SELECT count(*) FROM items;")
                        .singleResult(Mappers.singleLong()),
                Matchers.greaterThan(0L)
        );
    }

    /**
     * Test that new row was custom values is created.
     *
     * @throws SQLException If failed
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testRowWithCustomValue() throws SQLException {
        final String customName = "Almas";
        new Main(
                System.out,
                new UserInput(
                        new String[]{
                                "-table", "users",
                                "-host", CONTAINER.getContainerIpAddress(),
                                "-port", CONTAINER.getMappedPort(POSTGRESQL_PORT).toString(),
                                "-name", CONTAINER.getUsername(),
                                "-password", CONTAINER.getPassword(),
                                "-db", CONTAINER.getDatabaseName(),
                                "-Vname=" + customName,
                        }
                )
        ).call();
        // user with custom name was created
        Assert.assertThat(
                this.query.select(
                        String.format(
                                "SELECT name FROM users WHERE name='%s' LIMIT 1;",
                                customName
                        )
                ).singleResult(Mappers.singleString()),
                CoreMatchers.is(customName)
        );
    }

    /**
     * Test that help information was shown.
     * It uses {@link ByteArrayOutputStream} in order to keep output info
     *
     * @throws Exception If failed
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testPrintHelpInfo() throws Exception {
        try (final ByteArrayOutputStream storage = new ByteArrayOutputStream()) {
            try (final PrintStream stream = new PrintStream(storage)) {
                new Main(
                        stream,
                        new UserInput(
                                new String[]{
                                        "-help",
                                }
                        )
                ).call();
                Assert.assertThat(
                        storage.toString(),
                        CoreMatchers.is(
                                new Help().asString()
                        )
                );
            }
        }
    }

    /**
     * Test that version information was shown.
     * It uses {@link ByteArrayOutputStream} in order to keep output info
     *
     * @throws Exception If failed
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testPrintVersion() throws Exception {
        try (final ByteArrayOutputStream storage = new ByteArrayOutputStream()) {
            try (final PrintStream output = new PrintStream(storage)) {
                new Main(
                        output,
                        new UserInput(
                                new String[]{
                                        "-version",
                                })
                ).call();
                Assert.assertThat(
                        storage.toString(),
                        CoreMatchers.is(
                                new Version().asString()
                        )
                );
            }
        }
    }
}
