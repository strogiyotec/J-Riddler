package com.jriddler;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;

/**
 * Test {@link Main}.
 */
public final class MainTestCase extends TestDbInstance {

    /**
     * Test that new row is created.
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testMain() {
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
                this.jdbcTemplate.queryForList("SELECT * FROM users;").size(),
                CoreMatchers.is(1)
        );
    }

    /**
     * Test that new row is created.
     */
    @Test
    @SuppressWarnings("LineLength")
    public void testMainWithUserDefinedAttrs() {
        Main.main(
                new String[]{
                        "-table", "users",
                        "-host", CONTAINER.getContainerIpAddress(),
                        "-port", CONTAINER.getMappedPort(POSTGRESQL_PORT).toString(),
                        "-name", CONTAINER.getUsername(),
                        "-password", CONTAINER.getPassword(),
                        "-db", CONTAINER.getDatabaseName(),
                        "-attrs", "name:Almas"
                }
        );
        final List<Map<String, Object>> users = this.jdbcTemplate.queryForList("SELECT * FROM users where name='Almas';");
        Assert.assertFalse(users.isEmpty());
        Assert.assertThat(
                users.get(0).get("name").toString(),
                CoreMatchers.is("Almas")
        );
    }
}
