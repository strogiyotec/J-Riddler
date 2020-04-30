package com.jriddler.columns;

public final class PredefinedForeignKeys implements ForeignKeys {
    private final Iterable<ForeignKey> keys;

    public PredefinedForeignKeys(final Iterable<ForeignKey> keys) {
        this.keys = keys;
    }

    @Override
    public boolean hasKeys() {
        return true;
    }

    @Override
    public Iterable<ForeignKey> foreignKeys() {
        return this.keys;
    }
}
