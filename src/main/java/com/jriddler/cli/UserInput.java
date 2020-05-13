package com.jriddler.cli;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Random row settings from cli.
 */
@Getter
@NoArgsConstructor
public final class UserInput implements DbSettings {

    /**
     * Default db host.
     */
    private static final String DEFAULT_HOST = "localhost";

    /**
     * Default db port.
     */
    private static final int DEFAULT_PORT = 5432;

    /**
     * Ctor.
     * Creates input from cli argc
     *
     * @param args Cli args
     */
    public UserInput(final String[] args) {
        final UserInput input = new UserInput();
        JCommander.newBuilder()
                .addObject(input)
                .build()
                .parse(args);
        this.userValues = input.userValues;
        this.host = input.host;
        this.port = input.port;
        this.username = input.username;
        this.table = input.table;
        this.password = input.password;
        this.dbName = input.dbName;
        this.version = input.version;
        this.help = input.help;

    }

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
     * Db host.
     */
    @Parameter(names = "-host", description = "Database host")
    private String host = DEFAULT_HOST;

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

    /**
     * Show current version of J-Riddler.
     */
    @Parameter(names = "-version", description = "Print current version")
    private boolean version;

    /**
     * Show help.
     */
    @Parameter(names = "-help", description = "Print help information")
    private boolean help;
}
