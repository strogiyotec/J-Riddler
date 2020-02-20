package com.jriddler;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
public final class BigIntAttr implements AttributeDefinition {

    private final String name;

    @Override
    public Long value() {
        return ThreadLocalRandom.current().nextLong(0, 10);
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
