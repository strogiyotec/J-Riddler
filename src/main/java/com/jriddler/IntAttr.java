package com.jriddler;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
public final class IntAttr implements AttributeDefinition {


    private final String name;

    @Override
    public Integer value() {
        return ThreadLocalRandom.current().nextInt(0, 10);
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
