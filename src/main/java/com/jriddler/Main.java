package com.jriddler;

import com.beust.jcommander.JCommander;
import com.jriddler.cli.UserInput;
import com.jriddler.columns.ColumnValue;
import com.jriddler.columns.Columns;
import com.jriddler.columns.fk.ForeignKey;
import com.jriddler.columns.fk.ForeignKeys;
import com.jriddler.columns.fk.ForeignKeysBuilder;
import com.jriddler.sql.LoggableInsertQuery;
import com.jriddler.sql.SingleConnectionDataSource;
import com.jriddler.text.Version;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.mapper.Mappers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
     * Doesn't insert row if need to show version
     *
     * @param args Argc
     * @throws SQLException If failed
     */
    @SuppressWarnings("LineLength")
    public static void main(final String[] args) throws SQLException {
        final UserInput userInput = Main.parsedInput(args);
        if (userInput.isVersion()) {
            System.out.println(new Version().asString());
            return;
        }
        if(userInput.isHelp()){
            System.out.println();
        }
        final SingleConnectionDataSource dataSource = new SingleConnectionDataSource(userInput);
        final FluentJdbc jdbc = new FluentJdbcBuilder()
                .afterQueryListener(new LoggableInsertQuery())
                .connectionProvider(dataSource)
                .build();
        try {
            Main.insertRowWithForeignKeys(
                    userInput.getTable(),
                    userInput.getUserValues(),
                    dataSource,
                    jdbc
            );
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
     * If table has foreign keys
     * then it recursively create new rows
     * for tables pointed by foreign keys and
     * save primary keys of those rows in customValues map
     *
     * @param customValues Custom values for columns
     * @param table        Table name
     * @param dataSource   Datasource
     * @param jdbc         Jdbc
     * @return Generated keys
     * @throws SQLException If failed
     */
    private static Map<String, Object> insertRowWithForeignKeys(
            final String table,
            final Map<String, String> customValues,
            final SingleConnectionDataSource dataSource,
            final FluentJdbc jdbc
    ) throws SQLException {
        final ForeignKeys foreignKeys =
                new ForeignKeysBuilder(
                        dataSource,
                        table
                );
        if (foreignKeys.hasKeys()) {
            for (final ForeignKey fkey : foreignKeys) {
                //save pk value in custom values map
                //key is fk name
                //value is generated value of table pointed by fk
                customValues.put(
                        fkey.fkColumnName(),
                        Main.insertRowWithForeignKeys(
                                fkey.pkTableName(),
                                new HashMap<>(),
                                dataSource,
                                jdbc
                        ).get(fkey.pkColumnName()).toString()
                );
            }
        }
        final Columns columns = new Columns(
                table,
                dataSource,
                customValues
        );
        return Main.insert(columns, table, jdbc);
    }

    /**
     * Execute insert query.
     *
     * @param columns Columns to insert
     * @param table   Table
     * @param jdbc    Jdbc
     * @return Generated keys as map
     * @throws IllegalStateException If no generated keys
     */
    private static Map<String, Object> insert(
            final Columns columns,
            final String table,
            final FluentJdbc jdbc
    ) {
        return jdbc
                .query()
                .update(
                        new InsertQuery(
                                columns,
                                table
                        ).create()
                )
                .params(ColumnValue.values(columns))
                .runFetchGenKeys(Mappers.map())
                .firstKey()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No rows were created"
                        )
                );
    }
}
