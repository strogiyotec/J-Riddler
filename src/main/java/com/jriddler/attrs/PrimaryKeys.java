package com.jriddler.attrs;

import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Primary keys of table.
 */
public final class PrimaryKeys implements List<String> {

    /**
     * List of primary key column names.
     */
    @Delegate
    private final List<String> primaryKeys;

    /**
     * Ctor.
     * @param tableName Table name
     * @param jdbcTemplate Jdbc template
     */
    @SneakyThrows(SQLException.class)
    public PrimaryKeys(
            final String tableName,
            final JdbcTemplate jdbcTemplate
    ) {
        this.primaryKeys = PrimaryKeys.primaryKeysMeta(tableName, jdbcTemplate);
    }

    /**
     * Get primary keys.
     *
     * @param tableName    Table name
     * @param jdbcTemplate Jdbc template
     * @return Primary keys
     * @throws SQLException if failed
     */
    private static List<String> primaryKeysMeta(
            final String tableName,
            final JdbcTemplate jdbcTemplate
    ) throws SQLException {
        final List<String> keys = new ArrayList<>(16);
        try (final Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            try (final ResultSet rs = PrimaryKeys.primaryKeysMeta(connection, tableName)) {
                while (rs.next()) {
                    keys.add(
                            rs.getString("COLUMN_NAME")
                    );
                }
            }
        }
        return keys;
    }

    /**
     * Get primary keys meta.
     *
     * @param connection Sql connection
     * @param tableName  Table name
     * @return Metadata
     * @throws SQLException if failed
     */
    private static ResultSet primaryKeysMeta(
            final Connection connection,
            final String tableName
    ) throws SQLException {
        final DatabaseMetaData metaData = connection.getMetaData();
        return metaData.getPrimaryKeys(
                connection.getCatalog(),
                connection.getSchema(),
                tableName
        );
    }
}
