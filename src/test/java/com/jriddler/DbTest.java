package com.jriddler;

import org.flywaydb.core.Flyway;
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
public abstract class DbTest {

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
    static final OffsetDateTime NOW = OffsetDateTime.now();

    /**
     * Data source.
     */
    static DataSource datasource;

    /**
     * Init.
     */
    @BeforeClass
    public static void initClass() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(CONTAINER.getJdbcUrl());
        dataSource.setUser(CONTAINER.getUsername());
        dataSource.setPassword(CONTAINER.getPassword());
        datasource = dataSource;
        Flyway.configure()
                .dataSource(datasource)
                .load()
                .migrate();
    }


}
