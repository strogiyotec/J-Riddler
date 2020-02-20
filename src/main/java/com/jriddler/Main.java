package com.jriddler;

import java.sql.*;

/**
 * Main endpoint.
 */
public final class Main {
    public static void main(final String[] args) throws SQLException {
        final DefinitionFactory factory = new DefinitionFactory();
        final Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/test", "postgres", "123");
        final DatabaseMetaData metaData = connection.getMetaData();
        try (final ResultSet resultSet = metaData.getColumns("public", null, "test", null)) {
            while (resultSet.next()) {
                final AttributeDefinition definition = factory.create(
                        resultSet.getInt("DATA_TYPE"),
                        resultSet.getInt("COLUMN_SIZE"),
                        resultSet.getString("COLUMN_NAME")
                );
                System.out.println("Random value: " + definition.value() + " Name: " + definition.name());
            }
        }
    }
}
