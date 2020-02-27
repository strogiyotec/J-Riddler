package com.jriddler.attrs;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
public final class BoolAttr implements AttributeDefinition {

    private final String name;

    private final boolean value;

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
