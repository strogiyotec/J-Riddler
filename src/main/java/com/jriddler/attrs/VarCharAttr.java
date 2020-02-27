package com.jriddler.attrs;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class VarCharAttr implements AttributeDefinition {

    private final String name;

    private final int length;

    private final String value;

    public VarCharAttr(final String name, final int length) {
        this.name = name;
        if (length == Integer.MAX_VALUE) {
            this.length = 10;
        } else {
            this.length = length;
        }
        this.value = new Faker().regexify(String.format("[a-z1-9]{%d}", this.length));
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public String name() {
        return this.name;
    }
}
