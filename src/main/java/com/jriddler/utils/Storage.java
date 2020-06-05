package com.jriddler.utils;

import java.util.Optional;

/**
 * Map interface is bloated we only need get method.
 */
public interface Storage {

    /**
     * Empty storage instance.
     */
    Empty EMPTY = new Empty();

    /**
     * Get value by key.
     *
     * @param key Key
     * @return Value by key
     */
    Optional<String> get(String key);

    /**
     * Empty storage.
     */
    class Empty implements Storage {

        @Override
        public Optional<String> get(final String key) {
            return Optional.empty();
        }
    }
}
