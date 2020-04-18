package com.jriddler.sql;

import com.jriddler.InsertQuery;
import com.jriddler.attrs.AttributeDefinition;
import com.jriddler.attrs.Attributes;
import com.jriddler.attrs.PrimaryKeys;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Executes insert query.
 */
@AllArgsConstructor
@Log
@SuppressWarnings("LineLength")
public final class SqlInsert implements SqlOperation<List<Map<String, Object>>> {

    /**
     * List of table attributes.
     */
    private final List<AttributeDefinition> tableAttrs;

    /**
     * Primary keys.
     */
    private final List<String> pkNames;

    /**
     * Table name.
     */
    private final String tableName;

    /**
     * Jdbc query.
     */
    private final Query query;

    /**
     * Ctor.
     * Creates random attributes for new row
     *
     * @param dataSource DataSource
     * @param tableName  Table name
     */
    public SqlInsert(
            final DataSource dataSource,
            final String tableName
    ) {
        this(
                dataSource,
                tableName,
                Collections.emptyMap()
        );
    }

    /**
     * Ctor.
     * Create primary keys from table name
     *
     * @param tableAttrs Table attrs
     * @param tableName  Table name
     * @param dataSource Datasource
     */
    public SqlInsert(
            final List<AttributeDefinition> tableAttrs,
            final String tableName,
            final DataSource dataSource
    ) {
        this(
                tableAttrs,
                new PrimaryKeys(
                        tableName,
                        dataSource
                ),
                tableName,
                new FluentJdbcBuilder()
                        .connectionProvider(dataSource)
                        .build()
                        .query()
        );
    }

    /**
     * ctor.
     * use predefined values for row attrs
     *
     * @param dataSource     DataSource
     * @param tableName      Table name
     * @param userAttributes User defined attr values
     */
    public SqlInsert(
            final DataSource dataSource,
            final String tableName,
            final Map<String, String> userAttributes
    ) {
        this.tableAttrs = new Attributes(
                tableName,
                dataSource,
                userAttributes
        );
        this.pkNames = new PrimaryKeys(
                tableName,
                dataSource
        );
        this.query =
                new FluentJdbcBuilder()
                        .connectionProvider(dataSource)
                        .build()
                        .query();
        this.tableName = tableName;
    }

    /**
     * Execute sql and fetch inserted id.
     *
     * @return Inserted id
     */
    @Override
    @SuppressWarnings("LineLength")
    public List<Map<String, Object>> perform() {
        final List<Map<String, Object>> generatedKeys = this.query.update(this.query())
                .params(
                        this.tableAttrs.stream().map(AttributeDefinition::value).collect(Collectors.toList())
                )
                .runFetchGenKeys(Mappers.map(), this.pkNames.toArray(new String[0])).generatedKeys();
        this.logNewRow();
        return generatedKeys;
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
                        this.tableAttrs
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
                this.tableAttrs,
                this.tableName
        ).build();
    }
}
