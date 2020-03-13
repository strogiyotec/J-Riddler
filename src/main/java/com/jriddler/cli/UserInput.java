package com.jriddler.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;

/**
 * Db settings from cli.
 */
@Getter
public final class UserInput {

    /**
     * Default db port.
     */
    private static final int DEFAULT_PORT = 5432;

    /**
     * Db host.
     */
    @Parameter(names = "-host", description = "Database host")
    private String dbHost = "localhost";

    /**
     * Db port.
     */
    @Parameter(names = "-port", description = "Database port")
    private int port = DEFAULT_PORT;

    /**
     * Db user.
     */
    @Parameter(names = "-name", description = "User name")
    private String username;

    /**
     * Table name.
     */
    @Parameter(names = "-table", description = "Table name")
    private String table;

    /**
     * Db password.
     */
    @Parameter(
            names = "-password",
            description = "User password",
            password = true
    )
    private String password;

    /**
     * Database name.
     */
    @Parameter(names = "-db", description = "Database name")
    private String dbName;

}
