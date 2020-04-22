package com.jriddler.attrs;

import com.jriddler.attrs.jdbc.*;
import lombok.experimental.Delegate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;

/**
 * Build attr and store it in origin.
 */
public final class AttrBuilder implements AttributeDefinition {

    /**
     * Origin.
     */
    @Delegate
    private final AttributeDefinition origin;

    /**
     * Ctor.
     *
     * @param resultSet ResultSet
     * @param userAttrs User defined attribute values
     * @throws SQLException If fails
     */
    public AttrBuilder(
            final ResultSet resultSet,
            final Map<String, String> userAttrs
    ) throws SQLException {
        this(
                resultSet.getInt("DATA_TYPE"),
                resultSet.getInt("COLUMN_SIZE"),
                resultSet.getString("COLUMN_NAME"),
                userAttrs

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
    public AttrBuilder(
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
     * @param type           Type
     * @param length         Size
     * @param name           Name
     * @param userAttributes User defined attribute values
     */
    @SuppressWarnings({"ReturnCount", "LineLength"})
    public AttrBuilder(
            final int type,
            final int length,
            final String name,
            final Map<String, String> userAttributes
    ) {
        this.origin = AttrBuilder.randomAttr(type, length, name, userAttributes);
    }

    /**
     * Creates attribute with random value.
     * TODO: fix CyclomaticComplexity
     *
     * @param type            Sql type id
     * @param length          Attribute length in bytes
     * @param name            Attribute name
     * @param userDefinedAttr User defined attrs
     * @return Random value attr
     */
    @SuppressWarnings("CyclomaticComplexity")
    private static AttributeDefinition randomAttr(
            final int type,
            final int length,
            final String name,
            final Map<String, String> userDefinedAttr
    ) {
        final AttributeDefinition attr;
        if (type == Types.INTEGER) {
            if (userDefinedAttr.containsKey(name)) {
                attr = new IntAttr(
                        name,
                        Integer.parseInt(userDefinedAttr.get(name))
                );
            } else {
                attr = new IntAttr(name);
            }
        } else if (type == Types.VARCHAR) {
            if (userDefinedAttr.containsKey(name)) {
                attr = new VarCharAttr(
                        name,
                        length,
                        userDefinedAttr.get(name)
                );
            } else {
                attr = new VarCharAttr(name, length);
            }
        } else if (type == Types.BIT) {
            if (userDefinedAttr.containsKey(name)) {
                attr = new BoolAttr(
                        name,
                        Boolean.parseBoolean(userDefinedAttr.get(name))
                );
            } else {
                attr = new BoolAttr(name);
            }
        } else if (type == Types.BIGINT) {
            if (userDefinedAttr.containsKey(name)) {
                attr = new BigIntAttr(
                        name,
                        Long.parseLong(userDefinedAttr.get(name))
                );
            } else {
                attr = new BigIntAttr(name);
            }
        } else if (type == Types.TIMESTAMP) {
            if (userDefinedAttr.containsKey(name)) {
                attr = new TimeStampAttr(
                        name,
                        userDefinedAttr.get(name)
                );
            } else {
                attr = new TimeStampAttr(name);
            }
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "Attribute with name [%s] is not supported",
                            name
                    )
            );
        }
        return attr;
    }
}
