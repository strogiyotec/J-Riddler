package com.jriddler.columns;

import java.sql.Types;

/**
 * Definition of column in db.
 */
@SuppressWarnings("InterfaceIsType")
public interface ColumnDefinition extends ColumnName, ColumnValue {

    /**
     * Check if given sql type is int.
     *
     * @param type Sql type
     * @return True if type is int
     */
    static boolean isInt(final int type) {
        return type == Types.INTEGER
                || type == Types.TINYINT
                || type == Types.SMALLINT;
    }

    /**
     * Check if given sql type is string.
     *
     * @param type Sql type
     * @return True if type is string
     */
    static boolean isString(final int type) {
        return type == Types.VARCHAR
                || type == Types.CHAR
                || type == Types.NVARCHAR
                || type == Types.NCHAR;
    }
}
