package com.jriddler.attrs;

/**
 * Definition of column in db.
 */
public interface AttributeDefinition {

    /**
     * Auto incremented attr.
     */
    AttributeDefinition AUTO_INCR = new AutoIncremented();

    /**
     * Value of column.
     *
     * @return Value
     */
    Object value();

    /**
     * Size of the value in bytes.
     *
     * @return Size
     */
    int size();

    /**
     * Name of the column in table.
     *
     * @return Name
     */
    String name();

    /**
     * Auto generated attr.
     */
    final class AutoIncremented implements AttributeDefinition {

        @Override
        public Object value() {
            throw new UnsupportedOperationException(
                    "AutoIncremented attr doesn't have value"
            );
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException(
                    "AutoIncremented attr doesn't have size"
            );
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException(
                    "AutoIncremented attr doesn't have name"
            );
        }
    }

}
