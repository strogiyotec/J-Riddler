package com.jriddler;

import com.beust.jcommander.JCommander;
import com.jriddler.cli.UserInput;
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
     * @param args Argc
     */
    public static void main(final String[] args) {
        Main.setUpLogs();
        final UserInput userInput = new UserInput();
        JCommander.newBuilder()
                .addObject(userInput)
                .build()
                .parse(args);
        final SingleConnectionDataSource dataSource = Main.createDataSource(userInput);
        Main.insertRandomRow(userInput, dataSource);
        dataSource.destroy();
    }

    /**
     * Insert new random row.
     *
     * @param userInput  User Input
     * @param dataSource Datasource
     */
    private static void insertRandomRow(
            final UserInput userInput,
            final SingleConnectionDataSource dataSource
    ) {
        if (!userInput.getUserAttributes().isEmpty()) {
            new SqlInsert(
                    new JdbcTemplate(
                            dataSource
                    ),
                    userInput.getTable(),
                    userInput.getUserAttributes()
            ).perform();
        } else {
            new SqlInsert(
                    new JdbcTemplate(
                            dataSource
                    ),
                    userInput.getTable()
            ).perform();
        }
    }

    /**
     * Create one connection data source.
     *
     * @param userInput UserInput
     * @return DataSource
     */
    private static SingleConnectionDataSource createDataSource(
            final UserInput userInput
    ) {
        return new SingleConnectionDataSource(
                String.format(
                        "jdbc:postgresql://%s:%d/%s",
                        userInput.getDbHost(),
                        userInput.getPort(),
                        userInput.getDbName()
                ),
                userInput.getUsername(),
                userInput.getPassword(),
                true
        );
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
