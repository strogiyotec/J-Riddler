package com.jriddler.columns;


import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * List of table columns.
 */
public final class Columns implements Iterable<ColumnDefinition> {

    /**
     * Actual list of columns.
     */
    private final List<ColumnDefinition> columns;

    /**
     * Ctor.
     *
     * @param tableName  Table with columns
     * @param dataSource DataSource
     */
    @SneakyThrows(SQLException.class)
    @SuppressWarnings("LineLength")
    public Columns(
            final String tableName,
            final DataSource dataSource
    ) {
        this.columns = Columns.dbColumns(
                tableName,
                dataSource,
                Collections.emptyMap()
        );
    }


    /**
     * Ctor.
     *
     * @param tableName      Table with columns
     * @param dataSource     Datasource
     * @param customValues Custom column values to use
     */
    @SneakyThrows(SQLException.class)
    @SuppressWarnings("LineLength")
    public Columns(
            final String tableName,
            final DataSource dataSource,
            final Map<String, String> customValues
    ) {
        this.columns = Columns.dbColumns(
                tableName,
                dataSource,
                customValues
        );
    }

    @Override
    public Iterator<ColumnDefinition> iterator() {
        return this.columns.iterator();
    }

    /**
     * Collect all table columns into single list.
     *
     * @param tableName    Table name
     * @param dataSource   Datasource
     * @param customValues Custom values for columns
     * @return List of columns
     * @throws SQLException if failed
     */
    @SuppressWarnings("LineLength")
    private static List<ColumnDefinition> dbColumns(
            final String tableName,
            final DataSource dataSource,
            final Map<String, String> customValues
    ) throws SQLException {
        final List<ColumnDefinition> columns = new ArrayList<>(16);
        try (final Connection connection = dataSource.getConnection()) {
            try (final ResultSet columnsMeta = Columns.columnsMetaData(connection, tableName)) {
                while (columnsMeta.next()) {
                    //Skip auto increment columns
                    if (!"YES".equals(columnsMeta.getString("IS_AUTOINCREMENT"))) {
                        columns.add(new ColumnDefinitionBuilder(columnsMeta, customValues));
                    }
                }
            }
        }
        return columns;
    }

    /**
     * Create columns metadata.
     *
     * @param connection Connection
     * @param tableName  Table name
     * @return Columns metadata
     * @throws SQLException if failed
     */
    private static ResultSet columnsMetaData(
            final Connection connection,
            final String tableName
    ) throws SQLException {
        return connection.getMetaData().getColumns(
                connection.getCatalog(),
                connection.getSchema(),
                tableName,
                null
        );
    }
}
