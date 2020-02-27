package com.jriddler.attrs;

import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Timestamp with time zone attr.
 */
@AllArgsConstructor
public final class TimeStampAttr implements AttributeDefinition {

    /**
     * Name.
     */
    private final String name;

    /**
     * Value.
     */
    private final OffsetDateTime value;

    /**
     * Ctor that creates current time value.
     *
     * @param name Column name
     */
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
        return 35;
    }

    @Override
    public String name() {
        return this.name;
    }
}
