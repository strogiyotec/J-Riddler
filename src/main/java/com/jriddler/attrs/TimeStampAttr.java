package com.jriddler.attrs;

import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
public final class TimeStampAttr implements AttributeDefinition {

    private final String name;

    private final OffsetDateTime value;

    public TimeStampAttr(final String name) {
        this(
                name,
                OffsetDateTime.now()
        );
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String name() {
        return this.name;
    }
}
