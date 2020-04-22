package com.jriddler.attrs.jdbc;

import com.jriddler.attrs.AttributeDefinition;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Bool attr.
 */
@AllArgsConstructor
public final class BoolAttr implements AttributeDefinition {

    /**
     * Name.
     */
    private final String name;

    /**
     * Value.
     */
    private final boolean value;

    /**
     * Ctor that creates random value.
     * @param name Column name
     */
    public BoolAttr(final String name) {
        this(
                name,
                ThreadLocalRandom.current().nextBoolean()
        );
    }

    @Override
    public Boolean value() {
        return this.value;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String name() {
        return this.name;
    }
}
