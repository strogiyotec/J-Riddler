package com.jriddler.cli;

import com.beust.jcommander.Parameter;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * Db settings from cli.
 */
@Getter
public final class UserInput {

    /**
     * List of table attrs.
     */
    @SuppressWarnings("LineLength")
    @Parameter(
            names = "-attrs",
            description = "Dynamic attributes to use during insert instead of random data",
            listConverter = UserAttrConverter.class
    )
    private List<UserAttribute> userAttributes = Collections.emptyList();

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
