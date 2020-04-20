package com.jriddler.sql;

import com.jriddler.attrs.AttributeDefinition;
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
     * List of inserted attributes.
     */
    private final Iterable<AttributeDefinition> attributes;

    @Override
    public void listen(final ExecutionDetails executionDetails) {
        if (executionDetails.success()) {
            log.log(
                    Level.INFO,
                    String.join(
                            "\n",
                            "\nExecution time {0}ms",
                            "Insert query:",
                            "{1}Attributes:",
                            "{2}"
                    ),
                    new Object[]{
                            executionDetails.executionTimeMs(),
                            executionDetails.sql(),
                            this.attrLog(),
                    }
            );
        }
    }

    /**
     * String representation of all attributes.
     *
     * @return String representation
     */
    private String attrLog() {
        final StringBuilder builder = new StringBuilder();
        this.attributes.forEach(attr ->
                builder
                        .append("Name=")
                        .append(attr.name())
                        .append(", Value=")
                        .append(attr.value())
                        .append("\n"));
        return builder.toString();
    }
}
