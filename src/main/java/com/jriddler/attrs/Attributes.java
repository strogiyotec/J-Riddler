package com.jriddler.attrs;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
public final class Attributes implements Iterable<AttributeDefinition> {

    private final String tableName;

    private final JdbcTemplate jdbcTemplate;

    @Override
    @SneakyThrows(SQLException.class)
    public Iterator<AttributeDefinition> iterator() {
        final List<AttributeDefinition> attrs = new ArrayList<>(16);
        try (final Connection connection = this.jdbcTemplate.getDataSource().getConnection()) {
            final DatabaseMetaData metaData = connection.getMetaData();
            try (final ResultSet columns = this.columnsMetaData(connection, metaData)) {
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
        return attrs.iterator();
    }

    private ResultSet columnsMetaData(
            final Connection connection,
            final DatabaseMetaData metaData
    ) throws SQLException {
        return metaData.getColumns(
                connection.getCatalog(),
                connection.getSchema(),
                this.tableName,
                null
        );
    }
}
