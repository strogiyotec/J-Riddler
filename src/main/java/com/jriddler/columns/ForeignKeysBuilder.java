package com.jriddler.columns;

import lombok.experimental.Delegate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ForeignKeysBuilder implements ForeignKeys {

    @Delegate
    private final ForeignKeys keys;

    public ForeignKeysBuilder(final DataSource dataSource, final String tableName) throws SQLException {
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
            this.keys = new ForeignKeys.Empty();
        } else {
            this.keys = new PredefinedForeignKeys(keys);
        }
    }
}
