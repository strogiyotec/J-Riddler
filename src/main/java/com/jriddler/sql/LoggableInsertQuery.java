package com.jriddler.sql;

import lombok.AllArgsConstructor;
import org.codejargon.fluentjdbc.api.query.listen.AfterQueryListener;
import org.codejargon.fluentjdbc.api.query.listen.ExecutionDetails;

/**
 * Log successful insert query.
 */
@AllArgsConstructor
public final class LoggableInsertQuery implements AfterQueryListener {

    @Override
    public void listen(final ExecutionDetails executionDetails) {
        if (executionDetails.success()) {
            System.out.printf(
                    String.join(
                            "\n",
                            "\nExecution time %dms",
                            "Insert query:",
                            "%s"
                    ),
                    executionDetails.executionTimeMs(),
                    executionDetails.sql());
        }
    }
}
