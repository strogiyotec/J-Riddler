package com.jriddler;

import com.jriddler.attrs.AttributeDefinition;
import com.jriddler.attrs.DynamicAttr;

import java.sql.*;

/**
 * Main endpoint.
 */
public final class Main {
    public static void main(final String[] args) throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/test", "postgres", "123");
        final DatabaseMetaData metaData = connection.getMetaData();
        try (final ResultSet resultSet = metaData.getColumns("public", null, "test", null)) {
            while (resultSet.next()) {
                final AttributeDefinition definition = new DynamicAttr(
                        resultSet.getInt("DATA_TYPE"),
                        resultSet.getInt("COLUMN_SIZE"),
                        resultSet.getString("COLUMN_NAME")
                );
                System.out.println("Random value: " + definition.value() + " Name: " + definition.name());
            }
        }
    }
}
