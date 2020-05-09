package com.jriddler;

import com.jriddler.cli.UserInput;
import com.jriddler.text.Help;
import com.jriddler.text.Version;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;

/**
 * Test {@link Main}.
 */
public final class MainTestCase extends TestDbInstance {

    /**
     * Test that new row is created.
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
                this.query.select("SELECT * FROM users;").listResult(Mappers.map()).size(),
                CoreMatchers.is(1)
        );
    }

    /**
     * Test that row with foreign keys is created.
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
        final List<Map<String, Object>> users =
                this.query
                        .select("SELECT * FROM users where name='Almas';")
                        .listResult(Mappers.map());

        // name is equals to name from user columns
        Assert.assertThat(
                users.get(0).get("name").toString(),
                CoreMatchers.is(customName)
        );
    }

    /**
     * Test that help information was shown.
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
     *
     * @throws Exception If failed
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testPrintVersion() throws Exception {
        try (final ByteArrayOutputStream storage = new ByteArrayOutputStream()) {
            try (final PrintStream stream = new PrintStream(storage)) {
                new Main(
                        stream,
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
