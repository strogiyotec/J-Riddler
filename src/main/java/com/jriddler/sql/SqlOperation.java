package com.jriddler.sql;

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
     */
    T perform();
}
