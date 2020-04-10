package com.jriddler.attrs;

import com.jriddler.cli.UserAttribute;
import lombok.experimental.Delegate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

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
            final List<UserAttribute> userAttrs
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
                Collections.emptyList()
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
            final List<UserAttribute> userAttributes
    ) {
        if (!userAttributes.isEmpty()) {
            final UserAttribute userAttribute = this.userAttr(name, userAttributes);
            if (userAttribute == null) {
                this.origin = AttrBuilder.randomAttr(type, length, name);
            } else {
                this.origin = AttrBuilder.userDefinedAttr(type, length, name, userAttribute.getValue());
            }
        } else {
            this.origin = AttrBuilder.randomAttr(type, length, name);
        }
    }


    /**
     * Fetches user attr if exists.
     *
     * @param name           Attr name
     * @param userAttributes List of Attrs
     * @return User defined attr or null if not present
     */
    private UserAttribute userAttr(
            final String name,
            final List<UserAttribute> userAttributes
    ) {
        return userAttributes.stream()
                .filter(attr -> attr.getKey().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Creates attribute with user defined value.
     *
     * @param type   Sql type id
     * @param length Attribute length in bytes
     * @param name   Attr name
     * @param value  User defined value
     * @return User defined attr
     */
    private static AttributeDefinition userDefinedAttr(
            final int type,
            final int length,
            final String name,
            final String value
    ) {
        final AttributeDefinition attr;
        if (type == Types.INTEGER) {
            attr = new IntAttr(name, Integer.parseInt(value));
        } else if (type == Types.VARCHAR) {
            attr = new VarCharAttr(name, length, value);
        } else if (type == Types.BIT) {
            attr = new BoolAttr(name, Boolean.parseBoolean(value));
        } else if (type == Types.BIGINT) {
            attr = new BigIntAttr(name);
        } else if (type == Types.TIMESTAMP) {
            attr = new TimeStampAttr(name, value);
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

    /**
     * Creates attribute with random value.
     *
     * @param type   Sql type id
     * @param length Attribute length in bytes
     * @param name   Attribute name
     * @return Random value attr
     */
    private static AttributeDefinition randomAttr(
            final int type,
            final int length,
            final String name
    ) {
        final AttributeDefinition attr;
        if (type == Types.INTEGER) {
            attr = new IntAttr(name);
        } else if (type == Types.VARCHAR) {
            attr = new VarCharAttr(name, length);
        } else if (type == Types.BIT) {
            attr = new BoolAttr(name);
        } else if (type == Types.BIGINT) {
            attr = new BigIntAttr(name);
        } else if (type == Types.TIMESTAMP) {
            attr = new TimeStampAttr(name);
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
