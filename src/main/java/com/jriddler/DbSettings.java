package com.jriddler;

import com.beust.jcommander.Parameter;
import lombok.Getter;

/**
 * Db settings from cli.
 */
@Getter
public final class DbSettings {

    /**
     * Table name.
     */
    @Parameter(names = "-table", description = "Table name")
    private String table;

    /**
     * Db host.
     */
    @Parameter(names = "-host", description = "Database host")
    private String dbHost;

    /**
     * Db port.
     */
    @Parameter(names = "-port", description = "Database port")
    private int port;

    /**
     * Db user.
     */
    @Parameter(names = "-name", description = "User name")
    private String username;

    /**
     * Db password.
     */
    @Parameter(
            names = "-password",
            description = "User password",
            password = true
    )
    private String password;

}
