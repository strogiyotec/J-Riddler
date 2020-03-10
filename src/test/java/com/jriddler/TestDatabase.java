package com.jriddler;

import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Base class that gives access to db.
 */
public abstract class TestDatabase {

    static {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying,
        // irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
    }

    /**
     * Postgres.
     */
    @ClassRule
    public static final PostgreSQLContainer CONTAINER =
            new PostgreSQLContainer("postgres:11.1")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    /**
     * Current date.
     */
    protected static final OffsetDateTime NOW = OffsetDateTime.now();

    /**
     * Data source.
     */
    private static DataSource datasource;

    /**
     * Jdbc template.
     */
    @SuppressWarnings("VisibilityModifier")
    protected JdbcTemplate jdbcTemplate;

    /**
     * Init.
     */
    @BeforeClass
    public static void initClass() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(CONTAINER.getJdbcUrl());
        dataSource.setUser(CONTAINER.getUsername());
        dataSource.setPassword(CONTAINER.getPassword());
        TestDatabase.datasource = dataSource;
        Flyway.configure()
                .dataSource(datasource)
                .load()
                .migrate();
    }

    /**
     * Create jdbc template.
     */
    @Before
    public final void initJdbc() {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    /**
     * Clean table.
     */
    @After
    public final void destroy() {
        this.jdbcTemplate.update("DELETE FROM users");
    }


}
