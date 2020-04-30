package com.jriddler.sql;

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

    @Override
    public void listen(final ExecutionDetails executionDetails) {
        if (executionDetails.success()) {
            log.log(
                    Level.INFO,
                    String.join(
                            "\n",
                            "\nExecution time {0}ms",
                            "Insert query:",
                            "{1}"
                    ),
                    new Object[]{
                            executionDetails.executionTimeMs(),
                            executionDetails.sql(),
                    }
            );
        }
    }
}
