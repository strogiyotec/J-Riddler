package com.jriddler.columns.fk;

import lombok.experimental.Delegate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for {@link ForeignKeys}.
 */
public final class ForeignKeysBuilder implements ForeignKeys {

    /**
     * Origin.
     */
    @Delegate
    private final ForeignKeys origin;

    /**
     * Ctor.
     * Create foreign keys for given table
     * If table doesn't have foreign keys then
     * origin is assigned to {@link ForeignKeys.Empty}.
     *
     * @param dataSource DataSource
     * @param tableName  Table name
     * @throws SQLException If failed
     */
    public ForeignKeysBuilder(
            final DataSource dataSource,
            final String tableName
    ) throws SQLException {
        final Connection connection = dataSource.getConnection();
        final ResultSet importedKeys = connection.getMetaData().getImportedKeys(
                connection.getCatalog(),
                connection.getSchema(),
                tableName
        );
        final List<ForeignKey> keys = new ArrayList<>(16);
        while (importedKeys.next()) {
            keys.add(
                    new FkFromMeta(
                            importedKeys
                    )
            );
        }
        if (keys.isEmpty()) {
            this.origin = new ForeignKeys.Empty();
        } else {
            this.origin = new ForeignKeys.NonEmpty(keys);
        }
    }
}
