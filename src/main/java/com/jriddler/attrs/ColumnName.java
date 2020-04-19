package com.jriddler.attrs;

import java.util.ArrayList;
import java.util.List;

/**
 * Column name.
 */
public interface ColumnName {

    /**
     * Column name.
     *
     * @return Name
     */
    String name();

    /**
     * Create list of names from iterable.
     *
     * @param names Names
     * @return List of names
     */
    static List<ColumnName> names(final Iterable<? extends ColumnName> names) {
        final List<ColumnName> list = new ArrayList<>();
        names.forEach(list::add);
        return list;
    }
}
