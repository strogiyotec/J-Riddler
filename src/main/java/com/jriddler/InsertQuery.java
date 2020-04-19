package com.jriddler;

import com.jriddler.attrs.ColumnName;
import lombok.AllArgsConstructor;

import java.util.Iterator;

/**
 * Create insert query for attributes.
 */
@AllArgsConstructor
public final class InsertQuery {

    /**
     * List of column names.
     */
    private final Iterable<? extends ColumnName> names;

    /**
     * Table name.
     */
    private final String tableName;


    /**
     * Create insert query.
     */
    public String create() {
        final StringBuilder insert = new StringBuilder(16);
        insert.append("INSERT INTO ")
                .append(this.tableName)
                .append("\n(");
        final StringBuilder values = new StringBuilder(16);
        values.append("VALUES \n(");
        final Iterator<? extends ColumnName> iterator = this.names.iterator();
        while (iterator.hasNext()) {
            final ColumnName columnName = iterator.next();
            insert.append(columnName.name());
            values.append("?");
            if (iterator.hasNext()) {
                values.append(",");
                insert.append(",");
            }
        }
        values.append(")\n");
        insert.append(")\n");
        return insert.append(values).toString();
    }
}
