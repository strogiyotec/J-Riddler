package com.jriddler.attrs;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Int attr.
 */
@AllArgsConstructor
public final class IntAttr implements AttributeDefinition {

    /**
     * Name.
     */
    private final String name;

    /**
     * Value.
     */
    private final int value;

    /**
     * Ctor that creates random val.
     *
     * @param name Column name
     */
    public IntAttr(final String name) {
        this(
                name,
                ThreadLocalRandom.current().nextInt(0, 10)
        );
    }

    @Override
    public Integer value() {
        return this.value;
    }

    @Override
    public int size() {
        return Integer.BYTES;
    }

    @Override
    public String name() {
        return this.name;
    }

}
