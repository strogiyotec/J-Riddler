package com.jriddler;

import com.beust.jcommander.JCommander;
import com.jriddler.sql.SqlInsert;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.logging.Level;

/**
 * Entry point.
 */
@Log
public final class Main {

    /**
     * Ctor.
     */
    private Main() {

    }

    /**
     * Main.
     *
     * @param args argc
     */
    public static void main(final String[] args) {
        Main.setUpLogs();
        final DbSettings dbSettings = new DbSettings();
        JCommander.newBuilder()
                .addObject(dbSettings)
                .build()
                .parse(args);
        final SingleConnectionDataSource dataSource = new SingleConnectionDataSource(
                String.format(
                        "jdbc:postgresql://%s:%d/%s",
                        dbSettings.getDbHost(),
                        dbSettings.getPort(),
                        dbSettings.getDbName()
                ),
                dbSettings.getUsername(),
                dbSettings.getPassword(),
                true
        );
        new SqlInsert(
                new JdbcTemplate(
                        dataSource
                ),
                dbSettings.getTable()
        ).perform();
        dataSource.destroy();
    }

    /**
     * Setup logging.
     */
    private static void setUpLogs() {
        final String logConfig = Main.class
                .getClassLoader()
                .getResource("jul-log.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file", logConfig);
        log.log(Level.CONFIG, "Logging is configured");
    }
}
