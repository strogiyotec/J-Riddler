package com.jriddler;

import com.beust.jcommander.JCommander;
import com.jriddler.attrs.Attributes;
import com.jriddler.attrs.ColumnValue;
import com.jriddler.cli.UserInput;
import com.jriddler.sql.LoggableInsertQuery;
import com.jriddler.sql.SingleConnectionDataSource;
import lombok.extern.java.Log;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;

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
        final UserInput userInput = Main.parsedInput(args);
        final SingleConnectionDataSource dataSource = Main.dataSource(userInput);
        try {
            Main.insertRandomRow(userInput, dataSource);
        } finally {
            dataSource.destroy();
        }
    }

    /**
     * Convert args to UserInput instance.
     *
     * @param args List of cli args
     * @return UserInput
     */
    private static UserInput parsedInput(final String[] args) {
        final UserInput userInput = new UserInput();
        JCommander.newBuilder()
                .addObject(userInput)
                .build()
                .parse(args);
        return userInput;
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
        final Attributes attributes = new Attributes(
                userInput.getTable(),
                dataSource,
                userInput.getUserAttributes()
        );
        new FluentJdbcBuilder()
                .afterQueryListener(new LoggableInsertQuery(attributes))
                .connectionProvider(dataSource)
                .build()
                .query()
                .update(
                        new InsertQuery(
                                attributes,
                                userInput.getTable()
                        ).create()
                )
                .params(
                        ColumnValue.values(attributes)
                ).run();
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
