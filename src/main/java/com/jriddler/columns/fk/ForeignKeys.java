package com.jriddler.columns.fk;

import java.util.Collections;
import java.util.Iterator;

/**
 * Foreign Keys.
 */
public interface ForeignKeys extends Iterable<ForeignKey> {

    /**
     * Check if has elements.
     *
     * @return True if has elements.
     */
    boolean hasKeys();

    /**
     * Empty Foreign keys.
     */
    class Empty implements ForeignKeys {

        @Override
        public boolean hasKeys() {
            return false;
        }

        @Override
        public Iterator<ForeignKey> iterator() {
            return Collections.emptyIterator();
        }
    }

    /**
     * Non epmty foreign keys.
     */
    class NonEmpty implements ForeignKeys {

        /**
         * Foreign keys.
         */
        private final Iterable<ForeignKey> keys;

        /**
         * Ctor.
         * @param keys Foreign keys
         */
        public NonEmpty(final Iterable<ForeignKey> keys) {
            this.keys = keys;
        }

        @Override
        public boolean hasKeys() {
            return true;
        }

        @Override
        public Iterator<ForeignKey> iterator() {
            return this.keys.iterator();
        }
    }
}
