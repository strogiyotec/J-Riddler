package com.jriddler.columns.jdbc;

import com.jriddler.columns.ColumnDefinition;
import com.mifmif.common.regex.Generex;

/**
 * Varchar columns.
 */
public final class VarCharColumn implements ColumnDefinition {

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
     *
     * @param name   Column name
     * @param length Varchar length
     */
    @SuppressWarnings("MagicNumber")
    public VarCharColumn(final String name, final int length) {
        this.name = name;
        if (length == Integer.MAX_VALUE) {
            this.length = 10;
        } else {
            this.length = length;
        }
        this.value = new Generex(
                String.format(
                        "[a-z1-9]{%d}",
                        this.length
                )
        ).random();
    }

    /**
     * Ctor.
     *
     * @param name   Column name
     * @param length Column length
     * @param value  Column value
     */
    @SuppressWarnings("LineLength")
    public VarCharColumn(
            final String name,
            final int length,
            final String value
    ) {
        if (value.length() > length) {
            throw new IllegalArgumentException(
                    String.format(
                            "Value [%s] for columns [%s] exceeded max length limit [%d]",
                            value,
                            name,
                            length
                    )
            );
        }
        this.name = name;
        this.length = length;
        this.value = value;
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
