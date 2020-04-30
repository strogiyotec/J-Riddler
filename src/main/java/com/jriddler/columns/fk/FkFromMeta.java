package com.jriddler.columns.fk;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Foreign key from result set meta.
 */
public final class FkFromMeta implements ForeignKey {

    /**
     * Primary key table name.
     */
    private final String pkTableName;

    /**
     * Primary key column name.
     */
    private final String pkColumnName;

    /**
     * Foreign key table name.
     */
    private final String fkTableName;

    /**
     * Foreign key column name.
     */
    private final String fkColumnName;

    /**
     * Ctor.
     *
     * @param importedKeys Foreign keys metadata.
     * @throws SQLException If failed
     */
    public FkFromMeta(final ResultSet importedKeys) throws SQLException {
        this.fkTableName = importedKeys.getString("FKTABLE_NAME");
        this.fkColumnName = importedKeys.getString("FKCOLUMN_NAME");
        this.pkTableName = importedKeys.getString("PKTABLE_NAME");
        this.pkColumnName = importedKeys.getString("PKCOLUMN_NAME");
    }

    @Override
    public String fkTableName() {
        return this.fkTableName;
    }

    @Override
    public String fkColumnName() {
        return this.fkColumnName;
    }

    @Override
    public String pkTableName() {
        return this.pkTableName;
    }

    @Override
    public String pkColumnName() {
        return this.pkColumnName;
    }
}

