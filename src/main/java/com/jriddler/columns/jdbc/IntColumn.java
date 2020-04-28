package com.jriddler.columns.jdbc;

import com.jriddler.columns.ColumnDefinition;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Int columns.
 */
@AllArgsConstructor
public final class IntColumn implements ColumnDefinition {

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
    @SuppressWarnings("MagicNumber")
    public IntColumn(final String name) {
        this(
                name,
                ThreadLocalRandom.current().nextInt(
                        0,
                        Integer.MAX_VALUE / 2
                )
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
