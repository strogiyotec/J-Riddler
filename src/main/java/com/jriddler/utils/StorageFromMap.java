package com.jriddler.utils;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

/**
 * Storage that encapsulates Map.
 */
@AllArgsConstructor
public final class StorageFromMap implements Storage {

    /**
     * Original storage.
     */
    private final Map<String, String> map;

    @Override
    public Optional<String> get(final String key) {
        return Optional.ofNullable(this.map.get(key));
    }
}
