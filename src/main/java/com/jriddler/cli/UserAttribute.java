package com.jriddler.cli;

import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * Dynamic db attr from user.
 */
@AllArgsConstructor
public final class UserAttribute implements Map.Entry<String, String> {

    /**
     * Attr name.
     */
    private final String name;

    /**
     * Attr value.
     */
    private final String value;

    /**
     * Ctor.
     * Creates instance from cli input
     * The format : [name:value]
     *
     * @param cliParam Cli value
     */
    @SuppressWarnings("LineLength")
    public UserAttribute(final String cliParam) {
        final String[] param = cliParam.split(":");
        if (param.length != 2) {
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid attr from cli expected [name:value], actual [%s]",
                            cliParam
                    )
            );
        }
        this.name = param[0];
        this.value = param[1];
    }

    @Override
    public String getKey() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    @SuppressWarnings("HiddenField")
    public String setValue(final String value) {
        throw new UnsupportedOperationException(
                "Entry is immutable"
        );
    }
}
