package com.jriddler;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public final class SqlInsert implements SqlOperation<Boolean> {

    private final DataSource dataSource;

    private final List<AttributeDefinition> definitions;

    private final String tableName;

    @Override
    public Boolean perform() throws SQLException {
        try (final Connection connection = this.dataSource.getConnection()) {
            try (final PreparedStatement statement = connection.prepareStatement(this.query())) {
                for (int i = 1; i <= this.definitions.size(); i++) {
                    statement.setObject(i, this.definitions.get(i - 1).value());
                }
                return statement.execute();
            }
        }
    }

    private String query() {
        return new InsertQuery(this.definitions, this.tableName).build();
    }
}
