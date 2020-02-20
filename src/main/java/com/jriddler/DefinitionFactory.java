package com.jriddler;

import java.sql.Types;

public final class DefinitionFactory {

    public AttributeDefinition create(final int type, final int length, final String name) {
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
