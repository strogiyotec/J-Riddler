package com.jriddler.attrs;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
public final class IntAttr implements AttributeDefinition {

    private final String name;

    private final int value;


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
        return 4;
    }

    @Override
    public String name() {
        return this.name;
    }

}
