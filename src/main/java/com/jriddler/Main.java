package com.jriddler;

import com.beust.jcommander.JCommander;
import com.jriddler.cli.UserInput;
import com.jriddler.sql.SingleConnectionDataSource;
import com.jriddler.sql.SqlInsert;
import lombok.extern.java.Log;

import java.sql.SQLException;
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
     * @throws SQLException If failed
     */
    @SuppressWarnings("LineLength")
    public static void main(final String[] args) throws SQLException {
        Main.setUpLogs();
        final UserInput userInput = new UserInput();
        JCommander.newBuilder()
                .addObject(userInput)
                .build()
                .parse(args);

        final SingleConnectionDataSource dataSource = Main.dataSource(userInput);
        try {
            Main.insertRandomRow(userInput, dataSource);
        } finally {
            dataSource.destroy();
        }
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
        new SqlInsert(
                dataSource,
                userInput.getTable(),
                userInput.getUserAttributes()
        ).perform();
    }

    /**
     * Create one connection data source.
     *
     * @param userInput UserInput
     * @return DataSource
     */
    private static SingleConnectionDataSource dataSource(
            final UserInput userInput
    ) {
        return new SingleConnectionDataSource(
                userInput.getUsername(),
                userInput.getPassword(),
                String.format(
                        "jdbc:postgresql://%s:%d/%s",
                        userInput.getDbHost(),
                        userInput.getPort(),
                        userInput.getDbName()
                )
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
