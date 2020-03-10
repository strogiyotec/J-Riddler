package com.jriddler.sql;

import com.jriddler.InsertQuery;
import com.jriddler.attrs.AttributeDefinition;
import com.jriddler.attrs.Attributes;
import com.jriddler.attrs.PrimaryKeys;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Executes insert query.
 */
@AllArgsConstructor
@Log
public final class SqlInsert implements SqlOperation<KeyHolder> {

    /**
     * List of argc.
     */
    private final List<AttributeDefinition> attrs;

    /**
     * Primary keys.
     */
    private final List<String> primaryKeys;

    /**
     * Table name.
     */
    private final String tableName;

    /**
     * Jdbc jdbcTemplate.
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Ctor.
     *
     * @param jdbcTemplate Jdbc template
     * @param tableName    Table name
     */
    public SqlInsert(
            final JdbcTemplate jdbcTemplate,
            final String tableName
    ) {
        this.attrs = new Attributes(
                tableName,
                jdbcTemplate
        );
        this.primaryKeys = new PrimaryKeys(
                tableName,
                jdbcTemplate
        );
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
    }

    /**
     * Execute sql and fetch inserted id.
     *
     * @return Inserted id
     */
    @Override
    @SuppressWarnings("LineLength")
    public KeyHolder perform() {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(
                connection -> {
                    final PreparedStatement statement = connection.prepareStatement(
                            this.query(),
                            this.primaryKeys.toArray(new String[0])
                    );
                    for (int i = 0; i < this.attrs.size(); i++) {
                        statement.setObject(i + 1, this.attrs.get(i).value());
                    }
                    return statement;
                }, keyHolder
        );
        this.logNewRow();
        return keyHolder;
    }

    /**
     * Log attributes of new row.
     */
    private void logNewRow() {
        log.log(
                Level.INFO,
                "New row for table [{0}] was created Params\n{1}",
                new Object[]{
                        this.tableName,
                        this.attrs
                                .stream()
                                .map(attr -> String.format(
                                        "Attribute: %s Value %s",
                                        attr.name(),
                                        attr.value()
                                ))
                                .collect(Collectors.joining("\n")),
                }
        );
    }

    /**
     * Create insert query.
     *
     * @return Insert query
     */
    private String query() {
        return new InsertQuery(
                this.attrs,
                this.tableName
        ).build();
    }
}
