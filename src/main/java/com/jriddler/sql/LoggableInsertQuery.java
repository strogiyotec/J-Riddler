package com.jriddler.sql;

import com.jriddler.columns.ColumnDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.codejargon.fluentjdbc.api.query.listen.AfterQueryListener;
import org.codejargon.fluentjdbc.api.query.listen.ExecutionDetails;

import java.util.logging.Level;

/**
 * Log successful insert query.
 */
@AllArgsConstructor
@Log
public final class LoggableInsertQuery implements AfterQueryListener {

    /**
     * List of inserted columns.
     */
    private final Iterable<ColumnDefinition> columns;

    @Override
    public void listen(final ExecutionDetails executionDetails) {
        if (executionDetails.success()) {
            log.log(
                    Level.INFO,
                    String.join(
                            "\n",
                            "\nExecution time {0}ms",
                            "Insert query:",
                            "{1}Columns:",
                            "{2}"
                    ),
                    new Object[]{
                            executionDetails.executionTimeMs(),
                            executionDetails.sql(),
                            this.columnsAsString(),
                    }
            );
        }
    }

    /**
     * String representation of all columns.
     *
     * @return String representation
     */
    private String columnsAsString() {
        final StringBuilder builder = new StringBuilder();
        this.columns.forEach(column ->
                builder
                        .append("Name=")
                        .append(column.name())
                        .append(", Value=")
                        .append(column.value())
                        .append("\n"));
        return builder.toString();
    }
}
