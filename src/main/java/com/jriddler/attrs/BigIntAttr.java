package com.jriddler.attrs;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
public final class BigIntAttr implements AttributeDefinition {

    private final String name;

    private final long value;

    public BigIntAttr(final String name) {
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
        return 8;
    }

    @Override
    public String name() {
        return this.name;
    }

}
