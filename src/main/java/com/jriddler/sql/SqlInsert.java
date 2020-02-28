package com.jriddler.sql;

import com.jriddler.InsertQuery;
import com.jriddler.attrs.AttributeDefinition;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Executes insert query.
 */
@AllArgsConstructor
public final class SqlInsert implements SqlOperation<Integer> {

    /**
     * Jdbc template.
     */
    private final JdbcTemplate template;

    /**
     * List of attrs.
     */
    private final List<AttributeDefinition> definitions;

    /**
     * Table name.
     */
    private final String tableName;

    /**
     * Ctor.
     *
     * @param dataSource  Source
     * @param definitions Definitions
     * @param tableName   Table name
     */
    public SqlInsert(
            final DataSource dataSource,
            final List<AttributeDefinition> definitions,
            final String tableName
    ) {
        this.template = new JdbcTemplate(dataSource);
        this.definitions = definitions;
        this.tableName = tableName;
    }

    @Override
    public Integer perform() {
        return this.template.update(
                this.query(),
                this.definitions
                        .stream()
                        .map(AttributeDefinition::value)
                        .toArray()
        );
    }

    /**
     * Create insert query.
     *
     * @return Insert query
     */
    private String query() {
        return new InsertQuery(this.definitions, this.tableName).build();
    }
}
