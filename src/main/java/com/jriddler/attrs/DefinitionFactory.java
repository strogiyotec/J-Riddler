package com.jriddler.attrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Creates attribute definition.
 */
public final class DefinitionFactory {

    /**
     * Create attribute definition from result set.
     *
     * @param resultSet ResultSet
     * @return Attr definition
     * @throws SQLException If fails
     */
    public AttributeDefinition create(
            final ResultSet resultSet
    ) throws SQLException {
        return this.create(
                resultSet.getInt("DATA_TYPE"),
                resultSet.getInt("COLUMN_SIZE"),
                resultSet.getString("COLUMN_NAME")
        );
    }

    /**
     * Create definition from given params.
     *
     * @param type   Type
     * @param length Size
     * @param name   Name
     * @return Attribute definition
     */
    @SuppressWarnings("ReturnCount")
    public AttributeDefinition create(
            final int type,
            final int length,
            final String name
    ) {
        if (type == Types.INTEGER) {
            return new IntAttr(name);
        }
        if (type == Types.VARCHAR) {
            return new VarCharAttr(name, length);
        }
        if (type == Types.BIT) {
            return new BoolAttr(name);
        }
        if (type == Types.BIGINT) {
            return new BigIntAttr(name);
        }
        if (type == Types.TIMESTAMP) {
            return new TimeStampAttr(name);
        }
        throw new IllegalArgumentException(
                String.format(
                        "Attribute with name %s is not suported",
                        name
                )
        );

    }
}
