package com.jriddler.cli;

/**
 * Database settings.
 */
public interface DbSettings {

    /**
     * Db password.
     *
     * @return Password
     */
    String getPassword();

    /**
     * Db username.
     *
     * @return Username
     */
    String getUsername();

    /**
     * Db host.
     *
     * @return Host
     */
    String getHost();

    /**
     * Db port.
     *
     * @return Port
     */
    int getPort();

    /**
     * Db name.
     *
     * @return Db name
     */
    String getDbName();
}
