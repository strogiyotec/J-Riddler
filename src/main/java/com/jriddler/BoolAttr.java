package com.jriddler;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
public final class BoolAttr implements AttributeDefinition {

    private final String name;

    @Override
    public Boolean value() {
        return ThreadLocalRandom.current().nextBoolean();
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
