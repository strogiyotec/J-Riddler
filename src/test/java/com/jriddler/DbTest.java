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

public abstract class DbTest {

    static {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
    }

    @ClassRule
    public static final PostgreSQLContainer CONTAINER = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");


    static final OffsetDateTime NOW = OffsetDateTime.now();

    static DataSource DATASOURCE;

    @BeforeClass
    public static void initClass() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(CONTAINER.getJdbcUrl());
        dataSource.setUser(CONTAINER.getUsername());
        dataSource.setPassword(CONTAINER.getPassword());
        DATASOURCE = dataSource;
        Flyway.configure()
                .dataSource(DATASOURCE)
                .load()
                .migrate();
    }


}
