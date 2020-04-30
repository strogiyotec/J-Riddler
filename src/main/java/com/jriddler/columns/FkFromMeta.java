package com.jriddler.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class FkFromMeta implements ForeignKey {

    private final String pkTableName;

    private final String pkColumnName;

    private final String fkTableName;

    private final String fkColumnName;

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

