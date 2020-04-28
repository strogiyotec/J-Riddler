package com.jriddler.columns.jdbc;

import com.jriddler.columns.ColumnDefinition;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Big integer columns.
 */
@AllArgsConstructor
public final class BigIntColumn implements ColumnDefinition {

    /**
     * Name.
     */
    private final String name;

    /**
     * Value.
     */
    private final long value;

    /**
     * Ctor that create random value.
     *
     * @param name Column name
     */
    @SuppressWarnings("MagicNumber")
    public BigIntColumn(final String name) {
        this(
                name,
                ThreadLocalRandom.current().nextLong(0, 10)
        );
    }

    @Override
    public Long value() {
        return this.value;
    }

    @Override
    public int size() {
        return Long.BYTES;
    }

    @Override
    public String name() {
        return this.name;
    }

}
