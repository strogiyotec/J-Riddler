package com.jriddler.sql;

import com.jriddler.InsertQuery;
import com.jriddler.attrs.AttributeDefinition;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Executes insert query.
 */
@AllArgsConstructor
public final class SqlInsert implements SqlOperation<Boolean> {

    /**
     * Data source.
     */
    private final DataSource dataSource;

    /**
     * List of attrs.
     */
    private final List<AttributeDefinition> definitions;

    /**
     * Table name.
     */
    private final String tableName;

    @Override
    public Boolean perform() throws SQLException {
        try (final Connection connection = this.dataSource.getConnection()) {
            try (
                    final PreparedStatement statement =
                            connection.prepareStatement(this.query())
            ) {
                for (int i = 1; i <= this.definitions.size(); i++) {
                    statement.setObject(i, this.definitions.get(i - 1).value());
                }
                return statement.execute();
            }
        }
    }

    /**
     * Create insert query.
     *
     * @return Insert query
     */
    private String query() {
        return new InsertQuery(this.definitions, this.tableName).build();
    }
}
