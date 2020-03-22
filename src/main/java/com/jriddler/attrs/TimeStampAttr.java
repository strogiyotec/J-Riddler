package com.jriddler.attrs;

import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Timestamp with time zone attr.
 */
@AllArgsConstructor
public final class TimeStampAttr implements AttributeDefinition {

    /**
     * Default formatter for date from user CLI.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

    /**
     * Ctor that creates current time value.
     *
     * @param value Predefined value
     * @param name  Column name
     */
    public TimeStampAttr(final String name, final String value) {
        this(
                name,
                OffsetDateTime.parse(value, FORMATTER)
        );
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public int size() {
        return 35;
    }

    @Override
    public String name() {
        return this.name;
    }
}
