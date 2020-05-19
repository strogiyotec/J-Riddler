package com.jriddler.columns;

import com.jriddler.columns.jdbc.*;
import lombok.experimental.Delegate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Build column definition and store it in origin.
 */
public final class ColumnDefinitionBuilder implements ColumnDefinition {

    /**
     * Origin.
     */
    @Delegate
    private final ColumnDefinition origin;

    /**
     * Ctor.
     *
     * @param resultSet    ResultSet
     * @param customValues Custom values for columns
     * @throws SQLException If fails
     */
    public ColumnDefinitionBuilder(
            final ResultSet resultSet,
            final Map<String, String> customValues
    ) throws SQLException {
        this(
                resultSet.getInt("DATA_TYPE"),
                resultSet.getInt("COLUMN_SIZE"),
                resultSet.getString("COLUMN_NAME"),
                customValues

        );
    }

    /**
     * Ctor.
     *
     * @param type   Type
     * @param length Size
     * @param name   Name
     */
    @SuppressWarnings("ReturnCount")
    public ColumnDefinitionBuilder(
            final int type,
            final int length,
            final String name
    ) {
        this(
                type,
                length,
                name,
                Collections.emptyMap()
        );
    }

    /**
     * Ctor.
     *
     * @param type         Type
     * @param length       Size
     * @param name         Name
     * @param customValues Custom values for columns
     */
    @SuppressWarnings({"ReturnCount", "LineLength"})
    public ColumnDefinitionBuilder(
            final int type,
            final int length,
            final String name,
            final Map<String, String> customValues
    ) {
        this.origin = ColumnDefinitionBuilder.randomValueColumn(type, length, name, customValues);
    }

    /**
     * Creates column with random value.
     * TODO: fix CyclomaticComplexity
     *
     * @param type         Sql type id
     * @param length       Column length in bytes
     * @param name         Column name
     * @param customValues Custom values for columns
     * @return Random value columns
     */
    @SuppressWarnings({"ReturnCount", "LineLength"})
    private static ColumnDefinition randomValueColumn(
            final int type,
            final int length,
            final String name,
            final Map<String, String> customValues
    ) {
        final Optional<String> userDefinedValue =
                Optional.ofNullable(customValues.get(name));
        if (type == Types.INTEGER) {
            return userDefinedValue
                    .map(value -> new IntColumn(name, Integer.parseInt(value)))
                    .orElse(new IntColumn(name));
        }
        if (type == Types.VARCHAR) {
            return userDefinedValue
                    .map(value -> new VarCharColumn(name, length, value))
                    .orElse(new VarCharColumn(name, length));
        }
        if (type == Types.BIT) {
            return userDefinedValue
                    .map(value -> new BoolColumn(name, Boolean.parseBoolean(value)))
                    .orElse(new BoolColumn(name));
        }
        if (type == Types.BIGINT) {
            return userDefinedValue
                    .map(value -> new BigIntColumn(name, Long.parseLong(value)))
                    .orElse(new BigIntColumn(name));
        }
        if (type == Types.TIMESTAMP) {
            return userDefinedValue
                    .map(value -> new TimeStampColumn(name, value))
                    .orElse(new TimeStampColumn(name));
        }

        throw new IllegalArgumentException(
                String.format(
                        "Column with name [%s] doesn't exist",
                        name
                )
        );
    }
}
