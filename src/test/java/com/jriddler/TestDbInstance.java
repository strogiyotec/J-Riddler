package com.jriddler;

import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.Query;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Base class that gives access to db.
 */
public abstract class TestDbInstance {

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
    @SuppressWarnings("VisibilityModifier")
    protected static DataSource datasource;

    /**
     * Query.
     */
    @SuppressWarnings("VisibilityModifier")
    protected Query query;

    /**
     * Init.
     */
    @BeforeClass
    public static void initClass() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(CONTAINER.getJdbcUrl());
        dataSource.setUser(CONTAINER.getUsername());
        dataSource.setPassword(CONTAINER.getPassword());
        TestDbInstance.datasource = dataSource;
        Flyway.configure()
                .dataSource(datasource)
                .load()
                .migrate();
    }

    /**
     * Create jdbc template.
     */
    @Before
    public final void initQuery() {
        this.query = new FluentJdbcBuilder()
                .connectionProvider(datasource)
                .build()
                .query();
    }

    /**
     * Clean table.
     */
    @After
    public final void destroy() {
        this.query.update("DELETE FROM users").run();
    }


}
