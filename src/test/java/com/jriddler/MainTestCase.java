package com.jriddler;

import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

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
    public void testMain() throws SQLException {
        Main.main(
                new String[]{
                        "-table", "users",
                        "-host", CONTAINER.getContainerIpAddress(),
                        "-port", CONTAINER.getMappedPort(POSTGRESQL_PORT).toString(),
                        "-name", CONTAINER.getUsername(),
                        "-password", CONTAINER.getPassword(),
                        "-db", CONTAINER.getDatabaseName(),
                }
        );
        Assert.assertThat(
                this.query.select("SELECT * FROM users;").listResult(Mappers.map()).size(),
                CoreMatchers.is(1)
        );
    }

    /**
     * Test that new row is created.
     *
     * @throws SQLException If failed
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testMainWithUserDefinedAttrs() throws SQLException {
        Main.main(
                new String[]{
                        "-table", "users",
                        "-host", CONTAINER.getContainerIpAddress(),
                        "-port", CONTAINER.getMappedPort(POSTGRESQL_PORT).toString(),
                        "-name", CONTAINER.getUsername(),
                        "-password", CONTAINER.getPassword(),
                        "-db", CONTAINER.getDatabaseName(),
                        "-UAname=Almas",
                }
        );
        final List<Map<String, Object>> users =
                this.query
                        .select("SELECT * FROM users where name='Almas';")
                        .listResult(Mappers.map());
        // name is equals to name from user attrs
        Assert.assertThat(
                users.get(0).get("name").toString(),
                CoreMatchers.is("Almas")
        );
    }
}
