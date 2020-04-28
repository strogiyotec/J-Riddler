package com.jriddler.columns;

import com.jriddler.columns.jdbc.*;
import lombok.experimental.Delegate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;

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
    @SuppressWarnings("CyclomaticComplexity")
    private static ColumnDefinition randomValueColumn(
            final int type,
            final int length,
            final String name,
            final Map<String, String> customValues
    ) {
        final ColumnDefinition column;
        if (type == Types.INTEGER) {
            if (customValues.containsKey(name)) {
                column = new IntColumn(
                        name,
                        Integer.parseInt(customValues.get(name))
                );
            } else {
                column = new IntColumn(name);
            }
        } else if (type == Types.VARCHAR) {
            if (customValues.containsKey(name)) {
                column = new VarCharColumn(
                        name,
                        length,
                        customValues.get(name)
                );
            } else {
                column = new VarCharColumn(name, length);
            }
        } else if (type == Types.BIT) {
            if (customValues.containsKey(name)) {
                column = new BoolColumn(
                        name,
                        Boolean.parseBoolean(customValues.get(name))
                );
            } else {
                column = new BoolColumn(name);
            }
        } else if (type == Types.BIGINT) {
            if (customValues.containsKey(name)) {
                column = new BigIntColumn(
                        name,
                        Long.parseLong(customValues.get(name))
                );
            } else {
                column = new BigIntColumn(name);
            }
        } else if (type == Types.TIMESTAMP) {
            if (customValues.containsKey(name)) {
                column = new TimeStampColumn(
                        name,
                        customValues.get(name)
                );
            } else {
                column = new TimeStampColumn(name);
            }
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "Column with name [%s] doesn't exist",
                            name
                    )
            );
        }
        return column;
    }
}
