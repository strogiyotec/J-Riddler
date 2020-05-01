package com.jriddler;

/**
 * Shows version information.
 */
public final class Version {

    /**
     * Current version.
     */
    public static final String VERSION = "0.01";

    /**
     * String representation of version.
     *
     * @return Version
     */
    String asString() {
        return "JRiddler version \"" + VERSION + "\"";
    }
}
