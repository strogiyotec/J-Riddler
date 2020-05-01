package com.jriddler.cli;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Db settings from cli.
 */
@Getter
public final class UserInput implements DbSettings {

    /**
     * User specified parameters.
     * Key - Param name
     * Value -Param value as string
     */
    @SuppressWarnings("LineLength")
    @DynamicParameter(
            names = "-V",
            description = "Predefined values to use during insert instead of random data"
    )
    private Map<String, String> userValues = new HashMap<>();

    /**
     * Default db port.
     */
    private static final int DEFAULT_PORT = 5432;

    /**
     * Db host.
     */
    @Parameter(names = "-host", description = "Database host")
    private String host = "localhost";

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

    @Parameter(names = "-version", description = "Print current version")
    private boolean version = false;
}
