package com.jriddler.columns.fk;

/**
 * Foreign keys.
 */
public interface ForeignKey {

    /**
     * Foreign key table name.
     *
     * @return Table name
     */
    String fkTableName();

    /**
     * Foreign key column name.
     *
     * @return Column name
     */
    String fkColumnName();

    /**
     * Primary key table name.
     *
     * @return Table name
     */
    String pkTableName();

    /**
     * Primary key column name.
     *
     * @return Column name
     */
    String pkColumnName();

}
