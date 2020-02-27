package com.jriddler.attrs;

/**
 * Definition of column in db.
 */
public interface AttributeDefinition {

    /**
     * Value of column.
     *
     * @return Value
     */
    Object value();

    /**
     * Size of the value in bytes.
     *
     * @return Size
     */
    int size();

    /**
     * Name of the column in table.
     *
     * @return Name
     */
    String name();

}
