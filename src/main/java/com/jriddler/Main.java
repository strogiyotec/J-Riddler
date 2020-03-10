package com.jriddler;

import com.beust.jcommander.JCommander;
import com.jriddler.sql.SqlInsert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Entry point.
 */
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
        final DbSettings dbSettings = new DbSettings();
        JCommander.newBuilder()
                .addObject(dbSettings)
                .build()
                .parse(args);
        new SqlInsert(
                new JdbcTemplate(
                        new SingleConnectionDataSource(
                                String.format(
                                        "jdbc:postgresql://%s:%d/%s",
                                        dbSettings.getDbHost(),
                                        dbSettings.getPort(),
                                        dbSettings.getDbName()
                                ),
                                dbSettings.getUsername(),
                                dbSettings.getPassword(),
                                true
                        )
                ),
                dbSettings.getTable()
        ).perform();
    }
}
