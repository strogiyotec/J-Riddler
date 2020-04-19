package com.jriddler.attrs;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a value for column.
 */
public interface ColumnValue {

    /**
     * Column value.
     *
     * @return Value
     */
    Object value();

    /**
     * Column size in bytes.
     *
     * @return Size
     */
    int size();

    /**
     * Create array of values from iterable.
     *
     * @param values Values
     * @return Array of values
     */
    static Object[] values(final Iterable<? extends ColumnValue> values) {
        final List<Object> list = new ArrayList<>(16);
        values.forEach(value -> list.add(value.value()));
        return list.toArray(new Object[0]);
    }
}
