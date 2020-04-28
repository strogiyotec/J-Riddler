package com.jriddler.columns.jdbc;

import com.jriddler.columns.ColumnDefinition;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Bool columns.
 */
@AllArgsConstructor
public final class BoolColumn implements ColumnDefinition {

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
    public BoolColumn(final String name) {
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
