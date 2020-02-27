package com.jriddler.attrs;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;

/**
 * Varchar attr.
 */
@AllArgsConstructor
public final class VarCharAttr implements AttributeDefinition {

    /**
     * Name.
     */
    private final String name;

    /**
     * Var char length.
     */
    private final int length;

    /**
     * Value.
     */
    private final String value;

    /**
     * Ctor that creates random string.
     * @param name Column name
     * @param length Varchar length
     */
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
