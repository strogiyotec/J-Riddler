package com.jriddler.columns;

import com.jriddler.columns.jdbc.*;
import com.jriddler.utils.Storage;
import lombok.experimental.Delegate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
     * @param resultSet           ResultSet
     * @param customValuesStorage Custom values for columns
     * @throws SQLException If fails
     */
    public ColumnDefinitionBuilder(
            final ResultSet resultSet,
            final Storage customValuesStorage
    ) throws SQLException {
        this(
                resultSet.getInt("DATA_TYPE"),
                resultSet.getInt("COLUMN_SIZE"),
                resultSet.getString("COLUMN_NAME"),
                customValuesStorage

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
                Storage.EMPTY
        );
    }

    /**
     * Ctor.
     *
     * @param type                Type
     * @param length              Size
     * @param name                Name
     * @param customValuesStorage Custom values for columns
     */
    @SuppressWarnings({"ReturnCount", "LineLength"})
    public ColumnDefinitionBuilder(
            final int type,
            final int length,
            final String name,
            final Storage customValuesStorage
    ) {
        this.origin = ColumnDefinitionBuilder.randomValueColumn(type, length, name, customValuesStorage);
    }

    /**
     * Creates column with random value.
     * TODO: fix CyclomaticComplexity
     *
     * @param type                Sql type id
     * @param length              Column length in bytes
     * @param name                Column name
     * @param customValuesStorage Custom values for columns
     * @return Random value columns
     */
    @SuppressWarnings({"ReturnCount", "LineLength"})
    private static ColumnDefinition randomValueColumn(
            final int type,
            final int length,
            final String name,
            final Storage customValuesStorage
    ) {
        final Optional<String> userDefinedValue = customValuesStorage.get(name);
        //int
        if (ColumnDefinition.isInt(type)) {
            return userDefinedValue
                    .map(value -> new IntColumn(name, Integer.parseInt(value)))
                    .orElse(new IntColumn(name));
        }
        //string
        if (ColumnDefinition.isString(type)) {
            return userDefinedValue
                    .map(value -> new VarCharColumn(name, length, value))
                    .orElse(new VarCharColumn(name, length));
        }
        //bit
        if (type == Types.BIT) {
            return userDefinedValue
                    .map(value -> new BoolColumn(name, Boolean.parseBoolean(value)))
                    .orElse(new BoolColumn(name));
        }
        //long
        if (type == Types.BIGINT) {
            return userDefinedValue
                    .map(value -> new BigIntColumn(name, Long.parseLong(value)))
                    .orElse(new BigIntColumn(name));
        }
        //dates
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
