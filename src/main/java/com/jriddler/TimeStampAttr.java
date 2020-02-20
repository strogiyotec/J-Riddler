package com.jriddler;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public final class TimeStampAttr implements AttributeDefinition {

    private final String name;

    @Override
    public Object value() {
        return new Date();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String name() {
        return null;
    }
}
