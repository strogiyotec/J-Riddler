package com.jriddler.attrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Decorate definition created by ResultSet params.
 */
public final class DynamicAttr implements AttributeDefinition {

    /**
     * Origin.
     */
    private final AttributeDefinition origin;

    /**
     * Ctor.
     *
     * @param resultSet ResultSet
     * @throws SQLException If fails
     */
    public DynamicAttr(
            final ResultSet resultSet
    ) throws SQLException {
        this(
                resultSet.getInt("DATA_TYPE"),
                resultSet.getInt("COLUMN_SIZE"),
                resultSet.getString("COLUMN_NAME")
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
    public DynamicAttr(
            final int type,
            final int length,
            final String name
    ) {
        if (type == Types.INTEGER) {
            this.origin = new IntAttr(name);
        } else if (type == Types.VARCHAR) {
            this.origin = new VarCharAttr(name, length);
        } else if (type == Types.BIT) {
            this.origin = new BoolAttr(name);
        } else if (type == Types.BIGINT) {
            this.origin = new BigIntAttr(name);
        } else if (type == Types.TIMESTAMP) {
            this.origin = new TimeStampAttr(name);
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "Attribute with name [%s] is not supported",
                            name
                    )
            );
        }
    }

    @Override
    public Object value() {
        return this.origin.value();
    }

    @Override
    public int size() {
        return this.origin.size();
    }

    @Override
    public String name() {
        return this.origin.name();
    }
}
