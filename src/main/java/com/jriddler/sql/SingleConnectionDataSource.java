package com.jriddler.sql;

import lombok.extern.java.Log;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataSource which always gives the same connection.
 * This connection will be committed and closed when destroy
 * method is called
 */
@Log
public final class SingleConnectionDataSource implements DataSource {

    /**
     * Lock monitor.
     */
    private final Object monitor = new Object();

    /**
     * Single sql connection.
     */
    private SingleSqlConnection connection;

    /**
     * Jdbc username.
     */
    private final String username;

    /**
     * Jdbc password.
     */
    private final String password;

    /**
     * Jdbc url.
     */
    private final String url;

    /**
     * Autocommit.
     */
    private final boolean autoCommit;

    /**
     * Ctor.
     * Set autocommit to false
     *
     * @param username Username
     * @param password Password
     * @param url      Url
     */
    public SingleConnectionDataSource(
            final String username,
            final String password,
            final String url
    ) {
        this(
                username,
                password,
                url,
                false
        );
    }

    /**
     * Ctor.
     *
     * @param connection Preconfigured connection
     * @param username   Username
     * @param password   Password
     * @param url        Url
     */
    public SingleConnectionDataSource(
            final SingleSqlConnection connection,
            final String username,
            final String password,
            final String url
    ) {
        this(
                username,
                password,
                url
        );
        this.connection = connection;
    }

    /**
     * Ctor.
     *
     * @param username   Username
     * @param password   Password
     * @param url        Url
     * @param autoCommit Autocommit mode
     */
    public SingleConnectionDataSource(
            final String username,
            final String password,
            final String url,
            final boolean autoCommit
    ) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.autoCommit = autoCommit;
    }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (this.monitor) {
            this.createConnection();
        }
        return this.connection;
    }

    /**
     * Create connection.
     * Ignore username and pass
     * because these properties are already injected
     *
     * @param connectionUserName Username to ignore
     * @param connectionPassword Password to ignore
     * @return Connection
     * @throws SQLException If failed
     */
    @Override
    public Connection getConnection(
            final String connectionUserName,
            final String connectionPassword
    ) throws SQLException {
        if (!this.username.equals(connectionUserName)) {
            throw new IllegalArgumentException("New value for username");
        }
        if (!this.password.equals(connectionPassword)) {
            throw new IllegalArgumentException("New value for password");
        }
        return this.getConnection();
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("unwrap() is not supported");
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException(
                "isWrapperFor() is not supported"
        );
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException(
                "getLogWrite() is not supported"
        );
    }

    @Override
    public void setLogWriter(final PrintWriter out) throws SQLException {

        throw new UnsupportedOperationException(
                "setLogWriter() is not supported"
        );
    }

    @Override
    public void setLoginTimeout(final int seconds) throws SQLException {

        throw new UnsupportedOperationException(
                "setLoginTimeout() is not supported"
        );
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException(
                "getLoginTimeout() is not supported"
        );
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException(
                "getParentLogger() is not supported"
        );
    }

    /**
     * Close single connection.
     *
     * @throws SQLException if failed
     */
    public void destroy() throws SQLException {
        synchronized (this.monitor) {
            if (this.connection != null) {
                this.connection.destroyConnection();
                log.log(Level.INFO, "Connection was closed");
            } else {
                log.log(Level.INFO, "Connection doesn't exist, can't be closed");
            }
        }
    }

    /**
     * Create connection if doesn't exist.
     *
     * @throws SQLException if failed
     */
    private void createConnection() throws SQLException {
        if (this.connection == null) {
            final Connection origin = DriverManager.getConnection(
                    this.url,
                    this.username,
                    this.password
            );
            origin.setAutoCommit(this.autoCommit);
            this.connection = new SingleSqlConnection(
                    origin
            );
        } else if (this.connection.isClosed()) {
            throw new IllegalStateException(
                    "Single connection is already closed"
            );
        }
    }
}


