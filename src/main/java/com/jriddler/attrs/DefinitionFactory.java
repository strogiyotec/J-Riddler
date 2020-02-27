package com.jriddler.attrs;

import com.jriddler.attrs.*;

import java.sql.Types;

/**
 * Creates attribute definition.
 */
public final class DefinitionFactory {

    /**
     * Create definition from given params.
     *
     * @param type   Type
     * @param length Size
     * @param name   Name
     * @return Attribute definition
     */
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
        throw new IllegalArgumentException("bla");

    }
}
