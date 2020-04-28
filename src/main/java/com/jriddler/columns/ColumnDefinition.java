package com.jriddler.columns;

/**
 * Definition of column in db.
 */
@SuppressWarnings("InterfaceIsType")
public interface ColumnDefinition extends ColumnName, ColumnValue {

    /**
     * Auto incremented columns.
     */
    ColumnDefinition AUTO_INCR = new AutoIncremented();

    /**
     * Auto generated columns.
     */
    final class AutoIncremented implements ColumnDefinition {

        @Override
        public Object value() {
            throw new UnsupportedOperationException(
                    "AutoIncremented columns doesn't have value"
            );
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException(
                    "AutoIncremented columns doesn't have size"
            );
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException(
                    "AutoIncremented columns doesn't have name"
            );
        }
    }

}
