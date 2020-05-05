package com.jriddler.text;

/**
 * Shows version information.
 */
public final class Version implements Text {

    /**
     * Current version.
     */
    public static final String VERSION = "0.01";

    /**
     * String representation of version.
     *
     * @return Version
     */
    @Override
    public String asString() {
        return "JRiddler version \"" + VERSION + "\"";
    }
}

