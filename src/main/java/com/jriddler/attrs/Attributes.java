package com.jriddler.attrs;


import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * List of table attributes.
 */
public final class Attributes implements List<AttributeDefinition> {

    /**
     * Actual list of attributes.
     */
    @Delegate
    private final List<AttributeDefinition> attributes;

    /**
     * Ctor.
     *
     * @param tableName    Table with attributes
     * @param jdbcTemplate JdbcTemplate to send sql query
     */
    @SneakyThrows(SQLException.class)
    @SuppressWarnings("LineLength")
    public Attributes(
            final String tableName,
            final JdbcTemplate jdbcTemplate
    ) {
        this.attributes = Attributes.fetchAttributesFromDb(tableName, jdbcTemplate);
    }

    /**
     * Collect all table attributes into single list.
     *
     * @param tableName    Table name
     * @param jdbcTemplate JdbcTemplate for sql
     * @return List of attributes
     * @throws SQLException if failed
     */
    @SuppressWarnings("LineLength")
    private static List<AttributeDefinition> fetchAttributesFromDb(
            final String tableName,
            final JdbcTemplate jdbcTemplate
    ) throws SQLException {
        final List<AttributeDefinition> attrs = new ArrayList<>(16);
        try (final Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            try (final ResultSet columns = Attributes.columnsMetaData(connection, tableName)) {
                while (columns.next()) {
                    attrs.add(
                            new DynamicAttr(
                                    columns.getInt("DATA_TYPE"),
                                    columns.getInt("COLUMN_SIZE"),
                                    columns.getString("COLUMN_NAME")
                            )
                    );
                }

            }
        }
        return attrs;
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