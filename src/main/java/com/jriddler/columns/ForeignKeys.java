package com.jriddler.columns;

import java.util.Collections;

public interface ForeignKeys {

    boolean hasKeys();

    Iterable<ForeignKey> foreignKeys();

    class Empty implements ForeignKeys {

        @Override
        public boolean hasKeys() {
            return false;
        }

        @Override
        public Iterable<ForeignKey> foreignKeys() {
            return Collections::emptyIterator;
        }
    }
}
