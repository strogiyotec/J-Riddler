package com.jriddler.sql;

import java.sql.SQLException;

/**
 * Sql operations.
 *
 * @param <T> Type
 */
public interface SqlOperation<T> {

    /**
     * Execute sql.
     *
     * @return Type
     * @throws SQLException if failed
     */
    T perform() throws SQLException;
}
