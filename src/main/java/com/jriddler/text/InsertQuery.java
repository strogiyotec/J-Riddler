package com.jriddler.text;

import com.jriddler.columns.ColumnName;
import com.jriddler.text.Text;
import lombok.AllArgsConstructor;

import java.util.Iterator;

/**
 * Create insert query for table.
 */
@AllArgsConstructor
public final class InsertQuery implements Text {

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
     *
     * @return Insert query
     */
    @Override
    public String asString() {
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
