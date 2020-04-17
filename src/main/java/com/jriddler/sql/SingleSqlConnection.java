package com.jriddler.sql;

import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection which doesn't do anything on close.
 * It will close origin connection when destroy is called
 */
@AllArgsConstructor
public final class SingleSqlConnection implements Connection {

    /**
     * Original connection.
     */
    @Delegate(excludes = AutoCloseable.class)
    private final Connection origin;

    /**
     * Do nothing when close is called.
     */
    @Override
    public void close() {
        //do nothing
    }

    /**
     * Commit and close origin.
     *
     * @throws SQLException          If failed
     * @throws IllegalStateException If already closed
     */
    public void destroyConnection() throws SQLException {
        if (this.origin.isClosed()) {
            throw new IllegalStateException("Connection is already closed");
        }
        if (!this.origin.getAutoCommit()) {
            this.origin.commit();
        }
        this.origin.close();
    }
}
